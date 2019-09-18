package com.zeefive.vmcapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.MenuItem;

public class HomeMenuAdapter extends ArrayAdapter<MenuItem> {
    private Context mContext;
    private int resource;
    public HomeMenuAdapter(Context context, int resource, MenuItem[] list) {
        super(context, resource, list);
        this.mContext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        MenuItem item = getItem(position);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(resource, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textView1);
            holder.textView2 = (TextView) view.findViewById(R.id.textView2);
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
            // holder.divider = view.findViewById(R.id.divider);
            view.setTag(holder);
        }else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getIcon());

        if(!TextUtils.isEmpty(item.getDescription())){
            holder.textView2.setVisibility(View.VISIBLE);
            holder.textView2.setText(item.getDescription());
        }else{
            holder.textView2.setVisibility(View.GONE);
        }

        hideDividerForLastItem(holder, position);
        return view;
    }

    private void hideDividerForLastItem(ViewHolder holder, int position) {
        /*if(getCount() == (position + 1)){
            holder.divider.setVisibility(View.GONE);
        }else{
            holder.divider.setVisibility(View.VISIBLE);
        }*/
    }

    private class ViewHolder{
        private TextView textView;
        private TextView textView2;
        private ImageView imageView;
        // private View divider;
    }
}
