package com.example.mallsystem.Market.Order.DAO;

import lombok.Getter;
import lombok.Setter;

public enum OrderType {
    NotDeliver("未发货"), Deliver("发货"), Return("退货"), ToBeReceived("待收货"), Received("已收货");

    @Getter @Setter
    private String type;

    private OrderType(String type) {
        this.type = type;
    }
}
