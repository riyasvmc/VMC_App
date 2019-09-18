package com.zeefive.vmcapp.adapter;

import android.content.Intent;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.MySelectableInterface;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityProjectDetail;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Project;
import com.zeefive.vmcapp.viewholder.ProjectViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends FirebaseRecyclerAdapter<Project, ProjectViewHolder> implements ActionMode.Callback, MySelectableInterface {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public ProjectAdapter(Query ref, ActivityBase activity) {
        super(Project.class, R.layout.griditem, ProjectViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final ProjectViewHolder viewHolder, final Project item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to detail activity
                Intent intent = new Intent(activity, ActivityProjectDetail.class);
                intent.putExtra("item", item);
                activity.startActivity(intent);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activity.showPopupMenu(view, R.menu.menu_popup_project, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, position);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Project> getSelectedItems() {
        List<Project> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(getItem(selectedItems.keyAt(i)));
        }
        return items;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
        } else {
            selectedItems.put(pos, true);
        }


        mActionMode.setTitle(String.valueOf(getSelectedItemCount()));

        if (getSelectedItemCount() == 0) {
            mActionMode.finish();
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        multiChoiceMode = false;
        notifyDataSetChanged();
    }

    private void setItemCompleted(Project item) {
        ((DatabaseReference)Data.getQuery(activity, Data.PROJECTS)).child(item.getKey()).child(Data.KEY_COMPLETED).setValue(!item.isCompleted());
        Toast.makeText(activity, "Done.", Toast.LENGTH_SHORT).show();
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Project item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_mark_completed:
                        setItemCompleted(item); break;
                    case R.id.action_favorite:
                        Toast.makeText(activity, "Complete code.", Toast.LENGTH_SHORT).show(); break;
                }
                return true;
            }
        };
    }
}