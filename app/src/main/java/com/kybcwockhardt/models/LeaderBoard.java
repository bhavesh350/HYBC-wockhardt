package com.kybcwockhardt.models;

import java.util.List;

public class LeaderBoard {
    private boolean status;
    private String message;
    private List<User.Data> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User.Data> getData() {
        return data;
    }

    public void setData(List<User.Data> data) {
        this.data = data;
    }
}
