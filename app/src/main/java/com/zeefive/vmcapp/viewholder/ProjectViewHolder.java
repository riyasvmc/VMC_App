package com.zeefive.vmcapp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Project;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public ProjectViewHolder(View view) {
        super(view);

        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Project project, int position) {
        titleView1.setText(project.getTitle());
        titleView2.setText(project.getLocation());

        if(position % 2 == 1) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
