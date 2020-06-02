package com.example.mallsystem.DisPlayTest;

import com.example.mallsystem.Public.Database.DBManager;
import com.example.mallsystem.Public.Database.Insert;

import java.sql.*;
import java.util.Map;

public class InsertData extends DBManager implements Insert {

    @Override
    public boolean insert(Map<String, Object> map) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.connect();
            String sql = "insert into Goods(name,business,volume,price,detail) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, (String)map.get("name"));
            stmt.setString(2, (String)map.get("business"));
            stmt.setInt(3, (int)map.get("volume"));
            stmt.setDouble(4, (double)map.get("price"));
            stmt.setString(5, (String)map.get("detail"));

            int result = stmt.executeUpdate();
            System.out.println("success: insert to goods. line:"+result);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insert(Object map) throws SQLException {
        return false;
    }
}