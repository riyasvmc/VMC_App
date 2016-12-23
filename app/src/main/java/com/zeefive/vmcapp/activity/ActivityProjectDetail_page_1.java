package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.activity.ActivityProjectDetail;
import com.zeefive.vmcapp.adapter.TodoAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ToDo;
import com.zeefive.vmcapp.view.helper.SimpleItemTouchHelperCallback;

import static com.zeefive.vmcapp.adapter.TodoAdapter.mActionMode;

public class ActivityProjectDetail_page_1 extends Fragment {
    public static final String TITLE = "TODO";
    private FirebaseAuth mAuth;
    private TodoAdapter mAdapter;
    public static RecyclerView mRecyclerView;
    private EditText mEditText;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_todo, container, false);

        mEditText = (EditText) view.findViewById(R.id.editText);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                ToDo item = new ToDo("", mEditText.getText().toString(), ActivityProjectDetail.mProject, false, -1, ServerValue.TIMESTAMP);
                mAdapter.save(item);
                mEditText.setText("");
                return true;
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query query = (Data.QUERY_TODOS).orderByChild("project/title").equalTo(ActivityProjectDetail.mProject.getTitle());

        mAdapter = new TodoAdapter(query, (ActivityProjectDetail) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser && mActionMode != null){
            mActionMode.finish();
        }
    }
}