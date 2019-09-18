package com.zeefive.vmcapp.viewholder;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.ExpiryDate;

public class ExpiryDateViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public ExpiryDateViewHolder(View view) {
        super(view);
        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(ExpiryDate i, int position) {
        titleView1.setText(i.getTitle());
        titleView2.setText(i.getFormattedCreatedAt());
        if(i.isExpired()) {
            titleView2.setTextColor(Color.RED);
        }else if(i.isInWarningRange()){
            titleView2.setTextColor(Color.parseColor("#e3a702")); // yellow color
        }
        else {
            titleView2.setTextColor(Color.GRAY);
        }

        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
