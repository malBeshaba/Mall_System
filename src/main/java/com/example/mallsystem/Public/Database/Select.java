package com.example.mallsystem.Public.Database;

import java.util.ArrayList;
import java.util.Map;

public interface Select {
    public abstract<T> String select(String columns, String table, Map<String, T> where);
    public abstract ArrayList selectAll();
}
