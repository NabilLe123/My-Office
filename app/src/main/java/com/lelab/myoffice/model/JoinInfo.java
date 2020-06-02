package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

public class JoinInfo {
    @SerializedName("meeting")
    private Meeting meeting;
    @SerializedName("attendee")
    private Attendee attendee;

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
}
