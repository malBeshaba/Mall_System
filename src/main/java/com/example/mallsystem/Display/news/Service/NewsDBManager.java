package com.example.mallsystem.Display.news.Service;

import com.example.mallsystem.Display.goods.DAO.SelectExtension;
import com.example.mallsystem.Public.Database.DBManager;
import com.example.mallsystem.Public.Database.Insert;
import com.example.mallsystem.Public.Database.Select;
import com.example.mallsystem.Public.Database.Update;
import com.example.mallsystem.Public.URL.URLStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewsDBManager extends DBManager implements Insert, Select, Update, SelectExtension {
    @Override
    public boolean insert(Map<String, Object> map) throws SQLException {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "insert into News(title,detail) values(?,?)";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            FileReader reader = new FileReader(new File((String)map.get("detail")));
            stat.setString(1, (String)map.get("title"));
            stat.setCharacterStream(2, reader);
            int result = stat.executeUpdate();
            System.out.println("success: insert to news. line:"+result);
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.close(null, stat, conn);
        }
        return false;
    }

    @Override
    public boolean insert(Object map) throws SQLException {
        return false;
    }

    public void update(Map<String, Object> map) throws SQLException {
        Connection conn = null;
        PreparedStatement stat = null;
        String sql = "update News set detail=? where id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);

            FileReader reader = new FileReader(new File("/Users/malbeshaba/Downloads/h2.htm"));
            stat.setCharacterStream(1, reader);
            stat.setInt(2, 1);
            int result = stat.executeUpdate();
            reader.close();
            System.out.println("success: update detail in news. line:"+result);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.close(null, stat, conn);
        }
    }

    @Override
    public <T> String select(String columns, String table, Map<String, T> where) {
        return null;
    }

    @Override
    public ArrayList<LinkedHashMap<String, Object>> selectAll() {
        ArrayList<LinkedHashMap<String, Object>> list = null;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select * from News";

        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            list = new ArrayList<>();
            getList(list, conn, stat, rs);
            System.out.println("success: select all from News. line:"+list.size());
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void getList(ArrayList<LinkedHashMap<String, Object>> list, Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        while (rs.next()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("id", rs.getInt("id"));
            map.put("title", rs.getString("title"));
            StringBuilder builder = new StringBuilder(URLStorage.baseURL + "/news/detail?news_id=" + rs.getInt("id"));
            map.put("detail", builder.toString());
            list.add(map);
        }
    }

    public String selectDetail(int id) {
        String sql = "select detail from News where id=?;";
        StringBuilder str = new StringBuilder();
        try {
            Connection conn = this.connect();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                str.append(rs.getString("detail"));
            }
            System.out.println("success: select detail from News. id:"+id);
            this.close(rs, stat, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public ArrayList<LinkedHashMap<String, Object>> selectHP(int num) {
        ArrayList<LinkedHashMap<String, Object>> list = null;
        try {
            list = new ArrayList<>();

            Connection conn = this.connect();

            String sql = "select * from News order by id desc limit ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, num);

            ResultSet rs = stmt.executeQuery();

            getList(list, conn, stmt, rs);

            System.out.println("success: select "+num+" news from News for home page. line:"+list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}