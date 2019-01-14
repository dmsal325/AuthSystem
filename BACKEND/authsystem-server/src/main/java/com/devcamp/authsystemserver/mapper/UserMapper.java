package com.devcamp.authsystemserver.mapper;


import com.devcamp.authsystemserver.dto.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //모든 회원 리스트 조회
    @Select("SELECT user_idx, user_id, user_name, user_age FROM user")
    List<User> findAll();

    //회원 이름으로 조회
    @Select("SELECT * FROM user WHERE user_id = #{user_id}")
    User findByid(@Param("user_id") final String id);

    //회원 고유 번호로 조회
    @Select("SELECT user_idx, user_id, user_pwd, user_name, user_age, user_grade, user_status FROM user WHERE user_idx = #{user_idx}")
    User findByUserIdx(@Param("user_idx") final int userIdx);

//    //회원 로그인
//    @Select("SELECT user_id, user_name, user_age FROM user WHERE user_id = #{user.user_id} AND user_pwd = #{user.user_pwd}")
//    User login(@Param("user") final User user);

    //로그인 기록 갱신
    @Update("UPDATE user SET user_login_dt= now() WHERE user_idx = #{user_idx}")
    void loginDateUpdate(@Param("user_idx") final int userIdx);

    //회원 등록, Auto Increment는 회원 고유 번호
    //Auto Increment 값을 받아오고 싶으면 리턴 타입을 int로(Auto Increment 컬럼 타입)
    @Insert("INSERT INTO user(user_id, user_pwd, user_name, user_age, user_salt) VALUES(#{user.user_id}, #{user.user_pwd}, #{user.user_name}, #{user.user_age}, #{user.user_salt})")
    @Options(useGeneratedKeys = true, keyColumn = "user.user_idx")
    int save(@Param("user") final User user);

//    //Auto Increment 값을 받아오고 싶지 않을때
//    @Insert("INSERT INTO user(user_id, user_pwd, user_name, user_age) VALUES(#{user.id}, #{user.pwd}, #{user.name}, #{user.age})")
//    void save2(@Param("user") final User user);

    //회원 정보 수정
    @Update("UPDATE user SET user_name = #{user.user_name}, user_age = #{user.user_age} WHERE user_idx = #{user_idx}")
    void update(@Param("user_idx") final int userIdx, @Param("user") final User user);

    //회원 삭제
//    @Delete("DELETE FROM user where user_idx = #{user_idx}")
    @Update("UPDATE user SET user_status = 'disabled' WHERE user_idx = #{user_idx}")
    void deleteByUserIdx(@Param("user_idx") final int userIdx);
}
