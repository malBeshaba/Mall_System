package com.example.mallsystem.Market.Order.Controller;

import com.example.mallsystem.Display.ShoppingCart.Controller.CartController;
import com.example.mallsystem.Display.goods.DAO.Goods;
import com.example.mallsystem.Display.goods.Service.GoodsDBManager;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Market.Order.DAO.Order;
import com.example.mallsystem.Market.Order.Service.OrderDBManager;
import com.example.mallsystem.Market.Order.DAO.OrderType;
import com.example.mallsystem.Public.Json.BaseJSON;
import com.example.mallsystem.Public.Token.JwtToken;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private OrderDBManager orderDBManager;

    @Autowired
    private GoodsDBManager goodsDBManagerr;

    private final Gson gson = new Gson();

    @PostMapping(
            value = "/create",
            produces = "application/json;" + "charset=utf-8"
    )
    public String createOrder(@RequestHeader("Authorization") String authHeader, @RequestParam("goods_id") int goodsId) {
        User user = getUser(authHeader);
        Order order = new Order();
        order.setUser_id(user.getId());
        order.setGoods_id(goodsId);
        order.setType(OrderType.NotDeliver);
        boolean b = orderDBManager.insert(order);
        return isSuccess(true);
    }

    @GetMapping(
            value = "/getAll",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getAllOrder(@RequestHeader("Authorization") String authHeader) {
        User user = getUser(authHeader);
        ArrayList<Order> orders = orderDBManager.selectAll(user.getId());
        BaseJSON json = null;
        json = orders == null ? new BaseJSON("failed", 0, null): new BaseJSON("success", -1, orders);
        return gson.toJson(json);
    }

    @GetMapping(
            value = "/get",
            produces = "application/json;" + "charset=utf-8"
    )
    public String getOrder(@RequestHeader("Authorization") String authHeader, @RequestParam("order_id") int orderID) {
        Order order = orderDBManager.select(orderID);
        Goods goods = goodsDBManagerr.selectDetail(order.getGoods_id());
        OrderDetail data = new OrderDetail();
        data.setId(orderID);
        data.setGoods(goods);
        data.setType(OrderType.NotDeliver);
        BaseJSON json = new BaseJSON("success", -1, data);
        return gson.toJson(json);
    }

    @PostMapping(
            value = "/update",
            produces = "application/json;" + "charset=utf-8"
    )
    public String updateOrderType(@RequestHeader("Authorization") String authHeader, @RequestParam("order_id") int id, @RequestParam("type") String type) {
        User user = getUser(authHeader);
        if (user.getPower() < 2) {
            Order order = orderDBManager.select(id);
            order.setType(OrderType.valueOf(type));
            boolean b = orderDBManager.update(order);
            return isSuccess(b);
        } else {
            BaseJSON json = new BaseJSON("haven't power", 0, null);
            return gson.toJson(json);
        }
    }

    private User getUser(String token) {
        return CartController.getUser(token, jwtToken, gson);
    }

    private String isSuccess(boolean b) {
        if (b) {
            BaseJSON json = new BaseJSON("success", -1, null);
            return gson.toJson(json);
        } else {
            BaseJSON json = new BaseJSON("failed", 0, null);
            return gson.toJson(json);
        }
    }
}

