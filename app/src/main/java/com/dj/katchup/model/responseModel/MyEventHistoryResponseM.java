package com.dj.katchup.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyEventHistoryResponseM {
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("status_name")
    @Expose
    private String statusName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
