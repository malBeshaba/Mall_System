package com.example.mallsystem.Manager.Account.DAO;

import lombok.*;

import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull @Getter @Setter
    private int id;
    @NonNull @Getter @Setter
    private String name;
    @Getter @Setter
    private String pwd;
    @Getter @Setter
    private Date birthday;
    @Getter @Setter
    private String signture;
    @Getter @Setter
    private String address;
    @NonNull @Getter @Setter
    private int power;
    //0 管理员
    //1 商家
    //2 普通用户
}