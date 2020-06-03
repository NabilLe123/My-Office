package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JoinInfoResponse implements Serializable {
    @SerializedName("joininfo")
    private JoinInfo joinInfo;

    public JoinInfo getJoinInfo() {
        return joinInfo;
    }

    public void setJoinInfo(JoinInfo joinInfo) {
        this.joinInfo = joinInfo;
    }
}
