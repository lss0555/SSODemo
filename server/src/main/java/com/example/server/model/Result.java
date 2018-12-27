package com.example.server.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 基础模型
 * @Author lss0555
 * @Date 2018/12/26/026 11:26
 **/
public class Result<T> implements Serializable {
    private int errorcode;
    private String errorMsg;
    private T model;
    private List<T> data;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
