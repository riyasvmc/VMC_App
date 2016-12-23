package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddSale;
import com.zeefive.vmcapp.model.Sale;
import com.zeefive.vmcapp.viewholder.SaleViewHolder;

public class ActivitySale extends ActivityBase {

    private static final String TITLE = "Sale";
    private AppCompatActivity mActivity = this;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Sale, SaleViewHolder> mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);
        setUpActionBar(TITLE, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = Data.QUERY_SALES;

        mAdapter = new FirebaseRecyclerAdapter<Sale, SaleViewHolder>(Sale.class, R.layout.listitem,
                SaleViewHolder.class, query) {
            @Override
            protected void populateViewHolder(final SaleViewHolder viewHolder, final Sale item, final int position) {
                final DatabaseReference userRef = getRef(position);
                final String key = userRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // go to detail activity
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        showPopupMenu(view, R.menu.menu_popup_sale, new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.action_edit:
                                        Toast.makeText(mActivity, "Complete code.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.action_done:
                                        boolean value = item.isDone() ? false : true;
                                        ((DatabaseReference)Data.QUERY_SALES).child(item.getKey()).child(Data.KEY_DONE).setValue(value);
                                        break;
                                    case R.id.action_delete:
                                        userRef.setValue(null);
                                        Toast.makeText(mActivity, "Item Deleted!", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return true;
                            }
                        });
                        return true;
                    }
                });
                viewHolder.bindToPost(item, getBaseContext());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showAddDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = DialogFragment_AddSale.newInstance();
        dialog.show(ActivitySale.this.getFragmentManager(), "Sale");
    }
}
