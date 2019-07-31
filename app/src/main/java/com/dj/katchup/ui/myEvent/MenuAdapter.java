package com.dj.katchup.ui.myEvent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dj.katchup.R;
import com.dj.katchup.model.responseModel.MyEventHistoryResponseM;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<StatusItem> {
    private static final String TAG = "ListAdapter";
    private List<MyEventHistoryResponseM> menus;
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
    }


    public void updateList(List<MyEventHistoryResponseM> newRepo) {
        menus = new ArrayList<>();
        this.menus = newRepo;
        notifyDataSetChanged();
    }

    @Override
    public StatusItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_history, parent, false);
        return new StatusItem(itemView);
    }

    @Override
    public void onBindViewHolder(StatusItem holder, int position) {
        if (menus != null) {
            showCurrentItem(holder, menus.get(position),position);
        }
    }

    private void showCurrentItem(final StatusItem holder, final MyEventHistoryResponseM model, int position) {
        holder.event_name.setText(model.getEventName());
        holder.event_status.setText(model.getStatusName());
        if(model.getStatusName().equalsIgnoreCase("Approved")){
            holder.like.setImageDrawable(context.getDrawable(R.drawable.approved));
        }else if(model.getStatusName().equalsIgnoreCase("Rejected")){
            holder.like.setImageDrawable(context.getDrawable(R.drawable.rejected));
        }else {
            holder.like.setImageDrawable(context.getDrawable(R.drawable.pending));
        }
    }


    @Override
    public int getItemCount() {
        if (menus == null)
            return 0;
        else {
            return menus.size();
        }
    }
}
