package com.atguigu.zhxy.pojo;

import lombok.Data;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:30
 * 接收登录表单信息
 */
@Data
public class LoginForm {

    private String username;

    private String password;

    private String verifiCode;

    private Integer userType;
}
