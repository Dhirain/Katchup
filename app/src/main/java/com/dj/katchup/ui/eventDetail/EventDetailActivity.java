package com.dj.katchup.ui.eventDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.EventDetailRequestM;
import com.dj.katchup.model.responseModel.EventDetailResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.approval.ApprovalActivity;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.ui.booking.BookingActivity;
import com.dj.katchup.ui.eventAdding.AddEventActivity2;
import com.dj.katchup.utills.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "EventDetailActivity";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String EVENT_ID = "EVENT_ID";
    private String eventId, imageURL;
    private ProgressBar progressBar;
    private LinearLayout eventDetailLL;
    private ImageView eventImageIV;
    private TextView eventNameTV,eventDescriptionTV, eventCategoryTV, eventCostTV, eventSeatsTV, eventDateTV, eventTimeTV, eventAddressTV, eventPincodeTV;
    private EventDetailResponseM responseM;
    private Button eventBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getIntentData();
        setUI();
        showLocalProgress();
        getEventDescrpition();
    }

    private void setUI() {
        eventDetailLL       =findViewById(R.id.event_detail_ll);
        progressBar         =findViewById(R.id.progressBar);
        eventImageIV        =findViewById(R.id.event_image);
        eventNameTV         =findViewById(R.id.event_name);
        eventDescriptionTV  =findViewById(R.id.event_description);
        eventCategoryTV     =findViewById(R.id.event_category);
        eventCostTV         =findViewById(R.id.event_cost);
        eventSeatsTV        =findViewById(R.id.event_seats);
        eventDateTV         =findViewById(R.id.event_date);
        eventTimeTV         =findViewById(R.id.event_time);
        eventAddressTV      =findViewById(R.id.event_address);
        eventPincodeTV      =findViewById(R.id.event_pincode);
        eventBookingButton  = findViewById(R.id.event_book);

        eventBookingButton.setOnClickListener(this);
        ImageUtils.setImage(this, imageURL, eventImageIV, R.drawable.place_holder);
    }

    private void showLocalProgress() {
        progressBar.setVisibility(View.VISIBLE);
        eventDetailLL.setVisibility(View.GONE);
    }

    private void hideLocalProgress() {
        progressBar.setVisibility(View.GONE);
        eventDetailLL.setVisibility(View.VISIBLE);
    }

    private void getIntentData() {
        eventId = getIntent().getStringExtra(EVENT_ID);
        imageURL = getIntent().getStringExtra(IMAGE_URL);
        Log.d(TAG, "getIntentData: eventID and imageURL" + eventId + imageURL);

    }

    private void getEventDescrpition() {
        final EventDetailRequestM requestM = new EventDetailRequestM(eventId);
        NetworkService.instance().getEventDetail(requestM).enqueue(new Callback<EventDetailResponseM>() {
            @Override
            public void onResponse(Call<EventDetailResponseM> call, Response<EventDetailResponseM> response) {
                hideLocalProgress();
                if (response.isSuccessful() && response.body() != null) {
                    responseM = response.body();
                    showData(responseM);
                } else {
                    makeToast("Something went wrong");
                    Log.d(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<EventDetailResponseM> call, Throwable t) {
                hideLocalProgress();
                makeToast("Something went wrong");
                t.printStackTrace();
            }
        });
    }

    private void showData(EventDetailResponseM event) {
        eventNameTV.setText(event.getEventName());
        eventDescriptionTV.setText(event.getEventDescription());
        eventCategoryTV.setText(event.getEventCategory());
        eventCostTV.setText(event.getCostOfTicket());
        eventSeatsTV.setText(event.getTotalSeats()+"");
        eventTimeTV.setText(event.getTimeOfTheEvent());
        eventAddressTV.setText(event.getAddress());
        eventPincodeTV.setText(event.getPincode());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EEE, MMM d, ''yy");
        try {
            Date date = sdf.parse(event.getDateOfEvent());
            eventDateTV.setText(output.format(date));
        } catch (ParseException e) {
            eventDateTV.setText(event.getDateOfEvent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.event_book){
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra(BookingActivity.EVENT_ID ,responseM.getEventId());
            intent.putExtra(BookingActivity.EVENT_COST ,responseM.getCostOfTicket());
            intent.putExtra(BookingActivity.EVENT_TYPE ,responseM.getEventType());
            startActivity(intent);
        }
    }
}
