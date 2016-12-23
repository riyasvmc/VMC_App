package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.AccountAdapter;
import com.zeefive.vmcapp.adapter.PaymentAdapter;
import com.zeefive.vmcapp.adapter.ShopAdapter;
import com.zeefive.vmcapp.data.Data;

import hugo.weaving.DebugLog;

public class ActivityAccount extends ActivityBase {

    private static final String TITLE = "Accounts";
    private FirebaseAuth mAuth;
    private AccountAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);
        setUpActionBar(TITLE, true);

        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = (LinearLayout) findViewById(R.id.empty_view);

        // Floating Action Button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.showAddDialog();
            }
        });

        // project query
        Query query = Data.QUERY_ACCOUNTS;
        query.addValueEventListener(new ValueEventListener() {
            @DebugLog
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                if(count == 0){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else{
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new AccountAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}