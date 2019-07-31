package com.dj.katchup.ui.myEvent;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dj.katchup.R;

public class StatusItem extends RecyclerView.ViewHolder {
    public TextView event_name, event_status;
    public ImageView like;
    public RelativeLayout resturant_indicator;

    public StatusItem(View itemView) {
        super(itemView);
        event_name =  itemView.findViewById(R.id.event_name);
        like = itemView.findViewById(R.id.like);
        resturant_indicator = itemView.findViewById(R.id.indicator);
        event_status = itemView.findViewById(R.id.event_status);
    }
}
