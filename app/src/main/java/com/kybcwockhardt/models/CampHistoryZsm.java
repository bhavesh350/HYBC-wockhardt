package com.kybcwockhardt.models;

import java.util.List;

public class CampHistoryZsm {
    private boolean status;
    private String message;
    private List<MyTeam.Data> data;

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

    public List<MyTeam.Data> getData() {
        return data;
    }

    public void setData(List<MyTeam.Data> data) {
        this.data = data;
    }
}
