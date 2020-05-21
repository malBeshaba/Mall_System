package com.example.mallsystem.Display.homepage.Controller;

import com.example.mallsystem.Display.homepage.Service.DisplayData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HPGoodsController {
    DisplayData data;
    @GetMapping(
            value = "/homepage",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getData() {
        data = new DisplayData();
        return data.toJson();
    }
}