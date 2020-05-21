package com.example.mallsystem.Display.goods.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface SelectExtension {
    public void getList(ArrayList<LinkedHashMap<String, Object>> list, Connection conn, Statement stmt, ResultSet rs) throws SQLException;
    public ArrayList selectHP(int num);
}