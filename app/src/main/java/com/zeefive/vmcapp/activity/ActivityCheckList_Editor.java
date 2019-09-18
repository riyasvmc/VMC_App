package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_Work_DatePicker;
import com.zeefive.vmcapp.model.CheckList;
import com.zeefive.vmcapp.model.ExpiryDate;
import com.zeefive.vmcapp.model.Work;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityCheckList_Editor extends ActivityBase {

    private static final String TITLE = "Add Check List";
    private CheckList item;
    private boolean edit_mode = false;
    private EditText mEditText_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_editor);
        setUpActionBar(TITLE, true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            item = (CheckList) bundle.getSerializable(CheckList.ITEM);
            edit_mode = true;
        }else {
            item = new CheckList();
        }

        mEditText_title = (EditText) findViewById(R.id.editText_title);
        mEditText_title.setText(item.getTitle());

        // set icon for close
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                save();
                break;
        }
        return true;
    }

    private void save(){
        DatabaseReference reference;
        CheckList item_new;
        String title = mEditText_title.getText().toString();

        if(edit_mode){
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.CHECK_LIST)).child(item.getKey());
            item_new = new CheckList(item.getKey(), title, null);
            reference.setValue(item_new);
        }else {
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.CHECK_LIST)).push();
            String key = reference.getKey();
            item_new = new CheckList(key, title, null);
            reference.setValue(item_new);
        }
        reference.setValue(item_new);
        finish();
    }
}