package com.hello.mapper;

import com.hello.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //회원 고유 번호로 조회
    @Select("SELECT user_idx, user_id, user_pwd, user_name, user_age, user_grade, user_status FROM user WHERE user_idx = #{user_idx}")
    User findByUserIdx(@Param("user_idx") final int userIdx);

}
