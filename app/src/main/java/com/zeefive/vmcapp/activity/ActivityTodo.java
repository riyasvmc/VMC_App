package com.zeefive.vmcapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.TodoAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ToDo;
import com.zeefive.vmcapp.view.helper.SimpleItemTouchHelperCallback;

import java.util.BitSet;
import java.util.UUID;

import static com.zeefive.vmcapp.adapter.TodoAdapter.mActionMode;

public class ActivityTodo extends ActivityBase {

    private static final String TITLE = "Todos";
    private static Context mContext;
    private static TodoAdapter mAdapter;
    public static RecyclerView mRecyclerView;
    private EditText mEditText;

    // printer
    private static final String TAG = "Riyas Vmc";
    private static final String SUCCESS = "success";
    private static final int BLUETOOTH_ENABLE = 18;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BitSet dots;

    private ItemTouchHelper mItemTouchHelper;
    private LinearLayout emptyView;

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

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setUpActionBar(TITLE, true);

        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // hide fab
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.hide();

        // set empty view
        emptyView = (LinearLayout) findViewById(R.id.empty_view);

        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                ToDo item = new ToDo("", mEditText.getText().toString(), null, false, -1, ServerValue.TIMESTAMP);
                mAdapter.save(item);
                mEditText.setText("");
                return true;
            }
        });

        Query query = Data.getQuery(getBaseContext(), Data.TODOS);
        query.addValueEventListener(valueEventListener);

        mAdapter = new TodoAdapter(query, this);
        mAdapter.registerAdapterDataObserver(adapterDataObserver);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Toast.makeText(getBaseContext(), event.getAction(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActionMode = null;
    }

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            int count = mAdapter.getItemCount();
            int visiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if(visiblePosition == -1 || (positionStart >= (count - 1) && visiblePosition == (positionStart -1))){
                mRecyclerView.scrollToPosition(positionStart);
            }
        }
    };
}