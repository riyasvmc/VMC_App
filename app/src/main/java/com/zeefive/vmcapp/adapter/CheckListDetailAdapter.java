package com.zeefive.vmcapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityCheckListDetail;
import com.zeefive.vmcapp.activity.ActivityCheckList_Editor;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.CheckList;
import com.zeefive.vmcapp.model.CheckListDetail;
import com.zeefive.vmcapp.viewholder.CheckListDetailViewHolder;
import com.zeefive.vmcapp.viewholder.CheckListViewHolder;

import java.util.HashMap;
import java.util.Map;

public class CheckListDetailAdapter extends FirebaseRecyclerAdapter<CheckListDetail, CheckListDetailViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public CheckListDetailAdapter(Query ref, ActivityBase activity) {
        super(CheckListDetail.class, android.R.layout.simple_list_item_1, CheckListDetailViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final CheckListDetailViewHolder viewHolder, final CheckListDetail item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to detail activity

            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    private PopupMenu.OnMenuItemClickListener getListener(final CheckList item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        break;
                    case R.id.action_delete:
                        break;
                }
                return true;
            }
        };
    }
}