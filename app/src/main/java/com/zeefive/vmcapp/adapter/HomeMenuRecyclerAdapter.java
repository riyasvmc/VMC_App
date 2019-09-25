package com.zeefive.vmcapp.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityAbout;
import com.zeefive.vmcapp.activity.ActivityAccount;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityCheckList;
import com.zeefive.vmcapp.activity.ActivityConcreteCalculator;
import com.zeefive.vmcapp.activity.ActivityContacts;
import com.zeefive.vmcapp.activity.ActivityExpiryDates;
import com.zeefive.vmcapp.activity.ActivityLog;
import com.zeefive.vmcapp.activity.ActivityProject;
import com.zeefive.vmcapp.activity.ActivitySale;
import com.zeefive.vmcapp.activity.ActivityShop;
import com.zeefive.vmcapp.activity.ActivityTodo;
import com.zeefive.vmcapp.activity.ActivityTools;
import com.zeefive.vmcapp.activity.MainActivity;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.MenuItem;
import com.zeefive.vmcapp.viewholder.HomeMenuViewHolder;

public class HomeMenuRecyclerAdapter extends RecyclerView.Adapter<HomeMenuViewHolder> {

    private ActivityBase activity;
    private Context context;
    private MenuItem[] list;

    public HomeMenuRecyclerAdapter(ActivityBase activity, Context context, MenuItem[] list){
        this.activity = activity;
        this.context = context;
        this.list = list;
    }

    @Override
    public HomeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.griditem, parent, false);
        return new HomeMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeMenuViewHolder holder, final int position) {
        final MenuItem item = list[position];
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = item.getTitle();
                if(title.equals(Data.ITEM_PROJECT.getTitle())){
                    goToNextActivity(ActivityProject.class);
                }else if(title.equals(Data.ITEM_SALE.getTitle())){
                    goToNextActivity(ActivitySale.class);
                }else if(title.equals(Data.ITEM_ACCOUNTS.getTitle())){
                    goToNextActivity(ActivityAccount.class);
                }else if(title.equals(Data.ITEM_SHOPS.getTitle())){
                    goToNextActivity(ActivityShop.class);
                }else if(title.equals(Data.ITEM_TODO.getTitle())){
                    goToNextActivity(ActivityTodo.class);
                }else if(title.equals(Data.ITEM_TOOLS.getTitle())){
                    goToNextActivity(ActivityTools.class);
                }else if(title.equals(Data.ITEM_CHECKLISTS.getTitle())){
                    goToNextActivity(ActivityCheckList.class);
                }else if(title.equals(Data.ITEM_LOGS.getTitle())){
                    goToNextActivity(ActivityLog.class);
                }else if(title.equals(Data.ITEM_CONCRETE_CALCULATOR.getTitle())){
                    goToNextActivity(ActivityConcreteCalculator.class);
                }else if(title.equals(Data.ITEM_EXPIRY_DATES.getTitle())){
                    goToNextActivity(ActivityExpiryDates.class);
                }else if(title.equals(Data.ITEM_CONTACTS.getTitle())){
                    goToNextActivity(ActivityContacts.class);
                // settings page
                }else if(title.equals(Data.ITEM_LOGOUT.getTitle())){
                    MainActivity.revokeAccess();
                }else if(title.equals(Data.ITEM_FEEDBACK.getTitle())){
                    Toast.makeText(context, "Complete Code", Toast.LENGTH_SHORT).show();
                }else if(title.equals(Data.ITEM_ABOUT.getTitle())){
                    goToNextActivity(ActivityAbout.class);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title = Data.HOME_MENU_ITEMS[position].getTitle();
                if(title.equals(Data.ITEM_EXPIRY_DATES.getTitle())){
                    goToNextActivity(ActivityLog.class);
                }
                return true;
            }
        });
        holder.bindToPost(item, position);
    }

    private void goToNextActivity(Class mTargetActivity) {
        if(mTargetActivity != null){
            Intent i = new Intent(context, mTargetActivity);
            context.startActivity(i);
        }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
