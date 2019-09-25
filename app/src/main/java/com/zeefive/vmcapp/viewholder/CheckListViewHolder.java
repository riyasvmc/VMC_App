package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.CheckList;

public class CheckListViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public CheckListViewHolder(View view) {
        super(view);

        titleView1 = view.findViewById(R.id.textView1);
        titleView2 = view.findViewById(R.id.textView2);
    }

    public void bindToPost(CheckList i, Context context) {
        titleView1.setText(i.getTitle());
    }
}
