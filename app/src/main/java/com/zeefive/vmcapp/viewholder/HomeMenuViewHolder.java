package com.zeefive.vmcapp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.MenuItem;

public class HomeMenuViewHolder extends RecyclerView.ViewHolder {

    public TextView textView1;
    public TextView textView2;

    public HomeMenuViewHolder(View view) {
        super(view);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(MenuItem item, int position) {
        textView1.setText(item.getTitle());
        textView2.setText(item.getDescription());
        // if(position % 4 == 0 || position % 4 == 3) { << this is for Grid layour alternate styling
        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
