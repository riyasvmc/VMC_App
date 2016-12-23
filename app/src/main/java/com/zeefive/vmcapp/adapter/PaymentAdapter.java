package com.zeefive.vmcapp.adapter;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityShopDetail;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddPayment;
import com.zeefive.vmcapp.fragment.DialogFragment_AddPurchase;
import com.zeefive.vmcapp.fragment.DialogFragment_AddShop;
import com.zeefive.vmcapp.model.Payment;
import com.zeefive.vmcapp.model.Shop;
import com.zeefive.vmcapp.viewholder.PaymentViewHolder;
import com.zeefive.vmcapp.viewholder.ProjectViewHolder;
import com.zeefive.vmcapp.viewholder.ShopViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentAdapter extends FirebaseRecyclerAdapter<Payment, PaymentViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public PaymentAdapter(Query ref, ActivityBase activity) {
        super(Payment.class, R.layout.listitem, PaymentViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final PaymentViewHolder viewHolder, final Payment item, final int position) {
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
                activity.showPopupMenu(view, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });
        viewHolder.bindToPost(item, activity);
    }

    private void deleteItem(Payment item){
        Map<String, Object> map = new HashMap<>();
        map.put(item.getKey(), null);
        ((DatabaseReference)Data.QUERY_PAYMENT).updateChildren(map);
        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Payment item) {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditor(item);
                        break;
                    case R.id.action_delete:
                        deleteItem(item);
                        break;
                }
                return true;
            }
        };
    }

    public void showEditor(Payment item) {
        DialogFragment dialog = DialogFragment_AddPayment.newInstance();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            dialog.setArguments(bundle);
        }
        dialog.show(activity.getFragmentManager(), "Add");
    }
}