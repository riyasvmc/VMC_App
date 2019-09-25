package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.CheckList;
import com.zeefive.vmcapp.model.CheckListDetail;

public class CheckListDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public CheckListDetailViewHolder(View view) {
        super(view);

        titleView1 = view.findViewById(android.R.id.text1);
        titleView2 = view.findViewById(R.id.textView2);
    }

    public void bindToPost(CheckListDetail i, Context context) {
        titleView1.setText(i.getTitle());
    }
}
