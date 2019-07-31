package com.dj.katchup.ui.myEvent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.MyEventHistoryRequestM;
import com.dj.katchup.model.responseModel.MyEventHistoryResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventActivity extends BaseActivity {

    private static final String TAG = "MyEventActivity";
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    ArrayList<MyEventHistoryResponseM> myEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        setUI();
        initAdapter();
        intProgressbar();
        showProgress();
        getDataFromServer();
    }

    private void setUI() {
        recyclerView = findViewById(R.id.my_event_recycle);
    }

    private void initAdapter() {
        recyclerView.setHasFixedSize(true);
        menuAdapter = new MenuAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(menuAdapter);

    }

    private void getDataFromServer() {
        MyEventHistoryRequestM requestM = new MyEventHistoryRequestM(SharedPreferenceManager.singleton().getString(Constants.USER_ID));
        NetworkService.instance().getMyEventHistory(requestM).enqueue(new Callback<ArrayList<MyEventHistoryResponseM>>() {
            @Override
            public void onResponse(Call<ArrayList<MyEventHistoryResponseM>> call, Response<ArrayList<MyEventHistoryResponseM>> response) {
                hideProgress();
                if (response != null && response.isSuccessful()) {
                    myEvents = response.body();
                    menuAdapter.updateList(myEvents);
                } else {
                    makeToast("Something went wrong");
                    Log.d(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyEventHistoryResponseM>> call, Throwable t) {
                hideProgress();
                makeToast("Something went wrong");
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


}
