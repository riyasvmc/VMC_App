package com.zeefive.vmcapp.adapter;

import androidx.appcompat.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.MySelectableInterface;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ToDo;
import com.zeefive.vmcapp.view.helper.ItemTouchHelperAdapter;
import com.zeefive.vmcapp.viewholder.ToDoViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoAdapter extends FirebaseRecyclerAdapter<ToDo, ToDoViewHolder> implements ActionMode.Callback, MySelectableInterface, ItemTouchHelperAdapter {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public TodoAdapter(Query ref, ActivityBase activity) {
        super(ToDo.class, R.layout.griditem, ToDoViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final ToDoViewHolder viewHolder, final ToDo item, final int position) {
        final DatabaseReference userRef = getRef(position);
        viewHolder.bindToPost(item, position);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<ToDo> getSelectedItems() {
        List<ToDo> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(getItem(selectedItems.keyAt(i)));
        }
        return items;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_action_mode_todo, menu);
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
            case R.id.action_delete:
                deleteSelectedItems();
                mode.finish();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        clearSelections();
    }

    public void deleteSelectedItems(){
        List<ToDo> list = getSelectedItems();
        Map<String, Object> map = new HashMap<>();
        for (ToDo item : list) {
            map.put(item.getKey(), null);
        }
        ((DatabaseReference)Data.getQuery(activity, Data.TODOS)).updateChildren(map);
    }

    private void deleteItem(int position){
        getRef(position).setValue(null);
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void save(ToDo item){
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(activity, Data.TODOS)).push();
        item.setKey(reference.getKey());
        reference.setValue(item);
    }

    @Override
    public void onItemDismiss(int position) {
        deleteItem(position);
        Utilities.notifySound(activity);
    }
}
