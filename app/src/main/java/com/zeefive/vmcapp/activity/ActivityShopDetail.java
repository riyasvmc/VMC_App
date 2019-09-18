package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.PurchaseAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Shop;

public class ActivityShopDetail extends ActivityBase {

    public static Shop mItem = new Shop();
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private PurchaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);

        // get passed item
        mItem = (Shop) getIntent().getSerializableExtra(Shop.ITEM);

        setUpActionBar(mItem.getTitle(), true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addNewPurchase(mItem);
            }
        });

        Query query = Data.getQuery(getBaseContext(), Data.PURCHASE).orderByChild("from").equalTo(mItem.getKey());

        mAdapter = new PurchaseAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}