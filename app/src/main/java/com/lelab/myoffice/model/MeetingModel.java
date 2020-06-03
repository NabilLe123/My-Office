package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MeetingModel implements Serializable {
    @SerializedName("externalMeetingId")
    private String externalMeetingId;
    @SerializedName("mediaPlacement")
    private MediaPlacementVal mediaPlacement;
    @SerializedName("mediaRegion")
    private String mediaRegion;
    @SerializedName("meetingId")
    private String meetingId;

    public String getExternalMeetingId() {
        return externalMeetingId;
    }

    public void setExternalMeetingId(String externalMeetingId) {
        this.externalMeetingId = externalMeetingId;
    }

    public MediaPlacementVal getMediaPlacement() {
        return mediaPlacement;
    }

    public void setMediaPlacement(MediaPlacementVal mediaPlacement) {
        this.mediaPlacement = mediaPlacement;
    }

    public String getMediaRegion() {
        return mediaRegion;
    }

    public void setMediaRegion(String mediaRegion) {
        this.mediaRegion = mediaRegion;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
}
