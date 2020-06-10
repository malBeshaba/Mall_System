package com.example.mallsystem.Manager.Account.Controller;

import com.example.mallsystem.Display.Point.DAO.Point;
import com.example.mallsystem.Display.Point.Service.PointDBManager;
import com.example.mallsystem.Display.ShoppingCart.Controller.CartController;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Manager.Account.Service.UserDBManager;
import com.example.mallsystem.Manager.Account.Service.LoginService;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDBManager manager;

    private BaseJSON json;
    private Gson gson = new Gson();

    @PostMapping(
            value = "/login",
            produces = "application/json;" + "charset=utf-8"
    )
    public String login(@RequestParam("user_id") int id, @RequestParam("password") String pwd) {
        boolean b = manager.verify(id, pwd);
        if (b) {
            LoginService service = new LoginService(id);
            return service.toJson();
        } else {
            return gson.toJson(new BaseJSON("fail to login",0, null));
        }
    }

    @PostMapping(
            value = "/register",
            produces = "application/json;" + "charset=utf-8"
    )
    public String register(@RequestParam("user_name") String name, @RequestParam("password") String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        user.setPower(2);
        PointDBManager pointDBManager = new PointDBManager();
        boolean b = manager.insert(user);
        Point point = new Point();
        User user1 = manager.selectAll(name);
        point.setUser_id(user1.getId());
        point.setPoint(0);
        boolean a = pointDBManager.insert(point);
        return isSuccess(b && a);
    }

    @PostMapping(
            value = "/updateInfo",
            produces = "application/json;" + "charset=utf-8"
    )
    public String updateUserInfo(@RequestHeader("Authorization") String authHeader, @RequestParam("user_name") String name, @RequestParam("birthday") String birth, @RequestParam("address") String address, @RequestParam("signture") String signture, @RequestParam("password") String pwd) {
        User user = getUser(authHeader);
        user.setName(name);
        user.setPwd(pwd);
        user.setSignture(signture);
        user.setAddress(address);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(birth);
            user.setBirthday(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean b = manager.update(user);
        return isSuccess(b);
    }

    @PostMapping(
            value = "/updatePower",
            produces = "application/json;" + "charset=utf-8"
    )
    public String updatePower(@RequestHeader("Authorization") String authHeader, @RequestParam("power") int power) {
        User user = getUser(authHeader);
        user.setPower(power);
        boolean b = manager.update(user);
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