package com.example.mallsystem.Display.search.DAO;

import com.example.mallsystem.Public.Database.DBManager;
import com.example.mallsystem.Public.Database.Select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchDBManager extends DBManager {


    public ArrayList<ArrayList<LinkedHashMap<String, Object>>> blurSelect(String param) {
        ArrayList<ArrayList<LinkedHashMap<String, Object>>> lists = new ArrayList<>();
        lists.add(goodsBlurSelect(param));
        lists.add(newsBlurSelect(param));
        return lists;
    }

    public ArrayList<LinkedHashMap<String, Object>> goodsBlurSelect(String param) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        ArrayList<LinkedHashMap<String, Object>> arrayList = null;
        String sql1 = "select id as goods_id, name as goods_name from Goods where name like ?;";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql1);
            stat.setString(1, "'%" + param + "%'");
            rs = stat.executeQuery();
            arrayList = new ArrayList<>();
            while (rs.next()) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("goods_id", rs.getInt("goods_id"));
                map.put("goods_name", rs.getString("goods_name"));
                arrayList.add(map);
            }
            rs.close();
            stat.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<LinkedHashMap<String, Object>> newsBlurSelect(String param) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        ArrayList<LinkedHashMap<String, Object>> arrayList = null;
        String sql1 = "select id as news_id, name as news_name from News where name like ?;\"";
        try {
            conn = this.connect();
            stat = conn.prepareStatement(sql1);
            stat.setString(1, "'%" + param + "%'");
            rs = stat.executeQuery();
            arrayList = new ArrayList<>();
            while (rs.next()) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("news_id", rs.getInt("news_id"));
                map.put("news_name", rs.getString("news_name"));
                arrayList.add(map);
            }
            rs.close();
            stat.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}