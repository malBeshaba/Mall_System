package com.example.mallsystem.Display.ShoppingCart.Service;

import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCart;
import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCartDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.google.gson.Gson;
import lombok.Getter;

import java.util.ArrayList;

public class GetCart {
    @Getter
    private BaseJSON json;

    public GetCart(int userId) {
        ShoppingCartDBManager manager = new ShoppingCartDBManager();
        ArrayList<ShoppingCart> list =  manager.selectAll(userId);
        if (list != null) {
            json = new BaseJSON("success", -1, list);
        } else {
            json = new BaseJSON("fail to load cart", 0, null);
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(json);
    }

}