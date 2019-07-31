package com.dj.katchup.model.requestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTicketResponseM {

    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "MyTicketResponseM{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", bookingId=" + bookingId +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}