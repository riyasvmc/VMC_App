package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Work;

public class WorkViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;
    public TextView textView_tag_concrete;
    public TextView textView_tag_tipper;
    public TextView textView_tag_jcb;

    public WorkViewHolder(View itemView) {
        super(itemView);

        titleView1 = (TextView) itemView.findViewById(R.id.textView1);
        titleView2 = (TextView) itemView.findViewById(R.id.textView2);
        textView_tag_concrete = (TextView) itemView.findViewById(R.id.tag_concrete);
        textView_tag_tipper = (TextView) itemView.findViewById(R.id.tag_tipper);
        textView_tag_jcb = (TextView) itemView.findViewById(R.id.tag_jcb);
    }

    public void bindToPost(Work item, Context context) {
        titleView1.setText(item.getTitle());
        titleView2.setText(item.getSubTitle());
        if(item.getTags().contains(Data.TAG_CONCRETE)){
            textView_tag_concrete.setVisibility(View.VISIBLE);
        }else{
            textView_tag_concrete.setVisibility(View.GONE);
        }

        if(item.getTags().contains(Data.TAG_TIPPER)){
            textView_tag_tipper.setVisibility(View.VISIBLE);
        }else{
            textView_tag_tipper.setVisibility(View.GONE);
        }

        if(item.getTags().contains(Data.TAG_JCB)){
            textView_tag_jcb.setVisibility(View.VISIBLE);
        }else{
            textView_tag_jcb.setVisibility(View.GONE);
        }
    }
}
