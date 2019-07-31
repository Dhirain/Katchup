package com.dj.katchup.ui.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.dj.bonappetit.model.requestModel.RegisterRequestM;
import com.dj.katchup.R;
import com.dj.katchup.model.responseModel.LoginResponseModel;
import com.dj.katchup.model.responseModel.PincodeResposeModel;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.ui.landing.LandingActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.EmailMatcher;
import com.dj.katchup.utills.SharedPreferenceManager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private SearchableSpinner searchableSpinner;
    private EditText et_name, et_email, et_contact, et_password, et_repassword;
    private Button button_register;
    private ArrayList<String> pincodes =new ArrayList<>();
    private String name,email, contact, password, repassword,pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUI();
        intProgressbar();
        showProgress();
        fetchPincode();
    }

    private void setUI() {
        searchableSpinner = findViewById(R.id.sipnner_location);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_re_password);
        button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

    }

    private void fetchPincode() {
        NetworkService.instance().getPincode().enqueue(new Callback<PincodeResposeModel>() {
            @Override
            public void onResponse(Call<PincodeResposeModel> call, Response<PincodeResposeModel> response) {
                hideProgress();
                if (response.isSuccessful() && response.body()!=null) {
                    PincodeResposeModel pincodeResposeModel = response.body();
                    pincodes = pincodeResposeModel.getPincodes();
                    setSpinnerAdapter();

                    Log.d(TAG, "onResponse: "+ pincodeResposeModel.getPincodes());
                }
            }

            @Override
            public void onFailure(Call<PincodeResposeModel> call, Throwable t) {
                hideProgress();
                t.printStackTrace();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void setSpinnerAdapter() {
        SpinnerAdapter adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, pincodes);
        searchableSpinner.setAdapter(adapter);
    }

    private boolean isvalidEmail(String email){
        return EmailMatcher.isValidEmail(email);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                getEditTextDetail();
                if (name == null || name.isEmpty()) {
                    et_name.setError("Please enter valid name");
                    return;
                }
                if (contact == null || contact.isEmpty()) {
                    et_contact.setError("Please enter valid contact");
                    return;
                }
                if (password == null || password.isEmpty()) {
                    et_password.setError("Please enter valid password");
                    return;
                }
                if (repassword == null || repassword.isEmpty()) {
                    et_repassword.setError("Please enter valid password");
                    return;
                }
                if (!password.equals(repassword)) {
                    makeToast("Both password did not match");
                    return;
                }
                if (email==null || email.isEmpty()|| !isvalidEmail(email)){
                    et_email.setError("Please enter valid email");
                    return;
                }
                showProgress();
                RegisterRequestM registerRequestM = new RegisterRequestM(name,password,pincode,contact,email);
                NetworkService.instance().postRegister(registerRequestM).enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        hideProgress();
                        if(response.isSuccessful() && response.body()!=null) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            LoginResponseModel responseModel = response.body();
                            Log.d(TAG, "onResponse: "+responseModel.toString());
                            SharedPreferenceManager.singleton().save(Constants.USER_ID, responseModel.getUser_id());
                            SharedPreferenceManager.singleton().save(Constants.USER_PINCODE,responseModel.getPincode());
                            SharedPreferenceManager.singleton().save(Constants.USER_EMAIL,responseModel.getEmail());
                            SharedPreferenceManager.singleton().save(Constants.USER_NAME,responseModel.getName());
                            Toast.makeText(RegisterActivity.this, "Your userid is"+responseModel.getUser_id(), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(RegisterActivity.this, LandingActivity.class);
                            startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        hideProgress();
                        t.printStackTrace();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });

                break;
        }
    }

    private void getEditTextDetail() {
        name = et_name.getText().toString();
        email = et_email.getText().toString();
        contact = et_contact.getText().toString();
        password = et_password.getText().toString();
        repassword = et_repassword.getText().toString();
        pincode = searchableSpinner.getSelectedItem().toString();
    }
}

