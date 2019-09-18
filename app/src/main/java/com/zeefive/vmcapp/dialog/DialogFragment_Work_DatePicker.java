package com.zeefive.vmcapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.activity.ActivityProjectDetail_page_3_editor;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Work;

import java.util.Calendar;
import java.util.Date;

public class DialogFragment_Work_DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private long time;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null) {
            time = bundle.getLong("item");
            Log.d("Riyas", "Getting time : " + time);
        }else{
            time = System.currentTimeMillis();
        }

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(time);
        calendar.setTime(date);

        return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yy);
        calendar.set(Calendar.MONTH, mm);
        calendar.set(Calendar.DAY_OF_MONTH, dd);
        ActivityProjectDetail_page_3_editor.setButtonText(calendar);
    }

}