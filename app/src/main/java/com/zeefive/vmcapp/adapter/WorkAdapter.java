package com.zeefive.vmcapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityProjectDetail_page_3_editor;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Work;
import com.zeefive.vmcapp.viewholder.WorkViewHolder;

public class WorkAdapter extends  FirebaseRecyclerAdapter<Work, WorkViewHolder> {
    private ActivityBase activity;

    public WorkAdapter(Query ref, ActivityBase activity) {
        super(Work.class, R.layout.listitem_work, WorkViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final WorkViewHolder viewHolder, final Work item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(null);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("Riyas", "Long Clicked : " + item.getKey() + ", Title: " + item.getTitle() + ", Pos : " + position);
                ((ActivityBase)activity).showPopupMenu(v, R.menu.menu_popup_work_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    public void showEditorActivity(Work item) {
        Intent i = new Intent(activity, ActivityProjectDetail_page_3_editor.class);
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(item.ITEM, item);
            i.putExtras(bundle);
        }
        activity.startActivity(i);
    }

    private void duplicateItem(Work item){
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(activity, Data.WORKS)).push();
        String key = reference.getKey();
        showEditorActivity(new Work(key, item.getTitle(), item.getProjectKey(), item.getTags(), item.getCreatedAt()));
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Work item){
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditorActivity(item);
                        return true;
                    case R.id.action_duplicate:
                        duplicateItem(item);
                        Log.d("Riyas", "Duplicated  : " + item.getKey());
                        Toast.makeText(activity, "Duplicated !", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_delete:
                        Log.d("Riyas", "Deleting: " + item.getKey());
                        ((DatabaseReference)Data.getQuery(activity, Data.WORKS)).child(item.getKey()).setValue(null);
                        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        };
    }
}
