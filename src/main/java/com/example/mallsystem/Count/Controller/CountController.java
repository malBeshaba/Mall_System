package com.example.mallsystem.Count.Controller;

import com.example.mallsystem.Count.DAO.Count;
import com.example.mallsystem.Count.Service.CountManager;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/count")
public class CountController {

    @Autowired
    private CountManager manager;

    private final Gson gson = new Gson();

    @GetMapping(
            value = "/get",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getCount(@RequestParam("goods_id") int id) {
        Count count = new Count();
        count.setGoods_id(id);
        count.setDaily_sales(manager.getDailySales(id));
        count.setMonthly_sales(manager.getMonthlySales(id));
        count.setAnnual_sales(manager.getAnnualSales(id));
        return gson.toJson(new BaseJSON("success", -1, count));
    }
}