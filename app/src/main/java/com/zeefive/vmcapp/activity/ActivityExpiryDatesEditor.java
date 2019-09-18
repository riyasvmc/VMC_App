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
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.dialog.DialogFragment_ExpiryDate_DatePicker;
import com.zeefive.vmcapp.model.ExpiryDate;

import java.util.Calendar;
import java.util.Date;

public class ActivityExpiryDatesEditor extends ActivityBase implements DatePickerDialog.OnDateSetListener{

    private static final String TITLE = "Add Expiry Date";
    private ExpiryDate item;
    private boolean edit_mode = false;
    private EditText mEditText_title;
    private static Button mButtonDatePicker;
    private static Calendar sCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_dates_editor);
        setUpActionBar(TITLE, true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            item = (ExpiryDate) bundle.getSerializable(ExpiryDate.ITEM);
            edit_mode = true;
        }else {
            item = new ExpiryDate();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expiry_date_editor, menu);
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
        ExpiryDate item_new;
        String title = mEditText_title.getText().toString();
        long time = sCalendar.getTimeInMillis();

        if(edit_mode){
            reference = ((DatabaseReference)Data.getQuery(getBaseContext(), Data.EXPIRY_DATES)).child(item.getKey());
            item_new = new ExpiryDate(item.getKey(), title, 0, time);
        }else {
            reference = ((DatabaseReference) Data.getQuery(getBaseContext(), Data.EXPIRY_DATES)).push();
            item_new = new ExpiryDate(reference.getKey(), title, 0, time);
        }
        reference.setValue(item_new);
        finish();
    }

    public void showDatePicker(ExpiryDate item){
        DialogFragment dialog = new DialogFragment_ExpiryDate_DatePicker();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpiryDate.ITEM, item);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "Edit");
    }

    public static void updateUI() {

        String text = (DateUtils.isToday(sCalendar.getTimeInMillis()) ? "Today" : String.format("%1$td %1$tb %1$tY", sCalendar));
        // String date = DateUtils.getRelativeDateTimeString(context, sCalendar.getTimeInMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_DATE).toString();
        mButtonDatePicker.setText(text);

        // mButtonDatePicker.setText(String.format("%1$td %1$tb %1$tY", sCalendar));
    }

    private static Date addDaysToDate(Date baseDate, int daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
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
