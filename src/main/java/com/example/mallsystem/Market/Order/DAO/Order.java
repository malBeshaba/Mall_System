package com.example.mallsystem.Market.Order.DAO;

import lombok.*;

import java.sql.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private int user_id;
    @Getter @Setter
    private int goods_id;
    @Getter @Setter
    private OrderType type;
    @Getter @Setter
    private Date createTime;
}