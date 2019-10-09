package com.vmc.vmcdaily.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.vmc.vmcdaily.R;
import com.vmc.vmcdaily.viewholder.DailyViewHolder;
import com.vmc.common.model.Daily;

public class DailyAdapter extends FirebaseRecyclerAdapter<Daily, DailyViewHolder> {

    private Context context;
    public DailyAdapter(Context context, @NonNull FirebaseRecyclerOptions<Daily> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DailyViewHolder holder, int position, @NonNull Daily item) {
        Log.d("Riyas.Vmc", "Position : " + position + ", count : " + getItemCount());
        boolean visiblity = false;

        // enable section heading if it's the first one, or
        // different from the previous one
        String thisDate = item.getDate();
        if(getItemCount() != (position + 1)) {
            if (thisDate.equals(getItem(position + 1).getDate()))
                visiblity = false;
            else
                visiblity = true;
        }else{
            visiblity = true;
        }
        holder.bindToPost(context, item, position, visiblity);
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new DailyViewHolder(view);
    }
}
