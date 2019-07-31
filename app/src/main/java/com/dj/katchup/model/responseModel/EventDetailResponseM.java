package com.dj.katchup.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetailResponseM {

    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("address")
    @Expose
    private String address;
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
    @SerializedName("time_of_the_event")
    @Expose
    private String timeOfTheEvent;
    @SerializedName("cost_of_ticket")
    @Expose
    private String costOfTicket;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("total_seats")
    @Expose
    private Integer totalSeats;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("premium_type")
    @Expose
    private String premiumType;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_description")
    @Expose
    private String eventDescription;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTimeOfTheEvent() {
        return timeOfTheEvent;
    }

    public void setTimeOfTheEvent(String timeOfTheEvent) {
        this.timeOfTheEvent = timeOfTheEvent;
    }

    public String getCostOfTicket() {
        return costOfTicket;
    }

    public void setCostOfTicket(String costOfTicket) {
        this.costOfTicket = costOfTicket;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}