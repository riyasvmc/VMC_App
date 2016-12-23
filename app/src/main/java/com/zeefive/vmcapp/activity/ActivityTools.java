package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ToolAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddTool;
import com.zeefive.vmcapp.fragment.DialogFragment_ToolLocator;
import com.zeefive.vmcapp.recyclerview.ItemDecorationAlbumColumns;

public class ActivityTools extends ActivityBase {

    private static final String TITLE = "Tools";
    private static final int NUMBER_OF_COLUMNS = 2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ToolAdapter mAdapter;
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
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new ItemDecorationAlbumColumns(getResources().getDimensionPixelOffset(R.dimen.width_gridview_divider),NUMBER_OF_COLUMNS));
        /*
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());*/

        Query query = Data.QUERY_TOOlS;

        mAdapter = new ToolAdapter(query, this, R.layout.griditem_tool);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showAddDialog() {
        DialogFragment dialog = DialogFragment_AddTool.newInstance();
        dialog.show(ActivityTools.this.getFragmentManager(), "Add");
    }

    public void showDialog(Object[] list) {
        DialogFragment dialog = DialogFragment_ToolLocator.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", list);
        dialog.setArguments(bundle);
        dialog.show(this.getFragmentManager(), "Locate");
    }
}
