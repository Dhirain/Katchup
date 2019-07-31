package com.dj.katchup.ui.landing;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dj.katchup.R;


public class ResturantLandingItem extends RecyclerView.ViewHolder {
    public TextView event_name, event_pincode, event_date;
    public AppCompatImageView like;
    public ImageView event_image;
    public LinearLayout meta;
    public RelativeLayout parent;
    public CardView cardView;
    public RelativeLayout event_indicator;

    public ResturantLandingItem(View itemView) {
        super(itemView);
        event_name = (TextView) itemView.findViewById(R.id.event_name);
        event_pincode = (TextView) itemView.findViewById(R.id.event_pincode);
        event_date = (TextView) itemView.findViewById(R.id.event_date);
        event_image = (ImageView) itemView.findViewById(R.id.event_image);
        like = itemView.findViewById(R.id.like);
        event_indicator = itemView.findViewById(R.id.event_indicator);

    }
}
