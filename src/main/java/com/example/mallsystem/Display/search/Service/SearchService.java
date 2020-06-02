package com.example.mallsystem.Display.search.Service;


import com.example.mallsystem.Display.search.DAO.SearchDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Json.ToJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SearchService implements ToJson {
    private BaseJSON json;
    private ArrayList<ArrayList<LinkedHashMap<String, Object>>> data;
    private String message;
    private int err;
    private SearchDBManager manager;

    public SearchService(String param) {
        manager = new SearchDBManager();
        data = manager.blurSelect(param);
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

