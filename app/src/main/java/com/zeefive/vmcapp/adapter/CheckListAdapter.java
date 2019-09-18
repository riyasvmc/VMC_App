package com.zeefive.vmcapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityCheckListDetail;
import com.zeefive.vmcapp.activity.ActivityCheckList_Editor;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.CheckList;
import com.zeefive.vmcapp.viewholder.CheckListViewHolder;

import java.util.HashMap;
import java.util.Map;

public class CheckListAdapter extends FirebaseRecyclerAdapter<CheckList, CheckListViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public CheckListAdapter(Query ref, ActivityBase activity) {
        super(CheckList.class, R.layout.listitem, CheckListViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final CheckListViewHolder viewHolder, final CheckList item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to detail activity
                Intent intent = new Intent(activity, ActivityCheckListDetail.class);
                intent.putExtra(CheckList.ITEM, item);
                activity.startActivity(intent);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activity.showPopupMenu(view, R.menu.menu_popup_shop, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    private void deleteItem(CheckList item){
        Map<String, Object> map = new HashMap<>();
        map.put(item.getKey(), null);
        ((DatabaseReference)Data.getQuery(activity, Data.SHOPS)).updateChildren(map);
        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    private PopupMenu.OnMenuItemClickListener getListener(final CheckList item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditorActivity(item);
                        break;
                    case R.id.action_delete:
                        deleteItem(item);
                        break;
                }
                return true;
            }
        };
    }

    public void showEditorActivity(CheckList item) {
        Intent i = new Intent(activity, ActivityCheckList_Editor.class);
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(item.ITEM, item);
            i.putExtras(bundle);
        }
        activity.startActivity(i);
    }

}