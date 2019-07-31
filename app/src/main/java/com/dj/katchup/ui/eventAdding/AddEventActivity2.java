package com.dj.katchup.ui.eventAdding;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.PostEventRequestM;
import com.dj.katchup.model.responseModel.PostEventResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.approval.ApprovalActivity;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.ui.landing.LandingActivity;
import com.dj.katchup.ui.login.LoginActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity2 extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA = "EVENT1";
    private static final String TAG = "AddEventActivity2";
    private static final String CHANNEL_ID = "101";
    private AppCompatEditText et_cost, et_seats, et_address, et_pincode;
    private Button button_post;
    private Spinner s_event_type, s_premium_type, s_event_category;
    private String[] allEventType, allEventCategoery;
    private PostEventRequestM requestM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event2);
        getIntentData();
        getArraydata();
        setUI();
    }

    private void getArraydata() {
        allEventType = this.getResources().getStringArray(R.array.event_type_entries);
        allEventCategoery = this.getResources().getStringArray(R.array.event_category_entries);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        requestM = bundle.getParcelable(EXTRA);
        Log.d(TAG, "getIntentData: " + requestM.toString());
    }

    private void setUI() {
        et_cost = findViewById(R.id.et_cost);
        et_seats = findViewById(R.id.et_seats);
        et_address = findViewById(R.id.et_address);
        et_pincode = findViewById(R.id.et_pincode);
        button_post = findViewById(R.id.button_post_event);
        s_event_type = findViewById(R.id.spinner_eventtype);
        s_premium_type = findViewById(R.id.spinner_premiumtype);
        s_event_category = findViewById(R.id.spinner_eventcategoery);

        setTitleWithBackPress("Add event");

        button_post.setOnClickListener(this);

        intProgressbar();

        s_event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)//normal event
                {
                    et_cost.setVisibility(View.GONE);
                    s_premium_type.setVisibility(View.GONE);
                } else if (position == 2) {
                    et_cost.setVisibility(View.VISIBLE);
                    s_premium_type.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_event:
                validateData();
                break;
        }
    }

    private void validateData() {
        showProgress();
        boolean isValid = true;
        String eventType = null;
        String premiumType = null;
        String eventCategory = null;
        String eventAddress = null;
        float cost = 0;
        int numberOfSeats, eventPincode;

        //cost
        String costString = et_cost.getText().toString();
        try {
            if (costString.isEmpty()) {
                cost = 0;
                et_cost.setText("0");
            } else {
                cost = Float.valueOf(costString);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isValid = false;
            et_cost.setError("Enter valid Cost");
        }

        //eventType
        int eventTypePos = s_event_type.getSelectedItemPosition();
        switch (eventTypePos) {
            case 0:
                makeToast("Please select Event type");
                isValid = false;
                break;
            case 1:
                eventType = "Normal";
                if (cost > 0) {
                    makeToast("Normal event cannot have cost");
                    isValid = false;
                }
                break;
            case 2:
                eventType = "Premium";
                break;
        }

        //premium type
        int premiumTypePos = s_premium_type.getSelectedItemPosition();
        switch (premiumTypePos) {
            case 0:
                if (eventTypePos == 2) {
                    makeToast("Please select premium type");
                    isValid = false;
                    break;
                } else {
                    premiumType = "Normal";
                    break;
                }
            case 1:
                premiumType = "Bronze";
                break;
            case 2:
                premiumType = "Silver";
                break;
            case 3:
                premiumType = "Gold";
        }

        //event category
        int eventCatogeoryPos = s_event_category.getSelectedItemPosition();
        if (eventCatogeoryPos == 0) {
            makeToast("Please select the event Category");
            isValid = false;
        } else {
            eventCategory = allEventCategoery[eventCatogeoryPos];
        }

        //seats
        String seatsString = et_seats.getText().toString();
        /*try {
            numberOfSeats = Integer.valueOf(costString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isValid = false;
            et_seats.setError("Enter valid Seats");
        }*/

        //Address
        eventAddress = et_address.getText().toString();
        if (eventAddress == null || eventAddress.isEmpty()) {
            et_address.setError("Enter valid address");
            isValid = false;
        }

        //Pincode
        String pincodeString = et_pincode.getText().toString();
        try {
            eventPincode = Integer.valueOf(pincodeString);
            if (eventPincode > 99999 || eventPincode < 10000) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isValid = false;
            et_pincode.setError("Enter valid Pincode");
        }
        if (isValid) {
            requestM.setEventtype(eventType);
            requestM.setCost(costString);
            requestM.setEventcategory(eventCategory);
            requestM.setTotalseats(seatsString);
            requestM.setAddress(eventAddress);
            requestM.setPremiumtype(premiumType);
            requestM.setPincode(pincodeString);
            requestM.setUserid(String.valueOf(SharedPreferenceManager.singleton().getString(Constants.USER_ID)));
            Log.d(TAG, "validateData: " + requestM.toString());
            makePostNetworkCall();
        }
        hideProgress();
    }

    private void makePostNetworkCall() {
        NetworkService.instance().postEvent(requestM).enqueue(new Callback<PostEventResponseM>() {
            @Override
            public void onResponse(Call<PostEventResponseM> call, Response<PostEventResponseM> response) {
                if (response.isSuccessful() && response.body() != null) {
                    makeNotification(requestM);
                    Intent intent = new Intent(AddEventActivity2.this, ApprovalActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddEventActivity2.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<PostEventResponseM> call, Throwable t) {
                makeToast("Something went wrong");
                t.printStackTrace();
            }
        });
    }

    private void makeNotification(PostEventRequestM requestM) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(requestM.getEventname())
                .setContentText(requestM.getEventdescription())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(requestM.getEventdescription() + " " + requestM.getDate()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = requestM.getEventname();
            String description = requestM.getEventdescription();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(101, builder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

