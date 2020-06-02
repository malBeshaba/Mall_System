package com.example.mallsystem.Display.ShoppingCart.Controller;

import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCart;
import com.example.mallsystem.Display.ShoppingCart.Service.AddToCart;
import com.example.mallsystem.Display.ShoppingCart.Service.DeleteCart;
import com.example.mallsystem.Display.ShoppingCart.Service.GetCart;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private JwtToken jwtToken;

    private Gson gson = new Gson();

    @PostMapping(
            value = "/add",
            produces = "application/json;" + "charset=utf-8"
    )
    public String addCart(@RequestParam(value = "goods_id") int id, @RequestHeader("Authorization") String authHeader) throws AuthenticationException {
        // 黑名单token
        User user = getUser(authHeader);
        ShoppingCart sc = new ShoppingCart();
        sc.setUser_id(user.getId());
        sc.setGoods_id(id);
        AddToCart addToCart = new AddToCart(sc);
        return addToCart.toJson();
    }

    @GetMapping(
            value = "/get",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getCart(@RequestHeader("Authorization") String authHeader) {
        User user = getUser(authHeader);
        GetCart cart = new GetCart(user.getId());
        return cart.toJson();
    }

    @DeleteMapping(
            value = "/deleteCart",
            produces = "application/json;" + "charset=utf-8"
    )
    public String deleteCart(@RequestHeader("Authorization") String authHeader, @RequestParam("cart_id") int id) {
        DeleteCart cart = new DeleteCart(id);
        return cart.toJson();
    }

    private User getUser(String authHeader) {
        return getUser(authHeader, jwtToken, gson);
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