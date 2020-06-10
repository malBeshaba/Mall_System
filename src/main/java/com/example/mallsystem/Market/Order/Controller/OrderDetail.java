package com.example.mallsystem.Market.Order.Controller;

import com.example.mallsystem.Display.goods.DAO.Goods;
import com.example.mallsystem.Market.Order.DAO.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class OrderDetail {
    @Getter
    @Setter
    private int id;
    @Getter @Setter
    private OrderType type;
    @Getter @Setter
    private Goods goods;
}