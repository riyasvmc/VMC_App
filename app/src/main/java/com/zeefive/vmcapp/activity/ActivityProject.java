package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ProjectAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.fragment.DialogFragment_AddProject;

public class ActivityProject extends ActivityBase {

    private static final String TITLE = "Projects";
    private FirebaseAuth mAuth;
    private ProjectAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);
        setUpActionBar(TITLE, true);

        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Floating Action Button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        // project query
        Query query = Data.QUERY_PROJECTS_ORD_BY_COMPLETED;

        mAdapter = new ProjectAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id){
        }
        return true;
    }

    public void showAddDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = DialogFragment_AddProject.newInstance();
        dialog.show(ActivityProject.this.getFragmentManager(), "Add");
    }
}