package com.example.mallsystem.Display.goods.Controller;

import com.example.mallsystem.Display.goods.Service.GoodsDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsDetailController {
    private GoodsDetailService service;

    @GetMapping(
            value = "/goods/detail",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getGoodsDetail(@RequestParam(value = "goods_id") int id) {
        service = new GoodsDetailService(id);
        return service.toJson();
    }
}