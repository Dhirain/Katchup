package com.dj.katchup.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponseM {

    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("event_type")
    @Expose
    private String eventType;
    @SerializedName("event_category")
    @Expose
    private String eventCategory;
    @SerializedName("date_of_event")
    @Expose
    private String dateOfEvent;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    @Override
    public String toString() {
        return "HomeResponseM{" +
                "eventName='" + eventName + '\'' +
                ", eventId=" + eventId +
                ", imageUrl='" + imageUrl + '\'' +
                ", pincode='" + pincode + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventCategory='" + eventCategory + '\'' +
                ", dateOfEvent='" + dateOfEvent + '\'' +
                '}';
    }
}

