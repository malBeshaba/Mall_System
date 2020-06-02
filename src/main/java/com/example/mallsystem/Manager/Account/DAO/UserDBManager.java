package com.example.mallsystem.Manager.Account.DAO;

import com.example.mallsystem.Display.ShoppingCart.DAO.ShoppingCart;
import com.example.mallsystem.Public.Database.DBManager;
import lombok.Cleanup;
import org.springframework.context.annotation.Configuration;

import java.sql.*
        ;
@Configuration
public class UserDBManager extends DBManager {

    public User selectAll(int user_id) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        String sql = "select * from User where id=?;";
        User user = null;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, user_id);
            rs = stat.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(user_id);
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPower(rs.getInt("power"));
                user.setSignture(rs.getString("signture"));
                user.setPwd(rs.getString("password"));
            }
         } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean insert(User user) {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "insert into User(name, password, power) values (?,?,?)";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setString(1, user.getName());
            stat.setString(2, user.getPwd());
            stat.setInt(3, user.getPower());
            int result = stat.executeUpdate();
            System.out.println("success: insert to User. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User selectAll(String username) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        String sql = "select * from User where name=?;";
        User user = null;
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setString(1, username);
            rs = stat.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPower(rs.getInt("power"));
                user.setSignture(rs.getString("signture"));
                user.setPwd(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean update(User user) {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "update User set name=?,birthday=?,signture=?,power=?,password=?,address=? where id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setString(1, user.getName());
            stat.setDate(2, new Date(user.getBirthday().getTime()));
            stat.setString(3, user.getSignture());
            stat.setInt(4, user.getPower());
            stat.setString(5, user.getPwd());
            stat.setString(6, user.getAddress());
            stat.setInt(7, user.getId());
            int result = stat.executeUpdate();
            System.out.println("success: update User. line:"+result);
            this.close(null, stat, conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify(int id, String pwd) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        String sql = "select password from User where id=?";

        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            String select = null;
            while (rs.next()) {
                select = rs.getString("password");
            }
            assert select != null;
            return select.equals(pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify(String name, String pwd) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        String sql = "select password from User where name=?";

        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            rs = stat.executeQuery();
            String select = null;
            while (rs.next()) {
                select = rs.getString("password");
            }
            assert select != null;
            return select.equals(pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}