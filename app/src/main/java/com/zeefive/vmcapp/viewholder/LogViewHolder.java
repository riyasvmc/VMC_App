package com.zeefive.vmcapp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Log;

public class LogViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public LogViewHolder(View view) {
        super(view);
        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Log i, int position) {
        titleView1.setText(i.getTitle());
        titleView2.setText(i.getFormattedCreatedAt());

        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
