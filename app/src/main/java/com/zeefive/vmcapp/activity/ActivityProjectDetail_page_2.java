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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.PurchaseAdapter;
import com.zeefive.vmcapp.data.Data;

public class ActivityProjectDetail_page_2 extends Fragment {

    public static final String TITLE = "PURCHASE";
    private FirebaseAuth mAuth;
    private PurchaseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fab, container, false);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Query query = Data.getQuery(getActivity(), Data.PURCHASE).orderByChild(Data.KEY_PROJECT_KEY).equalTo(ActivityProjectDetail.mProject.getKey());

        mAdapter = new PurchaseAdapter(query, (ActivityBase)getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
