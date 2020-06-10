package com.example.mallsystem.DisPlayTest;

import com.example.mallsystem.Display.search.DAO.SearchDBManager;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Manager.Account.Service.UserDBManager;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import java.io.*;
import java.sql.SQLException;

public class DisplayText {
    public static void main(String[] args) throws SQLException, IOException {
        SearchDBManager manager = new SearchDBManager();
        System.out.println(manager.blurSelect("飞"));
    }


    public static User getUser(String authHeader, JwtToken jwtToken, Gson gson) {
        Claims claims = jwtToken.getClaimByToken(authHeader);
        if (claims == null || JwtToken.isTokenExpired(claims.getExpiration())) {
            return new User(0, "nobody", null, null, null, null, -1);
        }
        String userJson = (String) claims.get(jwtToken.getUserKey());
        return gson.fromJson(userJson, User.class);
    }
}