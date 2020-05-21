package com.example.mallsystem.Public.Json;

import com.google.gson.annotations.SerializedName;

public class BaseJSON {
    @SerializedName("error_code")
    protected int errorcode;

    protected String message;

    protected Object data;

    public BaseJSON(String msg, int err, Object data) {
        this.message = msg;
        this.errorcode = err;
        this.data = data;
    }
}