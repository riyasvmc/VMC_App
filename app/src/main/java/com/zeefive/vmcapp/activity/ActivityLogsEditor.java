package com.zeefive.vmcapp.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_ExpiryDate_DatePicker;
import com.zeefive.vmcapp.dialog.DialogFragment_Log_DatePicker;
import com.zeefive.vmcapp.model.ExpiryDate;
import com.zeefive.vmcapp.model.Log;

import java.util.Calendar;
import java.util.Date;

public class ActivityLogsEditor extends ActivityBase implements DatePickerDialog.OnDateSetListener {

    private static final String TITLE = "Add Logs";
    private Log item;
    private boolean edit_mode = false;
    private EditText mEditText_title;
    public static Button mButtonDatePicker;
    private static Calendar sCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_dates_editor);
        setUpActionBar(TITLE, true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            item = (Log) bundle.getSerializable(Log.ITEM);
            edit_mode = true;
        }else {
            item = new Log();
            item.setCreatedAt(System.currentTimeMillis());
        }

        mEditText_title = (EditText) findViewById(R.id.editText_title);
        mEditText_title.setText(item.getTitle());

        mButtonDatePicker = (Button) findViewById(R.id.button_date_picker);
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(item);
            }
        });

        // set date button text
        sCalendar.setTimeInMillis((long) item.getCreatedAt());
        updateUI();

        // set icon for close
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }

    private void showDatePicker(Log item){
        DialogFragment dialog = new DialogFragment_Log_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Log.ITEM, item);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "Edit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_editor, menu);
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
        Log item_new;
        String title = mEditText_title.getText().toString();
        long time = sCalendar.getTimeInMillis();

        if(edit_mode){
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.LOGS)).child(item.getKey());
            item_new = new Log(item.getKey(), title, time);
        }else {
            reference = ((DatabaseReference) Data.getQuery(getBaseContext(), Data.LOGS)).push();
            item_new = new Log(reference.getKey(), title, time);
        }
        reference.setValue(item_new);
        finish();
    }

    public static void updateUI() {
        String text = (DateUtils.isToday(sCalendar.getTimeInMillis()) ? "Today" : String.format("%1$td %1$tb %1$tY", sCalendar));
        mButtonDatePicker.setText(String.format("%1$td %1$tb %1$tY", sCalendar));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        sCalendar = calendar;
        item.setCreatedAt(sCalendar.getTimeInMillis());
        updateUI();
    }
}
