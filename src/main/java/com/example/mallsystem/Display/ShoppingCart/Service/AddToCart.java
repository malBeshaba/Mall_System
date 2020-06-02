package com.example.mallsystem.Display.ShoppingCart.Service;

import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCart;
import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCartDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.google.gson.Gson;
import lombok.Getter;

import java.sql.SQLException;


public class AddToCart {
    @Getter
    private BaseJSON json;

    public AddToCart(ShoppingCart sc) {
        ShoppingCartDBManager manager = new ShoppingCartDBManager();
        int err;
        String msg;
        if (manager.insert(sc)) {
            err = -1;
            msg = "success";
        } else {
            err = 0;
            msg = "fails to insert";
        }
        json = new BaseJSON(msg, err, null);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(json);
    }
}