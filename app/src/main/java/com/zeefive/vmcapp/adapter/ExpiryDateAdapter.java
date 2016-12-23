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
import com.zeefive.vmcapp.fragment.DialogFragment_AddExpiryDate;
import com.zeefive.vmcapp.fragment.DialogFragment_ExpiryDate_DatePicker;
import com.zeefive.vmcapp.model.ExpiryDate;
import com.zeefive.vmcapp.viewholder.ExpiryDateViewHolder;

import java.util.Calendar;
import java.util.Date;

public class ExpiryDateAdapter extends FirebaseRecyclerAdapter<ExpiryDate, ExpiryDateViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public ExpiryDateAdapter(Query ref, ActivityBase activity) {
        super(ExpiryDate.class, R.layout.listitem, ExpiryDateViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final ExpiryDateViewHolder viewHolder, final ExpiryDate item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(null);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((ActivityBase)activity).showPopupMenu(view, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item);
    }

    private void duplicateItem(ExpiryDate item){
        DatabaseReference reference = ((DatabaseReference)Data.QUERY_EXPIRY_DATES).push();
        String key = reference.getKey();
        item.setKey(key);
        showEditorDialog(item);
    }

    private PopupMenu.OnMenuItemClickListener getListener(final ExpiryDate item){
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
                        ((DatabaseReference)Data.QUERY_EXPIRY_DATES).child(item.getKey()).setValue(null);
                        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        };
    }

    public void showEditorDialog(ExpiryDate item) {
        DialogFragment dialog = new DialogFragment_AddExpiryDate().newInstance();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExpiryDate.ITEM, item);
            dialog.setArguments(bundle);
        }
        dialog.show(activity.getFragmentManager(), "Add");
    }

    public void showDatePicker(ExpiryDate item){
        DialogFragment dialog = new DialogFragment_ExpiryDate_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpiryDate.ITEM, item);
        dialog.setArguments(bundle);
        dialog.show(activity.getFragmentManager(), "Edit");
    }

    public void cycleAnItem(ExpiryDate item){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime((long) item.getCreatedAt());
        calendar.setTime(addDaysToDate(date, item.getPeriod()));
        ((DatabaseReference)Data.QUERY_EXPIRY_DATES).child(item.getKey()).child(Data.KEY_CREATED_AT).setValue(calendar.getTimeInMillis());
    }

    private static Date addDaysToDate(Date baseDate, int daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }
}
