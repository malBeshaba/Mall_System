package com.example.mallsystem.Display.Point.Controller;

import com.example.mallsystem.Display.Point.DAO.Point;
import com.example.mallsystem.Display.Point.Service.PointDBManager;
import com.example.mallsystem.Display.ShoppingCart.Controller.CartController;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/point")
public class PointController {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private PointDBManager manager;

    private final Gson gson = new Gson();

    private BaseJSON json;

    @GetMapping(
            value = "/get",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getPoint(@RequestHeader("Authorization") String authHeader) {
        User user = getUser(authHeader);
        Point point = manager.selectPoint(user.getId());
        if (point != null) {
            Map<String, Integer> map = new HashMap<>();
            map.put("point", point.getPoint());
            json = new BaseJSON("success", -1, map);
        } else {
            json = new BaseJSON("failed", 0, null);
        }
        return gson.toJson(json);
    }

    @PostMapping(
            value = "/add",
            produces = "application/json;" + "charset=utf-8"
    )
    public String addPoint(@RequestHeader("Authorization") String authHeader, @RequestParam("points") int p) {
        User user = getUser(authHeader);
        Point point = manager.selectPoint(user.getId());
        point.setPoint(point.getPoint() + p);
        boolean b = manager.update(point);
        return isSuccess(b);
    }

    @PostMapping(
            value = "/spend",
            produces = "application/json;" + "charset=utf-8"
    )
    public String spendPoints(@RequestHeader("Authorization") String authHeader, @RequestParam("points") int p) {
        User user = getUser(authHeader);
        Point point = manager.selectPoint(user.getId());
        point.setPoint(point.getPoint() - p);
        boolean b = manager.update(point);
        return isSuccess(b);
    }

    private User getUser(String token) {
        return CartController.getUser(token, jwtToken, gson);
    }

    private String isSuccess(boolean b) {
        if (b) {
            BaseJSON json = new BaseJSON("success", -1, null);
            return gson.toJson(json);
        } else {
            BaseJSON json = new BaseJSON("failed", 0, null);
            return gson.toJson(json);
        }
    }
}