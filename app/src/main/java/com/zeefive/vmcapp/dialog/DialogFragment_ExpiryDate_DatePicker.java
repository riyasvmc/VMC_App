package com.zeefive.vmcapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.zeefive.vmcapp.activity.ActivityExpiryDatesEditor;
import com.zeefive.vmcapp.model.ExpiryDate;

import java.util.Calendar;
import java.util.Date;

public class DialogFragment_ExpiryDate_DatePicker extends DialogFragment {

    private ExpiryDate item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (ExpiryDate) bundle.getSerializable("item");
        }
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime((long) item.getCreatedAt());
        calendar.setTime(date);
        return new DatePickerDialog(getActivity(), (ActivityExpiryDatesEditor) getActivity(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}