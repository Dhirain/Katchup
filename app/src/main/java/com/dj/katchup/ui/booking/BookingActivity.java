package com.dj.katchup.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.BookRequestM;
import com.dj.katchup.model.responseModel.BookingResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.ui.landing.LandingActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingActivity extends BaseActivity {

    private static final String TAG = "BookingActivity";
    public static final String EVENT_ID = "EVENT_ID";
    public static final String EVENT_TYPE = "EVENT_TYPE";
    public static final String EVENT_COST = "EVENT_COST";
    private int eventId;
    private ImageView qrIv;
    private TextView bookingTv;
    private BookingResponseM responseM;
    private String event_type;
    private String cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity);
        setUI();
        getIntentData();

        intProgressbar();
        showProgress();
        getBookingId();


    }

    private void setUI() {
        qrIv = findViewById(R.id.qr_iv);
        bookingTv = findViewById(R.id.booking_tv);
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this , LandingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getBookingId() {
        BookRequestM requestM = new BookRequestM(eventId, SharedPreferenceManager.singleton().getString(Constants.USER_ID) , "1");
        NetworkService.instance().bookEvent(requestM).enqueue(new Callback<BookingResponseM>() {
            @Override
            public void onResponse(Call<BookingResponseM> call, Response<BookingResponseM> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    responseM = response.body();
                    showData(responseM);
                } else {
                    hideProgress();
                    makeToast("Something went wrong");
                    Log.d(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<BookingResponseM> call, Throwable t) {
                hideProgress();
                makeToast("Something went wrong");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void showData(BookingResponseM responseM) {
        String message;
        if(!event_type.equalsIgnoreCase("normal")){
            qrIv.setImageDrawable(getResources().getDrawable(R.drawable.bank_detail));
            message = "Please transfer the amount" + cost + " to the above bank detail. We will share the booking ID and QR code once we receive the payment via email";
        }
        else {
            qrIv.setImageDrawable(getResources().getDrawable(R.drawable.qr_code));
            message = "Your booking id is "+String.valueOf(responseM.getBookingId())+ ". \n Please show this QR code at the venue!.";
        }
        qrIv.setVisibility(View.VISIBLE);
        bookingTv.setVisibility(View.VISIBLE);
        bookingTv.setText(message);
    }

    private void getIntentData() {
        eventId = getIntent().getIntExtra(EVENT_ID,0);
        event_type = getIntent().getStringExtra(EVENT_TYPE);
        cost = getIntent().getStringExtra(EVENT_COST);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookingActivity.this , LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}