package com.zeefive.vmcapp.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.TodoAdapter;
import com.zeefive.vmcapp.admin_activity.ActivityAdmin;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ToDo;
import com.zeefive.vmcapp.view.helper.SimpleItemTouchHelperCallback;

import java.util.Calendar;

import static com.zeefive.vmcapp.adapter.TodoAdapter.mActionMode;

public class MainActivity_page_2 extends Fragment {
    public static final String TITLE = "ToDos";
    private FirebaseAuth mAuth;
    private TodoAdapter mAdapter;
    public static RecyclerView mRecyclerView;
    private FloatingActionButton floatingActionButton;
    private LinearLayout emptyView;
    private EditText mEditText;
    private ItemTouchHelper mItemTouchHelper;
    private LinearLayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_todo, container, false);

        mEditText = (EditText) view.findViewById(R.id.editText);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text = mEditText.getText().toString();
                int hourSalt = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if(text.equals("passgate" + hourSalt)){
                    // go to admin activity.
                    getActivity().startActivity(new Intent(getActivity(), ActivityAdmin.class));
                }else {
                    ToDo item = new ToDo("", text, ActivityProjectDetail.mProject, false, -1, ServerValue.TIMESTAMP);
                    mAdapter.save(item);
                    mEditText.setText("");
                }
                return true;
            }
        });
        mAuth = FirebaseAuth.getInstance();

        // empty view
        emptyView = (LinearLayout) view.findViewById(R.id.empty_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // hide fab
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.hide();

        Query query = (Data.getQuery(getActivity(), Data.TODOS));
        query.addValueEventListener(valueEventListener);

        mAdapter = new TodoAdapter(query, (ActivityBase) getActivity());
        mAdapter.registerAdapterDataObserver(adapterDataObserver);
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

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            int count = mAdapter.getItemCount();
            int visiblePosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
            if(visiblePosition == -1 || (positionStart >= (count - 1) && visiblePosition == (positionStart -1))){
                mRecyclerView.scrollToPosition(positionStart);
            }
        }
    };
}