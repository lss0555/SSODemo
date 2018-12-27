package com.example.client.model;

import java.io.Serializable;

/**
 * @Description 用户类
 * @Author lss0555
 * @Date 2018/12/26/026 10:28
 **/
public class User implements Serializable {
    private String username;
    private String password;
    private String uuid;
    private int id;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
