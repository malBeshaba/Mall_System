package com.example.mallsystem.Display.homepage.Service;

import com.example.mallsystem.Display.goods.Service.GoodsDBManager;
import com.example.mallsystem.Display.news.Service.NewsDBManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Json.ToJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class HPData {
    protected ArrayList<LinkedHashMap<String, Object>> goodsList;
    protected ArrayList<LinkedHashMap<String, Object>> newsList;

    public HPData(ArrayList<LinkedHashMap<String, Object>> goodsList, ArrayList<LinkedHashMap<String, Object>> newsList) {
        this.goodsList = goodsList;
        this.newsList = newsList;
    }
}

public class DisplayData implements ToJson {
    /*商品和新闻的数据管理器*/
    private GoodsDBManager goodsDBManager;
    private NewsDBManager newsDBManager;
    /*首页JSON*/
    private BaseJSON json;
    /*JSON格式*/
    private int err;
    private String message;
    private HPData hpData;
    /*商品和新闻的数据列表*/
    private ArrayList<LinkedHashMap<String, Object>> goodList;
    private ArrayList<LinkedHashMap<String, Object>> newsList;


    public DisplayData() {
        loadData();
        json = new BaseJSON(message, err, hpData);
    }

    private void loadData() {
        goodsDBManager = new GoodsDBManager();
        goodList = goodsDBManager.selectHP(10);

        newsDBManager = new NewsDBManager();
        newsList = newsDBManager.selectHP(10);
        hpData = new HPData(goodList, newsList);
        message = isNull() ? "haven't data" : "success";
        err = isNull() ? 0 : -1;
    }

    private boolean isNull() {
        return goodList == null && newsList == null;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.json);
    }
}