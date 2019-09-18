package com.zeefive.vmcapp.adapter;

import androidx.appcompat.view.ActionMode;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityBase;
import com.zeefive.vmcapp.model.User;
import com.zeefive.vmcapp.viewholder.UserViewHolder;

public class UserAdapter extends FirebaseRecyclerAdapter<User, UserViewHolder>{
    private ActivityBase activity;

    public UserAdapter(Query ref, ActivityBase activity) {
        super(User.class, R.layout.griditem, UserViewHolder.class, ref);
        this.activity = activity;
    }

    @Override
    protected void populateViewHolder(final UserViewHolder viewHolder, final User item, final int position) {
        final DatabaseReference userRef = getRef(position);
        viewHolder.bindToPost(item, position);
    }
}
