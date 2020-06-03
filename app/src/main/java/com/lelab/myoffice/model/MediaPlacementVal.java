package com.lelab.myoffice.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaPlacementVal implements Serializable {
    @SerializedName("audioFallbackUrl")
    private String audioFallbackUrl;
    @SerializedName("audioHostUrl")
    private String audioHostUrl;
    @SerializedName("screenDataUrl")
    private String screenDataUrl;
    @SerializedName("screenSharingUrl")
    private String screenSharingUrl;
    @SerializedName("screenViewingUrl")
    private String screenViewingUrl;
    @SerializedName("signalingUrl")
    private String signalingUrl;
    @SerializedName("turnControlUrl")
    private String turnControlUrl;

    public String getAudioFallbackUrl() {
        return audioFallbackUrl;
    }

    public void setAudioFallbackUrl(String audioFallbackUrl) {
        this.audioFallbackUrl = audioFallbackUrl;
    }

    public String getAudioHostUrl() {
        return audioHostUrl;
    }

    public void setAudioHostUrl(String audioHostUrl) {
        this.audioHostUrl = audioHostUrl;
    }

    public String getScreenDataUrl() {
        return screenDataUrl;
    }

    public void setScreenDataUrl(String screenDataUrl) {
        this.screenDataUrl = screenDataUrl;
    }

    public String getScreenSharingUrl() {
        return screenSharingUrl;
    }

    public void setScreenSharingUrl(String screenSharingUrl) {
        this.screenSharingUrl = screenSharingUrl;
    }

    public String getScreenViewingUrl() {
        return screenViewingUrl;
    }

    public void setScreenViewingUrl(String screenViewingUrl) {
        this.screenViewingUrl = screenViewingUrl;
    }

    public String getSignalingUrl() {
        return signalingUrl;
    }

    public void setSignalingUrl(String signalingUrl) {
        this.signalingUrl = signalingUrl;
    }

    public String getTurnControlUrl() {
        return turnControlUrl;
    }

    public void setTurnControlUrl(String turnControlUrl) {
        this.turnControlUrl = turnControlUrl;
    }
}
