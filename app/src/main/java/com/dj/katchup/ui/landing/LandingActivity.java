package com.dj.katchup.ui.landing;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dj.katchup.R;
import com.dj.katchup.interfaces.EventSelectListerner;
import com.dj.katchup.model.responseModel.HomeResponseM;
import com.dj.katchup.network.NetworkService;
import com.dj.katchup.ui.base.BaseActivity;
import com.dj.katchup.ui.eventAdding.AddEventActivity1;
import com.dj.katchup.ui.eventDetail.EventDetailActivity;
import com.dj.katchup.ui.login.LoginActivity;
import com.dj.katchup.ui.myEvent.MyEventActivity;
import com.dj.katchup.ui.myTickets.MyTicketActivity;
import com.dj.katchup.utills.Constants;
import com.dj.katchup.utills.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LandingActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,  ResturantLandingAdapter.ListChangeListner, EventSelectListerner,  View.OnClickListener {
    private static final String TAG = "LandingActivity";
    private TextView emailIdTV, firstInitialTv;
    private FloatingActionButton fab;
    private RecyclerView event_recycle;
    private ResturantLandingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setUI();
        setUserDetail();
        intProgressbar();
        showProgress();
        initAdapter();
        getAllevent();
    }

    private void getAllevent() {
        NetworkService.instance().getHomeData().enqueue(new Callback<ArrayList<HomeResponseM>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeResponseM>> call, Response<ArrayList<HomeResponseM>> response) {
                hideProgress();
                Log.d(TAG, "onResponse: "+response.code());
                if(response.isSuccessful() && response.body()!=null) {
                    ArrayList<HomeResponseM> resturants = response.body();
                    Log.d(TAG, "onResponse: "+resturants.toString());
                    adapter.updateList(resturants);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HomeResponseM>> call, Throwable t) {
                hideProgress();
                t.printStackTrace();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void initAdapter() {
        event_recycle.setHasFixedSize(true);
        adapter = new ResturantLandingAdapter(this, this,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        event_recycle.setLayoutManager(manager);
        event_recycle.setAdapter(adapter);

    }

    private void setUserDetail() {
        String userName = SharedPreferenceManager.singleton().getString(Constants.USER_NAME);
        emailIdTV.setText(userName);
        if(!userName.isEmpty())
            firstInitialTv.setText(String.valueOf(userName.charAt(0)));

    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        emailIdTV = headerView.findViewById(R.id.tv_emailid);
        firstInitialTv = headerView.findViewById(R.id.tv_first_initial);

        navigationView.setNavigationItemSelectedListener(this);
        event_recycle = findViewById(R.id.event_recycle);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            this.finishAffinity();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_booking) {
            Intent intent = new Intent(this, MyEventActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_my_ticket) {
            Intent intent = new Intent(this, MyTicketActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_logout) {
            SharedPreferenceManager.singleton().save(Constants.USER_ID,null);
            SharedPreferenceManager.singleton().save(Constants.USER_NAME,null);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent myIntent = new Intent(this, AddEventActivity1.class);
                this.startActivity(myIntent);
                break;
        }
    }

    @Override
    public void onResturantClicked(HomeResponseM responseM, ImageView event_image) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LandingActivity.this, event_image, "transitionImage");
        Intent intent = new Intent(LandingActivity.this, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.IMAGE_URL , responseM.getImageUrl());
        intent.putExtra(EventDetailActivity.EVENT_ID , responseM.getEventId().toString());
        startActivity(intent,options.toBundle());
    }

    @Override
    public void updateCurrentList(List<HomeResponseM> model) {

    }
}
