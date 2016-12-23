package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ExpiryDateAdapter;
import com.zeefive.vmcapp.data.Data;

public class ActivityExpiryDates extends ActivityBase {

    private static final String TITLE = "Expiry Dates";
    private FirebaseAuth mAuth;
    private ExpiryDateAdapter mAdapter;
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
                mAdapter.showEditorDialog(null);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = Data.QUERY_EXPIRY_DATES_ORD_BY_CREATED_AT;

        mAdapter = new ExpiryDateAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
