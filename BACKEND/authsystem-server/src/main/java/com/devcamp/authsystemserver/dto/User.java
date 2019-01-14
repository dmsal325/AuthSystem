package com.devcamp.authsystemserver.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class User {
    private int user_idx;
    private String user_id;
    private String user_pwd;
    private String user_salt;
    private String user_name;
    private int user_age;
    private String user_grade;
    private String user_status;
    private Date user_regist_dt;
    private Date user_update_dt;
    private Date user_login_dt;

}