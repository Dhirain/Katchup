package com.dj.katchup.ui.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dj.katchup.R;
import com.dj.katchup.ui.landing.LandingActivity;
import com.dj.katchup.ui.login.LoginActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private String userId;
    private boolean isActivityVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSharedPreferencedata();
    }

    private void getSharedPreferencedata() {
        userId = SharedPreferenceManager.singleton().getString(Constants.USER_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
        delayedHide(3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (userId== null || userId.isEmpty()) {
                        Log.d(TAG, "run: "+ userId);
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this , LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delayMillis);
    }

}
