package com.dj.katchup.network;


import com.dj.bonappetit.model.requestModel.LoginRequestM;
import com.dj.bonappetit.model.requestModel.RegisterRequestM;
import com.dj.katchup.model.requestModel.BookRequestM;
import com.dj.katchup.model.requestModel.EventDetailRequestM;
import com.dj.katchup.model.requestModel.HomeRequestM;
import com.dj.katchup.model.requestModel.MyEventHistoryRequestM;
import com.dj.katchup.model.requestModel.MyTicketRequestM;
import com.dj.katchup.model.requestModel.MyTicketResponseM;
import com.dj.katchup.model.requestModel.PostEventRequestM;
import com.dj.katchup.model.responseModel.BookingResponseM;
import com.dj.katchup.model.responseModel.EventDetailResponseM;
import com.dj.katchup.model.responseModel.HomeResponseM;
import com.dj.katchup.model.responseModel.LoginResponseModel;
import com.dj.katchup.model.responseModel.MyEventHistoryResponseM;
import com.dj.katchup.model.responseModel.PincodeResposeModel;
import com.dj.katchup.model.responseModel.PostEventResponseM;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Dhirain Jain on 14-09-2017.
 */

public interface NetworkClient {

    @POST("/login")
    Call<LoginResponseModel> login(@Body LoginRequestM loginModel);

    @GET("/getpincode")
    Call<PincodeResposeModel> getPincode();

    @POST("/signup")
    Call<LoginResponseModel> postRegister(@Body RegisterRequestM registerRequestM);

    @POST("/postevent")
    Call<PostEventResponseM> postEvent(@Body PostEventRequestM requestM);

    @POST("/getallevents")
    Call<ArrayList<HomeResponseM>> getHomeData(@Body HomeRequestM requestM);

    @POST("/getallevents")
    Call<ArrayList<HomeResponseM>> getHomeData();

    @POST("/getevent")
    Call<EventDetailResponseM> getEventDetail(@Body EventDetailRequestM requestM);

    @POST("/registerevent")
    Call<BookingResponseM> bookEvent(@Body BookRequestM requestM);

    @POST("/geteventstatus")
    Call<ArrayList<MyEventHistoryResponseM>> getMyEventHistory(@Body MyEventHistoryRequestM requestM);

    @POST("/getuserbookingstatus")
    Call<ArrayList<MyTicketResponseM>> getTickets(@Body MyTicketRequestM requestM);


}
