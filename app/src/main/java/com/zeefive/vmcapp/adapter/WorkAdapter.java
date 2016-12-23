package com.zeefive.vmcapp.adapter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddWork;
import com.zeefive.vmcapp.fragment.DialogFragment_Work_DatePicker;
import com.zeefive.vmcapp.model.Work;
import com.zeefive.vmcapp.viewholder.WorkViewHolder;

public class WorkAdapter extends  FirebaseRecyclerAdapter<Work, WorkViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
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
                ((ActivityBase)activity).showPopupMenu(v, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    public void showEditorDialog(Work item) {
        DialogFragment dialog = DialogFragment_AddWork.newInstance();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            dialog.setArguments(bundle);
        }
        dialog.show(activity.getFragmentManager(), "Add");
    }

    private void duplicateItem(Work item){
        DatabaseReference reference = ((DatabaseReference)Data.QUERY_WORKS).push();
        String key = reference.getKey();
        item.setKey(key);
        showEditorDialog(item);
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Work item){
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditorDialog(item);
                        return true;
                    case R.id.action_edit_date:
                        showDatePicker(item);
                        return true;
                    case R.id.action_duplicate:
                        duplicateItem(item);
                        Toast.makeText(activity, "Duplicated!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_delete:
                        ((DatabaseReference)Data.QUERY_WORKS).child(item.getKey()).setValue(null);
                        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        };
    }

    private void showDatePicker(Work item){
        DialogFragment dialog = new DialogFragment_Work_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        dialog.setArguments(bundle);
        dialog.show(activity.getFragmentManager(), "Edit");
    }
}
