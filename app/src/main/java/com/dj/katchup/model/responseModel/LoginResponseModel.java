package com.dj.katchup.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("pincode")
    @Expose
    private String pincode;


    @SerializedName("message")
    @Expose
    private String message;

    public LoginResponseModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", pincode='" + pincode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
