package com.example.mallsystem.Manager.Account.Service;

import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Json.ToJson;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import lombok.Getter;

import java.util.LinkedHashMap;

public class LoginService implements ToJson {
    private BaseJSON json;
    private int err;
    private String msg;
    private LinkedHashMap<String, String > data;
    private UserDBManager manager;
    @Getter
    private User user;

    private JwtToken jwtToken;

    private String token;

    public LoginService(int id) {
        loadData(id);
    }

    private void loadData(int id) {
        manager = new UserDBManager();
        user = manager.selectAll(id);
        jwtToken = new JwtToken();
        token = jwtToken.generateToken(user);
        data = new LinkedHashMap<>();
        data.put("token", token);
        err = -1;
        msg = "success";
        json = new BaseJSON(msg, err, data);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(json);
    }
}