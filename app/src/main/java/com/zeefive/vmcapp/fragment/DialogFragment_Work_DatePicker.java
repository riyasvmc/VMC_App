package com.zeefive.vmcapp.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Work;

import java.util.Calendar;
import java.util.Date;

public class DialogFragment_Work_DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Work item = new Work();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (Work) bundle.getSerializable("item");
        }

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime((long) item.getCreatedAt());
        calendar.setTime(date);

        return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yy);
        calendar.set(Calendar.MONTH, mm);
        calendar.set(Calendar.DAY_OF_MONTH, dd);
        ((DatabaseReference)Data.QUERY_WORKS).child(item.getKey()).child(Data.KEY_CREATED_AT).setValue(calendar.getTimeInMillis());
    }

}