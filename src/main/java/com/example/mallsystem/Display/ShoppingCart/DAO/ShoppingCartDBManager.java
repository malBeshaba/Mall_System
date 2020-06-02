package com.example.mallsystem.Display.ShoppingCart.DAO;

import com.example.mallsystem.Public.Database.DBManager;
import com.example.mallsystem.Public.Database.Insert;
import com.example.mallsystem.Public.Database.Select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCartDBManager extends DBManager {


    public boolean insert(Object map) {
        ShoppingCart sc = (ShoppingCart) map;
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "insert into ShoppingCart(user_id, goods_id) values (?,?)";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, sc.getUser_id());
            stat.setInt(2, sc.getGoods_id());
            int result = stat.executeUpdate();
            System.out.println("success: insert to ShoppingCart. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ShoppingCart> selectAll(int userId) {
        ArrayList<ShoppingCart> list = null;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select id, goods_id from ShoppingCart where user_id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, userId);
            rs = stat.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                ShoppingCart sc = new ShoppingCart();
                sc.setId(rs.getInt("id"));
                sc.setUser_id(userId);
                sc.setGoods_id(rs.getInt("goods_id"));
                list.add(sc);
            }
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int cartId) {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "delete from ShoppingCart where id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, cartId);
            int result = stat.executeUpdate();
            System.out.println("success: delete from ShoppingCart. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}