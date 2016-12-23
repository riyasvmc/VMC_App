package com.zeefive.vmcapp.adapter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.MySelectableInterface;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityTools;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.fragment.DialogFragment_AddTool;
import com.zeefive.vmcapp.model.Tool;
import com.zeefive.vmcapp.viewholder.ToolsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ToolAdapter extends FirebaseRecyclerAdapter<Tool, ToolsViewHolder> implements ActionMode.Callback, MySelectableInterface {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private static ActivityBase activity;

    public ToolAdapter(Query ref, ActivityBase activity, int layout) {
        super(Tool.class, layout, ToolsViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final ToolsViewHolder viewHolder, final Tool item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        boolean isSelected = selectedItems.get(position, false);
        viewHolder.bindToPost(item, activity);
        viewHolder.itemView.setActivated(isSelected);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActionMode != null) {
                    toggleSelection(position);
                }else{
                    editItem(position);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActionMode != null) {
                    return true;
                }
                mActionMode = activity.startSupportActionMode(ToolAdapter.this);
                toggleSelection(position);
                return true;
            }
        });
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Tool> getSelectedItems() {
        List<Tool> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(getItem(selectedItems.keyAt(i)));
        }
        return items;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_action_mode_tool, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_set_location:
                ((ActivityTools) activity).showDialog(getSelectedItems().toArray());
                mode.finish();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        clearSelections();
    }

    public void toggleSelection(int pos) {
        multiChoiceMode = true;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }


        mActionMode.setTitle(String.valueOf(getSelectedItemCount()));

        if(getSelectedItemCount() == 0){
            mActionMode.finish();
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        multiChoiceMode = false;
        notifyDataSetChanged();
    }


    private void editItem(int position){
        Tool item = getItem(position);
        // show edit dialog
        DialogFragment dialog = DialogFragment_AddTool.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        dialog.setArguments(bundle);
        dialog.show(activity.getFragmentManager(), "Edit");
    }
}
