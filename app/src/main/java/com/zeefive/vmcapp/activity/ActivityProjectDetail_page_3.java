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
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.WorkAdapter;
import com.zeefive.vmcapp.data.Data;

public class ActivityProjectDetail_page_3 extends Fragment {

    public static final String TITLE = "WORK";
    private WorkAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fab, container, false);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.showEditorActivity(null);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Query query = (Data.getQuery(getContext(), Data.WORKS)).orderByChild(Data.KEY_PROJECT_KEY).equalTo(ActivityProjectDetail.mProject.getKey());

        mAdapter = new WorkAdapter(query, (ActivityBase) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
