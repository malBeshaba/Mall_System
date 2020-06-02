package com.example.mallsystem.DisPlayTest;

import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Manager.Account.DAO.UserDBManager;

import java.io.*;
import java.sql.SQLException;

public class DisplayText {
    public static void main(String[] args) throws SQLException, IOException {
        UserDBManager manager = new UserDBManager();
        User user = new User();
        user.setName("18822149353");
        user.setPwd("123456");
        user.setPower(2);
        System.out.println(manager.verify(1, "123456"));
    }
}