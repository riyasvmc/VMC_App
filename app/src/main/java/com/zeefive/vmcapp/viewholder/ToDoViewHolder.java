package com.zeefive.vmcapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.ToDo;

public class ToDoViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;
    public ImageView imageView;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        titleView1 = (TextView) itemView.findViewById(R.id.textView1);
        titleView2 = (TextView) itemView.findViewById(R.id.textView2);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bindToPost(ToDo item, int subTitleVisibility, boolean isSelected, boolean multiChoiceMode) {
        if(multiChoiceMode){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }

        if(isSelected){
            imageView.setImageResource(R.drawable.ic_check_circle_blue_300_36dp);
        }else{
            imageView.setImageResource(R.drawable.ic_check_circle_grey_300_36dp);
        }

        titleView1.setText(item.getTitle());
        titleView2.setText(item.getSubTitle());
        titleView2.setVisibility(subTitleVisibility);
    }
}
