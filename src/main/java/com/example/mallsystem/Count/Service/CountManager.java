package com.example.mallsystem.Count.Service;

import com.example.mallsystem.Public.Database.DBManager;
import lombok.Cleanup;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.Calendar;

@Configuration
public class CountManager extends DBManager {

    public int getDailySales(int goods_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select count(*) from Order where createTime=?";
        int result = 0;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setDate(1, new Date(System.currentTimeMillis()));
            rs = stat.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getMonthlySales(int goods_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select count(*) from Order where createTime between ? and ?";
        int result = 0;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setDate(1, getBeforeDate(30));
            stat.setDate(2, new Date(System.currentTimeMillis()));
            rs = stat.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getAnnualSales(int goods_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select count(*) from Order where createTime between ? and ?";
        int result = 0;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setDate(1, getBeforeDate(365));
            stat.setDate(2, new Date(System.currentTimeMillis()));
            rs = stat.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Date getBeforeDate(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -30);
        return new Date(calendar.getTime().getTime());
    }

}