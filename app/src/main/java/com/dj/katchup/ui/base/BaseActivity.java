package com.dj.katchup.ui.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dj.katchup.R;


public class BaseActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;

    public void setTitleWithBackPress(String title) {
        Drawable cd = new ColorDrawable(getResources().getColor(R.color.primary));
        ActionBar mActionBar = getSupportActionBar();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(17, 17, 17));
        }

        if (mActionBar != null) {
            //mActionBar.setHomeAsUpIndicator(R.drawable.back);
            mActionBar.setBackgroundDrawable(cd);
            mActionBar.setTitle(title);
            mActionBar.setDisplayHomeAsUpEnabled(true); //to activate back pressed on home button press
            mActionBar.setDisplayShowHomeEnabled(false); //
        }

    }

    public void intProgressbar() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgress() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void makeToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
