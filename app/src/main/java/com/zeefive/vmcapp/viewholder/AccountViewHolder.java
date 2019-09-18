package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Account;

public class AccountViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public AccountViewHolder(View view) {
        super(view);

        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Account i, Context context) {
        titleView1.setText(i.getTitle());
        titleView2.setVisibility(View.GONE);
    }
}
