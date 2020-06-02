package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

public class Attendee {
    @SerializedName("attendeeId")
    private String attendeeId;
    @SerializedName("externalUserId")
    private String externalUserId;
    @SerializedName("joinToken")
    private String joinToken;

    public String getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(String attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getJoinToken() {
        return joinToken;
    }

    public void setJoinToken(String joinToken) {
        this.joinToken = joinToken;
    }
}
