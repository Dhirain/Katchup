package com.dj.katchup.ui.eventAdding;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.PostEventRequestM;
import com.dj.katchup.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity1 extends BaseActivity implements View.OnClickListener {
    private AppCompatEditText et_name, et_date, et_time, et_eventDescription;
    private Button button_next;
    private DatePickerDialog.OnDateSetListener date;
    private String eventName, eventDescription, eventDate, eventTime;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event1);
        setDatePicker();
        setUI();
    }

    private void setUI() {
        et_name = findViewById(R.id.et_name);
        et_date = findViewById(R.id.et_date);
        et_time = findViewById(R.id.et_time);
        button_next = findViewById(R.id.button_next);
        et_eventDescription = findViewById(R.id.et_description);
        setTitleWithBackPress("Add event");

        et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openDatePicker();
                }
            }
        });

        et_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openTimePicker();
                }
            }
        });
        et_date.setOnClickListener(this);
        et_time.setOnClickListener(this);

        button_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date:
                openDatePicker();
                break;

            case R.id.et_time:
                openTimePicker();
                break;

            case R.id.button_next:
                validateData();
                break;
        }
    }

    private void validateData() {
        boolean valid = true;
        eventName = et_name.getText().toString();
        eventDescription = et_eventDescription.getText().toString();
        eventDate = et_date.getText().toString();
        eventTime = et_time.getText().toString();

        if(eventName==null || eventName.isEmpty()){
            et_name.setError("Please enter the event name");
            valid = false;
        }else {
            et_name.setError(null);
        }

        if(eventDescription==null || eventDescription.isEmpty()){
            et_eventDescription.setError("Please enter the event description");
            valid = false;
        }else {
            et_eventDescription.setError(null);
        }

        if(eventDate==null || eventDate.isEmpty()){
            et_date.setError("Please enter the event date");
            valid = false;
        }


        if(eventTime==null || eventTime.isEmpty()){
            et_time.setError("Please enter the event date");
            valid = false;
        }

        if(valid){
            PostEventRequestM requestM = new PostEventRequestM(
                    eventName ,
                    null ,
                    eventDescription,
                    eventDate ,
                    eventTime,
                    null , null, null, null, null, null, null);
            Intent intent = new Intent(AddEventActivity1.this , AddEventActivity2.class);
            intent.putExtra(AddEventActivity2.EXTRA, requestM);
            this.startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
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


    private void openTimePicker() {
        et_time.setError(null);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void openDatePicker() {
        et_date.setError(null);
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDatePicker() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };
    }

    private void updateDateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

}
