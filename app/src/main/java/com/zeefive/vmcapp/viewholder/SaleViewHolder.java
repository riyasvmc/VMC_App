package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Sale;

public class SaleViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public SaleViewHolder(View view) {
        super(view);

        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Sale i, Context context) {
        titleView1.setText(i.getTitle());

        if(i.isDone()) {
            titleView2.setTextColor(Color.parseColor("#11a1ec")); // yellow color
            titleView2.setText(i.getFormattedAmount() + " Recieved from " + i.getCustomer());
        }else {
            titleView2.setTextColor(Color.GRAY);
            titleView2.setText(i.getSubTitle());
        }
    }
}
