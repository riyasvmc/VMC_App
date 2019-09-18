package com.zeefive.vmcapp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.ToDo;

public class ToDoViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        titleView1 = (TextView) itemView.findViewById(R.id.textView1);
        titleView2 = (TextView) itemView.findViewById(R.id.textView2);
    }

    public void bindToPost(ToDo item, int position) {
        titleView1.setText(item.getTitle());
        titleView2.setText(item.getSubTitle());

        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
