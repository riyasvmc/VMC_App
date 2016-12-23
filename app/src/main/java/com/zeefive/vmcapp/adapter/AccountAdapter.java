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
import com.zeefive.vmcapp.activity.ActivityAccountDetail;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityShopDetail;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddAccount;
import com.zeefive.vmcapp.fragment.DialogFragment_AddShop;
import com.zeefive.vmcapp.model.Account;
import com.zeefive.vmcapp.model.Shop;
import com.zeefive.vmcapp.viewholder.AccountViewHolder;
import com.zeefive.vmcapp.viewholder.ShopViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountAdapter extends FirebaseRecyclerAdapter<Account, AccountViewHolder> {
    public static boolean multiChoiceMode = false;
    public static ActionMode mActionMode;
    private ActivityBase activity;

    public AccountAdapter(Query ref, ActivityBase activity) {
        super(Account.class, R.layout.listitem, AccountViewHolder.class, ref);
        this.activity = activity;
    }

    protected void populateViewHolder(final AccountViewHolder viewHolder, final Account item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to detail activity
                Intent intent = new Intent(activity, ActivityAccountDetail.class);
                intent.putExtra(Account.ITEM, item);
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

    private void deleteItem(Account item){
        Map<String, Object> map = new HashMap<>();
        map.put(item.getKey(), null);
        ((DatabaseReference)Data.QUERY_SHOP).updateChildren(map);
        Toast.makeText(activity, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Account item) {
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
        DialogFragment dialog = DialogFragment_AddAccount.newInstance();
        dialog.show(activity.getFragmentManager(), "Add");
    }
}