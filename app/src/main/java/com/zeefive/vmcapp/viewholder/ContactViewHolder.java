package com.zeefive.vmcapp.viewholder;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView1;
    public TextView titleView2;

    public ContactViewHolder(View view) {
        super(view);
        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Contact i, int position) {
        final SpannableStringBuilder title = new SpannableStringBuilder(i.getTitle() + " \u00B7 " + "(" + i.getDesignation() + ")");
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, i.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new StyleSpan(Typeface.ITALIC), i.getTitle().length() + 1, i.getTitle().length() + i.getDesignation().length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleView1.setText(title);

        titleView2.setText(i.getPhone());

        // set Background
        if(position % 2 == 0) {
            itemView.setBackgroundResource(R.drawable.selector_dark);
        }
        else{
            itemView.setBackgroundResource(R.drawable.selector_light);
        }
    }
}
