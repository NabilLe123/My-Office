package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JoinInfo implements Serializable {
    @SerializedName("meeting")
    private MeetingModel meeting;
    @SerializedName("attendee")
    private AttendeeModel attendee;

    public MeetingModel getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingModel meeting) {
        this.meeting = meeting;
    }

    public AttendeeModel getAttendee() {
        return attendee;
    }

    public void setAttendee(AttendeeModel attendee) {
        this.attendee = attendee;
    }
}
