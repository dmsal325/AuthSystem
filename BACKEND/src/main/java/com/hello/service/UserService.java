package com.hello.service;

import com.hello.mapper.UserMapper;
import com.hello.utils.DefaultRes;
import com.hello.utils.ResponseMessage;
import com.hello.utils.StatusCode;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Resource(name="redisTemplate")
    private HashOperations<String, String, String> values;

    public UserService(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public DefaultRes hello(@RequestHeader(value="token") String token) {
        try {

            String user_id = (String)values.get(token, "user_id");
            String user_name = (String)values.get(token, "user_name");
//            System.out.println("id는"+user_id);
//            System.out.println("name는"+user_name);

//            userMapper.deleteByUserIdx(userIdx);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.SUCCESS_AUTH, "안녕하세요, "+user_name+"님!");
        } catch (Exception e) {
            //Rollback
                        return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);

        }

    }
}
