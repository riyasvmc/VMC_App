package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.PaymentAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Account;
import com.zeefive.vmcapp.model.Payment;
import com.zeefive.vmcapp.model.Purchase;

public class ActivityAccountDetail extends ActivityBase {

    public static Account mItem = new Account();
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private PaymentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);

        // get passed item
        mItem = (Account) getIntent().getSerializableExtra(Payment.ITEM);

        setUpActionBar(mItem.getTitle(), true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = ((DatabaseReference)Data.QUERY_PAYMENT).push();
                String key = reference.getKey();
                Payment item = new Payment(key, mItem, "", "", ServerValue.TIMESTAMP);
                mAdapter.showEditor(item);
            }
        });

        Query query = Data.QUERY_PAYMENT.orderByChild("account/key").equalTo(mItem.getKey());

        mAdapter = new PaymentAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}