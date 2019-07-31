package com.dj.katchup.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.dj.bonappetit.model.requestModel.LoginRequestM;
import com.dj.katchup.R;
import com.dj.katchup.model.responseModel.LoginResponseModel;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.landing.LandingActivity;
import com.dj.katchup.ui.register.RegisterActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Integer[] IMAGES= {R.drawable.login_viewpage1,R.drawable.login_viewpage2,R.drawable.login_viewpage3,R.drawable.login_viewpage4};
    private static final String TAG = "LoginActivity";
    private AppCompatEditText et_password, et_email;
    private Button b_register, b_login;
    private SwitchCompat switch_user;
    private ViewPager viewPager;
    private CircleIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideSoftKeyboard();
        setUI();
        validateUser();
    }

    private void validateUser() {

    }

    private void setUI() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        b_login = findViewById(R.id.button_signin);
        b_register = findViewById(R.id.button_register);
        viewPager = findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        b_login.setOnClickListener(this);
        b_register.setOnClickListener(this);

        viewPager.setAdapter(new LoginViewPagerAdapter(this, new ArrayList<Integer>(Arrays.asList(IMAGES))));
        indicator.setViewPager(viewPager);

        NUM_PAGES = IMAGES.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signin:
                if(et_email.getText() == null  && et_email.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please enter User id", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(et_password.getText() == null && et_password.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = et_email.getText().toString();
                String password = et_password.getText().toString();

               /* if(!EmailMatcher.isValidEmail(email)){
                    Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Call<LoginResponseModel> call = NetworkService.instance().login(new LoginRequestM(email,password));
                call.enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        Log.d(TAG, "onResponse: "+response.code());
                        if(response.isSuccessful() && response.body()!=null) {
                            LoginResponseModel responseModel = response.body();
                            SharedPreferenceManager.singleton().save(Constants.USER_ID, responseModel.getUser_id());
                            SharedPreferenceManager.singleton().save(Constants.USER_PINCODE,responseModel.getPincode());
                            SharedPreferenceManager.singleton().save(Constants.USER_EMAIL,responseModel.getEmail());
                            SharedPreferenceManager.singleton().save(Constants.USER_NAME,responseModel.getName());
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            Toast.makeText(LoginActivity.this, "Welcome to Katch Up", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(LoginActivity.this, LandingActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(myIntent);
                            finish();

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFailure: "+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
                break;

            case R.id.button_register:
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
                break;


        }
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
