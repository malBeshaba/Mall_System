package com.example.mallsystem.Display.goods.Service;

import com.example.mallsystem.Display.goods.DAO.GoodsDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Json.ToJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GoodsDetailService implements ToJson {
    private BaseJSON json;
    private ArrayList<LinkedHashMap<String, Object>> data;
    private String message;
    private int err;
    private GoodsDBManager manager;

    public GoodsDetailService(int id) {
        manager = new GoodsDBManager();
        data = manager.selectGoodsDetail(id);
        message = isNull() ? "haven't data" : "success";
        err = isNull() ? 0 : -1;
        json = new BaseJSON(message, err, data);
    }

    private boolean isNull() {
        return data == null;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(json);
    }
}