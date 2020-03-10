package com.icd.wksh.payloads;

import com.icd.wksh.models.User;

import java.util.List;

public class GetUsersResponse {
    private int totalRecord;
    private List<User> userList;

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "GetUsersResponse{" +
                "totalRecord=" + totalRecord +
                ", userList=" + userList +
                '}';
    }
}
