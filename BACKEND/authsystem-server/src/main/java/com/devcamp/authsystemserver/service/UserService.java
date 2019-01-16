package com.devcamp.authsystemserver.service;

import com.devcamp.authsystemserver.dto.User;
import com.devcamp.authsystemserver.mapper.UserMapper;
import com.devcamp.authsystemserver.utils.DefaultRes;
import com.devcamp.authsystemserver.utils.HasingPwd;
import com.devcamp.authsystemserver.utils.ResponseMessage;
import com.devcamp.authsystemserver.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

import static sun.security.ssl.HandshakeMessage.debug;

@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    @SuppressWarnings("unchecked")
//    RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//
//    // value operation
//    ListOperations<String, String> values = redisTemplate.opsForList();


    public UserService(final UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }


    //모든 회원 조회
    public DefaultRes getAllUsers() {
        final List userList = userMapper.findAll();

        if (userList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, userList);
    }


//     //이름으로 회원 조회
//    public DefaultRes findByName(final String name) {
//        final User user = userMapper.findByName(name);
//        if (user == null)
//            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
//        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
//    }

    /**
     * 로그인
     *
     * @param user 회원 데이터
     * @return DefaultRes
     */

    public DefaultRes login(final String id, final User user) {

        try {
            //입력받은 아이디의 user가 있는지 검사
            User temp = userMapper.findByid(id);
            if (temp == null)
                return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);

            if (temp.getUser_status().equals("disabled"))
                return DefaultRes.res(StatusCode.SERVICE_UNAVAILABLE, ResponseMessage.FAIL_DELETE_USER);

            //temp의 pwd와 salt가져와
            String pwd = temp.getUser_pwd();
            String salt = temp.getUser_salt();

            //입력받은 pwd를 salt값으로 해쉬한뒤 가져온 pwd와 검사
            String beforeEncodingPwd = salt + user.getUser_pwd();
            String encodePwd = HasingPwd.hasingPwd(beforeEncodingPwd);


            if (pwd.equals(encodePwd)) {
                //맞으면 ok메세지 리턴

                System.out.println("idx값은 " + temp.getUser_idx());
                userMapper.loginDateUpdate(temp.getUser_idx());
                final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(temp.getUser_idx()));

                redisTemplate.opsForValue().set("key:auth", tokenDto.getToken());
                String value = (String) redisTemplate.opsForValue().get("key:auth");
                System.out.println(value);

//                String value = (String)redisTemplate.opsForValue().get(key);
//                Assert.assertEquals("token", value);
//                values.leftPush("key:auth", tokenDto.getToken());


                return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
            } else {
                //안맞으면 error메세지 리턴
                return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.LOGIN_FAIL);
            }

        } catch (Exception e) {
            log.debug(e.toString());
            return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.LOGIN_FAIL);
        }


    }


    /**
     * 회원 가입
     *
     * @param user 회원 데이터
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes save(final User user) {
        try {
            String pwd = user.getUser_pwd();

            //salt값생성
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            String saltNum = new Integer(prng.nextInt()).toString();

            //해시함수써서 저장할 비밀번호값 생성
            String beforeEncodingPwd = saltNum + pwd;
            String encodePwd = HasingPwd.hasingPwd(beforeEncodingPwd);

            user.setUser_salt(saltNum);
            user.setUser_pwd(encodePwd);

            //set해서 저장
            userMapper.save(user);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 회원 정보 수정
     *
     * @param userIdx 회원 고유 번호
     * @param user    수정할 회원 데이터
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes update(final int userIdx, final User user) {
        User temp = userMapper.findByUserIdx(userIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        try {
            if (user.getUser_name() != null) temp.setUser_name(user.getUser_name());
            if (user.getUser_age() != 0) temp.setUser_age(user.getUser_age());
            userMapper.update(userIdx, temp);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.UPDATE_USER);
        } catch (Exception e) {

            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);

        }

    }

    /**
     * 회원 탈퇴
     *
     * @param userIdx 회원 고유 번호
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes deleteByUserIdx(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if (user == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        try {
            userMapper.deleteByUserIdx(userIdx);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.SUCCESS_DELETE_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);

        }

    }
}
