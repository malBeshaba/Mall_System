package com.example.mallsystem.Display.Pay;

import com.example.mallsystem.Display.goods.Service.GoodsDBManager;
import com.example.mallsystem.Manager.Account.DAO.User;
import com.example.mallsystem.Market.Order.DAO.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PayService {
    @Setter @Getter
    private Order order;

    private GoodsDBManager manager;

    public void connectToAliPay(String bussiness) {

    }

    public void connectToWeChat(String bussiness) {

    }

    public boolean verify() {
        return false;
    }

    public void confirm(boolean b) {

    }
}