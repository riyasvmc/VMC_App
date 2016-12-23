package com.zeefive.vmcapp.adapter;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.MySelectableInterface;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityShop;
import com.zeefive.vmcapp.activity.ActivityShopDetail;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddShop;
import com.zeefive.vmcapp.model.Shop;
import com.zeefive.vmcapp.viewholder.ShopViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopAdapter extends FirebaseRecyclerAdapter<Shop, ShopViewHolder> implements ActionMode.Callback, MySelectableInterface {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public ShopAdapter(Query ref, ActivityBase activity) {
        super(Shop.class, R.layout.listitem, ShopViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final ShopViewHolder viewHolder, final Shop item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to detail activity
                Intent intent = new Intent(activity, ActivityShopDetail.class);
                intent.putExtra(Shop.ITEM, item);
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

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Shop> getSelectedItems() {
        List<Shop> items = new ArrayList<>(selectedItems.size());
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

    private void deleteItem(Shop item){
        Map<String, Object> map = new HashMap<>();
        map.put(item.getKey(), null);
        ((DatabaseReference)Data.QUERY_SHOP).updateChildren(map);
        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    public void clearSelections() {
        selectedItems.clear();
        multiChoiceMode = false;
        notifyDataSetChanged();
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Shop item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_delete:
                        deleteItem(item);
                        break;
                }
                return true;
            }
        };
    }

    public void showAddDialog() {
        DialogFragment dialog = DialogFragment_AddShop.newInstance();
        dialog.show(activity.getFragmentManager(), "Add");
    }
}