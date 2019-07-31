package com.dj.katchup.ui.myTickets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dj.katchup.R;

public class TicketItem extends RecyclerView.ViewHolder {
    public TextView event_name, ticket_date, ticket_time,ticket_id;
    public ImageView like;
    public RelativeLayout resturant_indicator;

    public TicketItem(View itemView) {
        super(itemView);
        event_name = itemView.findViewById(R.id.event_name);
        like = itemView.findViewById(R.id.like);
        resturant_indicator = itemView.findViewById(R.id.indicator);
        ticket_date = itemView.findViewById(R.id.ticket_date);
        ticket_time = itemView.findViewById(R.id.ticket_time);
        ticket_id = itemView.findViewById(R.id.ticket_id);
    }
}
