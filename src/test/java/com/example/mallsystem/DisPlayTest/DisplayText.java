package com.example.mallsystem.DisPlayTest;

import com.example.mallsystem.Display.goods.DAO.GoodsDBManager;
import com.example.mallsystem.Display.news.DAO.NewsDBManager;

import java.io.*;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class DisplayText {
    public static void main(String[] args) throws SQLException, IOException {
//        InsertData insertData = new InsertData();
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "飞狐剃须刀");
//        map.put("business", "TS杂货店");
//        map.put("volume", 100);
//        map.put("price", 300.0);
//        map.put("detail", "童叟无欺");
//        boolean b = insertData.insert("", map);
//        System.out.println(b);
//        GoodsDBManager manager = new GoodsDBManager();
//        System.out.println(manager.selectAll());
//        DisplayData data = new DisplayData();
//        Gson gson = new Gson();
//        System.out.println(data.toJson());
//        byte[] buff = manager.getImage(1);
//        OutputStream out = new FileOutputStream(new File("/Users/malbeshaba/Downloads/1.png"));
//        out.write(buff, 0, buff.length);
//        out.close();
        NewsDBManager manager = new NewsDBManager();
//        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//        map.put("title", "自我中心亲密关系可视化");
//        map.put("detail", "/Users/malbeshaba/Downloads/h3.htm");
//        boolean b = manager.insert(map);
//        manager.update(map);
        System.out.println(manager.selectDetail(1));
    }
}