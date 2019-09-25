package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.CheckListAdapter;
import com.zeefive.vmcapp.data.Data;

public class ActivityCheckList extends ActivityBase {

    private static final String TITLE = "Check Lists";
    private CheckListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);
        setUpActionBar(TITLE, true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = (LinearLayout) findViewById(R.id.empty_view);

        // Floating Action Button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.showEditorActivity(null);
            }
        });

        // project query
        Query query = Data.getQuery(getBaseContext(), Data.CHECK_LISTS);
        query.addValueEventListener(new ValueEventListener() {

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

        mAdapter = new CheckListAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}