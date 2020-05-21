package com.example.mallsystem.Display.goods.Controller;

import com.example.mallsystem.Display.goods.DAO.GoodsDBManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;

@RestController
public class GoodsImageController {
    @GetMapping(
            value = "/goods/image",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseBody
    public byte[] getImage(@RequestParam(value = "goods_id") int id) throws SQLException {
        GoodsDBManager manager = new GoodsDBManager();
        return manager.getImage(id);
    }

}