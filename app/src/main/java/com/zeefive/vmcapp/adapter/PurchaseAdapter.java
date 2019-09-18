package com.zeefive.vmcapp.adapter;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_AddPurchase;
import com.zeefive.vmcapp.dialog.DialogFragment_Purchase_DatePicker;
import com.zeefive.vmcapp.model.Purchase;
import com.zeefive.vmcapp.model.Shop;
import com.zeefive.vmcapp.viewholder.PurchaseViewHolder;

import java.util.HashMap;
import java.util.Map;

public class PurchaseAdapter extends FirebaseRecyclerAdapter<Purchase, PurchaseViewHolder> {
    private ActivityBase activity;

    public PurchaseAdapter(Query ref, ActivityBase activity) {
        super(Purchase.class, R.layout.listitem_purchase, PurchaseViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final PurchaseViewHolder viewHolder, final Purchase item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();

        viewHolder.itemView.setOnClickListener(null);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activity.showPopupMenu(view, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Purchase item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditor(item);
                        return true;
                    case R.id.action_edit_date:
                        showEditDateDialog(item);
                        return true;
                    case R.id.action_duplicate:
                        duplicateItem(item);
                        return true;
                    case R.id.action_delete:
                        deleteItem(item);
                        return true;
                }
                return false;
            }
        };
    }

    private void showEditor(Purchase item){
        DialogFragment dialog = DialogFragment_AddPurchase.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        dialog.setArguments(bundle);
        dialog.show(activity.getFragmentManager(), "Edit");
    }

    private void showEditDateDialog(Purchase item){
        DialogFragment dialog = new DialogFragment_Purchase_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        dialog.setArguments(bundle);
        dialog.show(activity.getFragmentManager(), "Edit");
    }

    private void deleteItem(Purchase item){
        Map<String, Object> map = new HashMap<>();
        map.put(item.getKey(), null);
        ((DatabaseReference)Data.getQuery(activity, Data.PURCHASE)).updateChildren(map);
        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    private void duplicateItem(Purchase item){
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(activity, Data.PURCHASE)).push();
        String key = reference.getKey();
        showEditorDialog(new Purchase(key, item.getTitle(), item.getProjectKey(), item.getProject(), item.getQuantity(), item.getRate(), item.getAmount(), item.getFrom(), item.getShop(), item.getCreatedAt()));
    }

    public void showEditorDialog(@NonNull Purchase item) {
        DialogFragment dialog = DialogFragment_AddPurchase.newInstance();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            dialog.setArguments(bundle);
        }
        dialog.show(activity.getFragmentManager(), "Add");
    }

    public void addNewPurchase(Shop shop){
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(activity, Data.PURCHASE)).push();
        String key = reference.getKey();
        Purchase item = new Purchase(key, "", "", null, "0", "0", 0, shop.getKey(), shop, ServerValue.TIMESTAMP);
        showEditorDialog(item);
    }
}
