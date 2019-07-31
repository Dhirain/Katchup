package com.dj.katchup.ui.landing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dj.katchup.R;
import com.dj.katchup.interfaces.EventSelectListerner;
import com.dj.katchup.model.responseModel.HomeResponseM;
import com.dj.katchup.utills.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResturantLandingAdapter extends RecyclerView.Adapter<ResturantLandingItem> {
    private static final String TAG = "ListAdapter";
    private List<HomeResponseM> resturants;
    private Context context;
    HomeResponseM currentMusic;
    private int lastPosition;
    private int selectedItemPostion = -1;
    private EventSelectListerner eventSelectListerner;
    private ListChangeListner listChangeListner;
    private Date             date  ;
    private SimpleDateFormat sdf   ;
    private SimpleDateFormat output;

    public ResturantLandingAdapter(Context context, EventSelectListerner eventSelectListner, ListChangeListner changeListner) {
        this.context = context;
        this.eventSelectListerner = eventSelectListner;
        this.listChangeListner = changeListner;
        dateFormater();
    }

    private void dateFormater() {
        date = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        output = new SimpleDateFormat("EEE, MMM d, ''yy");
    }

    public void setSelection(int pos) {
        selectedItemPostion = pos;
        //notifyDataSetChanged();
    }

    public void updateList(List<HomeResponseM> newRepo) {
        resturants = new ArrayList<>();
        this.resturants = newRepo;
        this.lastPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public ResturantLandingItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);
        return new ResturantLandingItem(itemView);
    }

    @Override
    public void onBindViewHolder(ResturantLandingItem holder, int position) {
        if (resturants != null) {
            Log.d(TAG, "onBindViewHolder: "+resturants.toString());
            showCurrentItem(holder, resturants.get(position),position);
            //setAnimation(holder.parent,position);
        }
    }

    private void showCurrentItem(final ResturantLandingItem holder, final HomeResponseM model, final int position) {
        try {
            date = sdf.parse(model.getDateOfEvent());
            holder.event_date.setText(output.format(date));
        } catch (ParseException e) {
            holder.event_date.setText(model.getDateOfEvent());
        }
        holder.event_pincode.setText(model.getPincode());
        holder.event_name.setText(model.getEventName());
        ImageUtils.setImage(context, model.getImageUrl(), holder.event_image, R.drawable.place_holder);
        if(model.getEventType().equalsIgnoreCase("premium")){
            holder.like.setVisibility(View.VISIBLE);
        }else {
            holder.like.setVisibility(View.INVISIBLE);
        }

        holder.event_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventSelectListerner.onResturantClicked(model,holder.event_image);
                setSelection(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (resturants == null)
            return 0;
        else {
            return resturants.size();
        }
    }


    public interface ListChangeListner {
        public void updateCurrentList(List<HomeResponseM> model);
    }
}

