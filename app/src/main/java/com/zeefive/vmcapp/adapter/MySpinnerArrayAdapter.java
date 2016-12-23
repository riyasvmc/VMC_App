package com.zeefive.vmcapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.HomeMenuItem;
import com.zeefive.vmcapp.model.Project;
import com.zeefive.vmcapp.model.SpinnerItem;

import java.util.List;

public class MySpinnerArrayAdapter extends ArrayAdapter<Project> {
    private Context mContext;
    private int resource;

    public MySpinnerArrayAdapter(Context context, int resource, List<Project> projects) {
        super(context, resource, projects);
        this.mContext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        Project item = getItem(position);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(resource, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textView1);
            holder.textView2 = (TextView) view.findViewById(R.id.textView2);
            view.setTag(holder);
        }else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textView.setText(item.getTitle());
        holder.textView2.setText(item.getKey());
        return view;
    }

    private class ViewHolder{
        private TextView textView;
        private TextView textView2;
        private ImageView imageView;
        private View divider;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;
        Project item = getItem(position);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(resource, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textView1);
            holder.textView2 = (TextView) view.findViewById(R.id.textView2);
            view.setTag(holder);
        }else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textView.setText(item.getTitle());
        holder.textView2.setText(item.getKey());
        return view;
    }
}
