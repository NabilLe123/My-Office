package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

public class JoinInfoResponse {
    @SerializedName("joininfo")
    private JoinInfo joinInfo;

    public JoinInfo getJoinInfo() {
        return joinInfo;
    }

    public void setJoinInfo(JoinInfo joinInfo) {
        this.joinInfo = joinInfo;
    }
}
