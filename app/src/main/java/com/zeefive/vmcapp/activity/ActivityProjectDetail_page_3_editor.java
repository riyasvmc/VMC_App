package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_Work_DatePicker;
import com.zeefive.vmcapp.model.ExpiryDate;
import com.zeefive.vmcapp.model.Work;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityProjectDetail_page_3_editor extends ActivityBase {

    private static final String TITLE = "Add Work";
    private static final String CONCRETE = Data.TAG_CONCRETE;
    private Work item;
    private boolean edit_mode = false;
    private EditText mEditText_title;
    CheckBox mCheckBox_Concrete;
    CheckBox mCheckBox_Tipper;
    CheckBox mCheckBox_Jcb;
    private static Button mButtonDatePicker;
    private static Calendar sCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_editor);
        setUpActionBar(TITLE, true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            item = (Work) bundle.getSerializable(Work.ITEM);
            edit_mode = true;
        }else {
            item = new Work();
            item.setCreatedAt(System.currentTimeMillis());
        }

        mEditText_title = (EditText) findViewById(R.id.editText_title);
        mEditText_title.setText(item.getTitle());

        mCheckBox_Concrete = (CheckBox) findViewById(R.id.checkBox_Concrete);
        mCheckBox_Tipper = (CheckBox) findViewById(R.id.checkBox_Tipper);
        mCheckBox_Jcb = (CheckBox) findViewById(R.id.checkBox_Jcb);

        mButtonDatePicker = (Button) findViewById(R.id.button_date_picker);
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // set date button text
        sCalendar.setTimeInMillis((long) item.getCreatedAt());
        setButtonText(sCalendar);

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

        List<String> tags = new ArrayList<>();
        if(mCheckBox_Concrete.isChecked()){
            tags.add(CONCRETE);
        }else{
            tags.remove(CONCRETE);
        }

        if(mCheckBox_Tipper.isChecked()){
            tags.add(Data.TAG_TIPPER);
        }else{
            tags.remove(Data.TAG_TIPPER);
        }
        if(mCheckBox_Jcb.isChecked()){
            tags.add(Data.TAG_JCB);
        }else{
            tags.remove(Data.TAG_JCB);
        }

        DatabaseReference reference;
        Work item_new;
        String title = mEditText_title.getText().toString();
        long time = sCalendar.getTimeInMillis();

        if(edit_mode){
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.WORKS)).child(item.getKey());
            item_new = new Work(item.getKey(), title, ActivityProjectDetail.mProject.getKey(), tags, time);
            reference.setValue(item_new);
        }else {
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.WORKS)).push();
            String key = reference.getKey();
            item_new = new Work(key, title, ActivityProjectDetail.mProject.getKey(), tags, time);
            reference.setValue(item_new);
        }
        reference.setValue(item_new);
        finish();
    }

    public void showDatePicker(){
        DialogFragment dialog = new DialogFragment_Work_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putLong(ExpiryDate.ITEM, sCalendar.getTimeInMillis());
        Log.d("Riyas", "Putting time : " + sCalendar.getTimeInMillis());
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "Edit");
    }

    public static void setButtonText(Calendar calendar) {
        sCalendar.setTimeInMillis(calendar.getTimeInMillis());
        mButtonDatePicker.setText(String.format("%1$td %1$tb %1$tY", sCalendar));
    }
}