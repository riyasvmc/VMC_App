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

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.HomeMenuRecyclerAdapter;
import com.zeefive.vmcapp.data.Data;

public class MainActivity_page_3 extends Fragment {
    public static final String TITLE = "SETTINGS";
    private HomeMenuRecyclerAdapter mAdapter;
    public static RecyclerView mRecyclerView;
    private FloatingActionButton floatingActionButton;
    private LinearLayout emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fab, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // set/Hide empty view
        emptyView = (LinearLayout) view.findViewById(R.id.empty_view);
        emptyView.setVisibility(View.GONE);

        // hide fab
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.hide();

        mAdapter = new HomeMenuRecyclerAdapter((ActivityBase) getActivity(), getContext(), Data.SETTINGS_MENU_ITEMS);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}