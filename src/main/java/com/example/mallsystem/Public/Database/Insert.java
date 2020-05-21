package com.example.mallsystem.Public.Database;

import java.sql.SQLException;
import java.util.Map;

public interface Insert {
    public boolean insert(Map<String, Object> map) throws SQLException;
}
