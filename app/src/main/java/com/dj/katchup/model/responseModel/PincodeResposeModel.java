package com.dj.katchup.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PincodeResposeModel {

    @SerializedName("pincodes")
    @Expose
    private ArrayList<String> pincodes;


    public ArrayList<String> getPincodes() {
        return pincodes;
    }

    public void setPincodes(ArrayList<String> pincodes) {
        this.pincodes = pincodes;
    }

    @Override
    public String toString() {
        return "PincodeResposeModel{" +
                "pincodes=" + pincodes.toString() +
                '}';
    }
}
