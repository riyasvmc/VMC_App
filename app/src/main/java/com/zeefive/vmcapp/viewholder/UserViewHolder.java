package com.zeefive.vmcapp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public UserViewHolder(View itemView) {
        super(itemView);
        titleView1 = (TextView) itemView.findViewById(R.id.textView1);
        titleView2 = (TextView) itemView.findViewById(R.id.textView2);
    }

    public void bindToPost(User item, int position) {
        titleView1.setText(item.getName());
        titleView2.setText(item.getEmail());

        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
