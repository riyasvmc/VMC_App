package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Purchase;

public class PurchaseViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;
    public TextView mTextView_Amount;

    public PurchaseViewHolder(View itemView) {
        super(itemView);

        titleView1 = (TextView) itemView.findViewById(R.id.textView1);
        titleView2 = (TextView) itemView.findViewById(R.id.textView2);
        mTextView_Amount = (TextView) itemView.findViewById(R.id.textView_amount);
    }

    public void bindToPost(Purchase item, Context context) {
        titleView1.setText(item.getTitle() + "( " + item.getQuantity() + " X " + item.getRate() + " )");
        titleView2.setText(item.getSubTitle());
        mTextView_Amount.setText(item.getFormattedAmount());
    }
}
