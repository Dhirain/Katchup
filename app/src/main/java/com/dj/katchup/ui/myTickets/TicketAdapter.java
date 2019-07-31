package com.dj.katchup.ui.myTickets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dj.katchup.R;
import com.dj.katchup.model.requestModel.MyTicketResponseM;
import com.dj.katchup.model.responseModel.MyEventHistoryResponseM;
import com.dj.katchup.ui.myEvent.StatusItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TicketAdapter extends RecyclerView.Adapter<TicketItem> {
    private static final String TAG = "ListAdapter";
    private List<MyTicketResponseM> menus;
    private Context context;
    private int [] images = new int[]{R.drawable.qr1,R.drawable.qr2,R.drawable.qr3,R.drawable.qr4,R.drawable.qr5,R.drawable.qr6};
    private Date             date  ;
    private SimpleDateFormat sdf   ;
    private SimpleDateFormat output;

    public TicketAdapter(Context context) {
        this.context = context;
        dateFormater();
    }


    public void updateList(List<MyTicketResponseM> newRepo) {
        menus = new ArrayList<>();
        this.menus = newRepo;
        notifyDataSetChanged();
    }

    @Override
    public TicketItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_ticket, parent, false);
        return new TicketItem(itemView);
    }

    @Override
    public void onBindViewHolder(TicketItem holder, int position) {
        if (menus != null) {
            showCurrentItem(holder, menus.get(position),position);
        }
    }

    private void dateFormater() {
        date = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        output = new SimpleDateFormat("EEE, MMM d, ''yy");
    }

    private void showCurrentItem(final TicketItem holder, final MyTicketResponseM model, int position) {
        holder.event_name.setText(model.getEventName());
        try {
            date = sdf.parse(model.getDate());
            holder.ticket_date.setText(output.format(date));
        } catch (Exception e) {
            holder.ticket_date.setText(model.getDate());
        }
        holder.ticket_time.setText(model.getTime());
        holder.ticket_id.setText("Booking ID: "+model.getBookingId());
        holder.like.setImageDrawable(context.getDrawable(getRandomImage()));

    }


    @Override
    public int getItemCount() {
        if (menus == null)
            return 0;
        else {
            return menus.size();
        }
    }

    private int getRandomImage() {
        int rnd = new Random().nextInt(images.length);
        return images[rnd];
    }
}