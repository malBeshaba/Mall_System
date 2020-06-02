package com.example.mallsystem.Display.goods.Service;

import com.example.mallsystem.Display.goods.DAO.Goods;
import com.example.mallsystem.Display.goods.DAO.SelectExtension;
import com.example.mallsystem.Public.Database.DBManager;
import com.example.mallsystem.Public.Database.Insert;
import com.example.mallsystem.Public.Database.Select;
import com.example.mallsystem.Public.URL.URLStorage;
import lombok.Cleanup;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.sql.*;
import java.util.*;

@Configuration
public class GoodsDBManager extends DBManager implements Select, Insert, SelectExtension {


    @Override
    public<T> String select(String columns, String table, Map<String, T> where) {
        return null;
    }

    public ArrayList<LinkedHashMap<String, Object>> selectGoodsDetail(int id) {
        ArrayList<LinkedHashMap<String, Object>> list = null;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select * from Goods where id=?";
        try {
            list = new ArrayList<>();
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            getList(list, conn, stat, rs);
            System.out.println("success: select from Goods. id:"+id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Goods selectDetail(int id) {
        @Cleanup
        Connection conn = null;
        @Cleanup
        PreparedStatement stat = null;
        @Cleanup
        ResultSet rs = null;
        Goods goods = null;
        String sql = "select * from Goods where id=?";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            goods = new Goods();
            while (rs.next()) {
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setBusiness(rs.getString("bussiness"));
                goods.setDetail(rs.getString("detail"));
                goods.setPrice(rs.getDouble("price"));
                goods.setVolume(rs.getInt("volume"));
                StringBuffer url =  new StringBuffer(URLStorage.baseURL + "/goods/image?goods_id="+id);
                goods.setImageUrl(url.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    public ArrayList<LinkedHashMap<String, Object>> selectAll() {
        ArrayList<LinkedHashMap<String, Object>> list = null;
        try {
            list = new ArrayList<>();

            Connection conn = this.connect();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from Goods");

            getList(list, conn, stmt, rs);

            System.out.println("success: select all from goods. line:"+list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Map<String, Object> map) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.connect();
            String sql = "insert into Goods(name,business,volume,price,detail,image) values(?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, (String)map.get("name"));
            stmt.setString(2, (String)map.get("business"));
            stmt.setInt(3, (int)map.get("volume"));
            stmt.setDouble(4, (double)map.get("price"));
            stmt.setString(5, (String)map.get("detail"));
            if (map.get("image") != null) {
                InputStream in = new  FileInputStream(new File((String)map.get("image")));
                stmt.setBinaryStream(6, in);
            }
            int result = stmt.executeUpdate();
            System.out.println("success: insert to goods. line:"+result);
            return true;
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            this.close(rs, stmt, conn);
        }
        return false;
    }

    @Override
    public boolean insert(Object map) throws SQLException {
        return false;
    }

    public byte[] getImage(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        byte[] buff = null;
        try {
            connection = this.connect();
            String sql = "select image from Goods where id=?";
            stat = connection.prepareStatement(sql);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            while (rs.next()) {
                InputStream in = rs.getAsciiStream("image");
                buff = new byte[in.available()];
                in.read(buff, 0, in.available());
                in.close();
            }
            System.out.println("success: select image from goods. id:"+id);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.close(rs, stat, connection);
        }
        return buff;
    }

    public void update(String image, int id) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = this.connect();
            String sql = "update Goods set image=? where id=?";
            stat = conn.prepareStatement(sql);
            File file = new File(image);
            InputStream in = new FileInputStream(file);
            stat.setBinaryStream(1, in);
            stat.setInt(2, id);
            stat.executeUpdate();
            System.out.println("success: update Goods set image . id:"+id);
            in.close();
            this.close(null, stat, conn);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getList(ArrayList<LinkedHashMap<String, Object>> list, Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        LinkedHashMap<String, Object> map;
        while (rs.next()) {
            map = new LinkedHashMap<>();
            map.put("id", rs.getInt("id"));
            map.put("name", rs.getString("name"));
            map.put("business", rs.getString("business"));
            map.put("volume", rs.getInt("volume"));
            map.put("price", rs.getDouble("price"));
            map.put("detail", rs.getString("detail"));
            StringBuffer url =  new StringBuffer(URLStorage.baseURL + "/goods/image?goods_id="+map.get("id"));
            map.put("image", url.toString());
            list.add(map);
        }
        this.close(rs, stmt, conn);
    }

    public ArrayList<LinkedHashMap<String, Object>> selectHP(int num) {
        ArrayList<LinkedHashMap<String, Object>> list = null;
        try {
            list = new ArrayList<>();

            Connection conn = this.connect();

            String sql = "select * from Goods order by id desc limit ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, num);

            ResultSet rs = stmt.executeQuery();

            LinkedHashMap<String, Object> map = null;
            while (rs.next()) {
                map = new LinkedHashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("name", rs.getString("name"));
                map.put("business", rs.getString("business"));
                map.put("price", rs.getDouble("price"));
                StringBuffer url =  new StringBuffer(URLStorage.baseURL + "/goods/image?goods_id="+map.get("id"));
                map.put("image", url.toString());
                list.add(map);
            }
            this.close(rs, stmt, conn);

            System.out.println("success: select "+num+" goods from Goods for home page. line:"+list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}