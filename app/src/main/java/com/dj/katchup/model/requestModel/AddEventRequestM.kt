package com.dj.katchup.model.requestModel

data class AddEventRequestM(
        var eventname : String = "",
        var eventdescription : String = "",
        var date : String = "",
        var time : String = "",
        var eventtype : String = "",
        var eventcategory : String = "",
        var cost : String = "",
        var userid : Int = 0,
        var totalseats : String = "",
        var pincode : String = "",
        var premiumtype : String = "")