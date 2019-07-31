package com.dj.katchup.ui.myTickets;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.MyTicketRequestM;
import com.dj.katchup.model.requestModel.MyTicketResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketActivity extends BaseActivity {

    private static final String TAG = "MyTicketActivity";
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    ArrayList<MyTicketResponseM> myTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        setUI();
        initAdapter();
        intProgressbar();
        showProgress();
        getDataFromServer();
    }

    private void setUI() {
        recyclerView = findViewById(R.id.my_ticket_recycler);
    }

    private void initAdapter() {
        recyclerView.setHasFixedSize(true);
        ticketAdapter = new TicketAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(ticketAdapter);

    }

    private void getDataFromServer() {
        MyTicketRequestM requestM = new MyTicketRequestM(SharedPreferenceManager.singleton().getString(Constants.USER_ID));
        NetworkService.instance().getTickets(requestM).enqueue(new Callback<ArrayList<MyTicketResponseM>>() {
            @Override
            public void onResponse(Call<ArrayList<MyTicketResponseM>> call, Response<ArrayList<MyTicketResponseM>> response) {
                hideProgress();
                if (response != null && response.isSuccessful()) {
                    myTickets = response.body();
                    Log.d(TAG, "onResponse: "+myTickets.toString());
                    ticketAdapter.updateList(myTickets);

                } else {
                    makeToast("Something went wrong");
                    Log.d(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyTicketResponseM>> call, Throwable t) {
                hideProgress();
                makeToast("Something went wrong");
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


}
