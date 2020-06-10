package com.example.mallsystem.Market.Order.Service;

import com.example.mallsystem.Display.Point.DAO.Point;
import com.example.mallsystem.Market.Order.DAO.Order;
import com.example.mallsystem.Market.Order.DAO.OrderType;
import com.example.mallsystem.Public.Database.DBManager;
import lombok.Cleanup;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;

@Configuration
public class OrderDBManager extends DBManager {

    public boolean insert(Order order) {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "insert into Orders(user_id, goods_id, type, createTime) values (?,?,?,?)";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, order.getUser_id());
            stat.setInt(2, order.getGoods_id());
            stat.setString(3, order.getType().getType());
            stat.setDate(4, new Date(System.currentTimeMillis()));
            int result = stat.executeUpdate();
            System.out.println("success: insert to Order. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Order> selectAll(int userId) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select * from Orders where user_id=?";
        ArrayList<Order> list = null;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, userId);
            rs = stat.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser_id(userId);
                order.setGoods_id(rs.getInt("goods_id"));
                order.setType(OrderType.NotDeliver);
                order.setCreateTime(rs.getDate("createTime"));
                list.add(order);
            }
            System.out.println("success: select from Order.");
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order select(int orderId) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select * from Orders where id=?";
        Order order = null;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, orderId);
            rs = stat.executeQuery();
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setGoods_id(rs.getInt("goods_id"));
                order.setType(OrderType.NotDeliver);
                order.setCreateTime(rs.getDate("createTime"));
            }
            System.out.println("success: select from Order.");
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public boolean update(Order order) {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "update Orders set type=? where id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setString(1, order.getType().getType());
            stat.setInt(2, order.getId());
            int result = stat.executeUpdate();
            System.out.println("success: update Order. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}