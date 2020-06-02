package com.example.mallsystem.Display.Point.Service;

import com.example.mallsystem.Display.Point.DAO.Point;
import com.example.mallsystem.Public.Database.DBManager;
import lombok.Cleanup;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration
public class PointDBManager extends DBManager {
    public boolean update(Point point) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        String sql = "update Point set point=? where user_id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, point.getPoint());
            stat.setInt(2, point.getUser_id());
            int result = stat.executeUpdate();
            System.out.println("success: update Point. line:"+result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(Point point) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        String sql = "insert into Point(user_id,point) values (?,?)";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, point.getUser_id());
            stat.setInt(2, point.getPoint());
            int result = stat.executeUpdate();
            System.out.println("success: insert into Point. line:"+result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Point selectPoint(int userId) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        String sql = "select * from Point where user_id=?";
        Point point = null;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, userId);
            rs = stat.executeQuery();
            while (rs.next()) {
                point = new Point();
                point.setId(rs.getInt("id"));
                point.setUser_id(userId);
                point.setPoint(rs.getInt("point"));
            }
            System.out.println("success: insert into Point.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point;
    }
}