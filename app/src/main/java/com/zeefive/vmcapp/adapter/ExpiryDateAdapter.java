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
import com.zeefive.vmcapp.activity.ActivityExpiryDatesEditor;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ExpiryDate;
import com.zeefive.vmcapp.viewholder.ExpiryDateViewHolder;

public class ExpiryDateAdapter extends FirebaseRecyclerAdapter<ExpiryDate, ExpiryDateViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public ExpiryDateAdapter(Query ref, ActivityBase activity) {
        super(ExpiryDate.class, R.layout.griditem, ExpiryDateViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final ExpiryDateViewHolder viewHolder, final ExpiryDate item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditorActivity(item);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((ActivityBase)activity).showPopupMenu(view, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, position);
    }

    private void duplicateItem(ExpiryDate item){
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(activity, Data.EXPIRY_DATES)).push();
        String key = reference.getKey();
        showEditorActivity(new ExpiryDate(key, item.getTitle(), item.getPeriod(), item.getCreatedAt()));
    }

    private PopupMenu.OnMenuItemClickListener getListener(final ExpiryDate item){
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditorActivity(item);
                        return true;
                    case R.id.action_duplicate:
                        duplicateItem(item);
                        Toast.makeText(activity, "Duplicated!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_delete:
                        ((DatabaseReference)Data.getQuery(activity, Data.EXPIRY_DATES)).child(item.getKey()).setValue(null);
                        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        };
    }

    public void showEditorActivity(ExpiryDate item) {
        Intent i = new Intent(activity, ActivityExpiryDatesEditor.class);
        if(item != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(item.ITEM, item);
            i.putExtras(bundle);
        }
        activity.startActivity(i);
    }
}
