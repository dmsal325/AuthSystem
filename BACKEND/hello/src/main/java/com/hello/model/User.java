package com.hello.model;

import lombok.Data;

import java.util.Date;

@Data
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