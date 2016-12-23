package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Sale;
import com.zeefive.vmcapp.model.Shop;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public ShopViewHolder(View view) {
        super(view);

        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Shop i, Context context) {
        titleView1.setText(i.getTitle());
        titleView2.setVisibility(View.GONE);
    }
}
