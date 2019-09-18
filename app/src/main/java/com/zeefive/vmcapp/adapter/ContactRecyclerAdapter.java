package com.zeefive.vmcapp.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.activity.ActivityContactsDetail;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_AddContact;
import com.zeefive.vmcapp.model.Contact;
import com.zeefive.vmcapp.viewholder.ContactViewHolder;

public class ContactRecyclerAdapter extends FirebaseRecyclerAdapter<Contact, ContactViewHolder> {

    private Activity activity;

    // constructor
    public ContactRecyclerAdapter(Query query, Activity activity){
        super(Contact.class, R.layout.griditem, ContactViewHolder.class, query);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final ContactViewHolder viewHolder, final Contact item, final int position) {
        final DatabaseReference userRef = getRef(position);
        final String key = userRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ActivityContactsDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Contact.ITEM, item);
                i.putExtras(bundle);
                activity.startActivity(i);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ActivityBase)activity).showPopupMenu(v, R.menu.menu_popup_item, getListener(item));
                return true;
            }
        });

                /*Picasso.with(getBaseContext())
                        .load(item.getPhotoUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolder.imageView);*/

        viewHolder.bindToPost(item, position);
    }

    private PopupMenu.OnMenuItemClickListener getListener(final Contact item){
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        showEditorDialog(item);
                        break;
                    case R.id.action_delete:
                         ((DatabaseReference) Data.getQuery(activity, Data.CONTACTS)).child(item.getKey()).setValue(null);
                        break;
                }
                return true;
            }
        };
    }

    public void showEditorDialog(Contact item) {
        DialogFragment dialog = DialogFragment_AddContact.newInstance();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            dialog.setArguments(bundle);
        }
        dialog.show(activity.getFragmentManager(), "Add");
    }
}
