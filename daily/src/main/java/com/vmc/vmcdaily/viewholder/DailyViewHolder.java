package com.vmc.vmcdaily.viewholder;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vmc.vmcdaily.R;
import com.vmc.common.model.Daily;
import com.vmc.vmcdaily.utility.Utility;

public class DailyViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public TextView titleView1;
    public TextView titleView2;
    public TextView titleView3;

    public DailyViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        titleView1 = itemView.findViewById(R.id.date);
        titleView2 = itemView.findViewById(R.id.site);
        titleView3 = itemView.findViewById(R.id.work);
    }

    public void bindToPost(Context context, Daily item, int position, boolean headerVisibility) {
        if(headerVisibility){
            titleView1.setVisibility(View.VISIBLE);
            titleView1.setText(Utility.getമലയാളംFormattedDateString(context, item.getDate()));
            //view.invalidate();
        }else{
            titleView1.setVisibility(View.GONE);
            //view.invalidate();
        }
        titleView2.setText(item.getSite());
        titleView3.setText(Html.fromHtml(item.getWork()));
    }
}
