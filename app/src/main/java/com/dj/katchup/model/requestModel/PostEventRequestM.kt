package com.dj.katchup.model.requestModel

import android.os.Parcel
import android.os.Parcelable

data class PostEventRequestM (
        var eventname           : String? = null,
        var address             : String? = null,
        var eventdescription    : String? = null,
        var date                : String? = null,
        var time                : String? = null,
        var eventtype           : String? = null,
        var eventcategory       : String? = null,
        var cost                : String? = null,
        var userid              : String? = null,
        var totalseats          : String? = null,
        var pincode             : String? = null,
        var premiumtype         : String? = null): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventname)
        parcel.writeString(address)
        parcel.writeString(eventdescription)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(eventtype)
        parcel.writeString(eventcategory)
        parcel.writeString(cost)
        parcel.writeString(userid)
        parcel.writeString(totalseats)
        parcel.writeString(pincode)
        parcel.writeString(premiumtype)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostEventRequestM> {
        override fun createFromParcel(parcel: Parcel): PostEventRequestM {
            return PostEventRequestM(parcel)
        }

        override fun newArray(size: Int): Array<PostEventRequestM?> {
            return arrayOfNulls(size)
        }
    }
}

