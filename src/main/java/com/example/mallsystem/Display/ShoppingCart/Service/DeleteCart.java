package com.example.mallsystem.Display.ShoppingCart.Service;

import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCartDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Json.ToJson;
import com.google.gson.Gson;
import lombok.Getter;

public class DeleteCart implements ToJson {
    @Getter
    private BaseJSON json;
    public DeleteCart(int cartId) {
        ShoppingCartDBManager manager = new ShoppingCartDBManager();
        int err;
        String msg;
        if (manager.delete(cartId)) {
            err = -1;
            msg = "success";
        } else {
            err = 0;
            msg = "fails to delete";
        }
        json = new BaseJSON(msg, err, null);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(json);
    }
}