package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ProjectAdapter;
import com.zeefive.vmcapp.data.Data;


public class ActivityProject_page_2 extends Fragment {
    public static final String TITLE = "COMPLETED";
    private ProjectAdapter mAdapter;
    public static RecyclerView mRecyclerView;
    private LinearLayout emptyView;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fab, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        emptyView = (LinearLayout) view.findViewById(R.id.empty_view);

        // hide fab
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.hide();

        Query query = Data.getQuery(getContext(), Data.PROJECTS).orderByChild(Data.KEY_COMPLETED).equalTo(true);
        query.addValueEventListener(valueEventListener);

        mAdapter = new ProjectAdapter(query, (ActivityProject) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
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
    };
}