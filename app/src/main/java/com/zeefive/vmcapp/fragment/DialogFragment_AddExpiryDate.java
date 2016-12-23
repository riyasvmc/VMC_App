package com.zeefive.vmcapp.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.ExpiryDate;

import hugo.weaving.DebugLog;

/**
 * Adds a new goal
 */
public class DialogFragment_AddExpiryDate extends DialogFragment {
    private ExpiryDate item = new ExpiryDate();
    EditText mEditText_title;
    EditText mEdittext_period;
    private Boolean edit_mode = false;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    @DebugLog
    public static DialogFragment_AddExpiryDate newInstance() {
        DialogFragment_AddExpiryDate fragment = new DialogFragment_AddExpiryDate();
        return fragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @DebugLog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (ExpiryDate) bundle.getSerializable(ExpiryDate.ITEM);
            edit_mode = true;
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_expiry_date, null);
        mEditText_title = (EditText) rootView.findViewById(R.id.editText_title);
        mEdittext_period = (EditText) rootView.findViewById(R.id.editText_period);
        mEditText_title.setText(item.getTitle());
        mEdittext_period.setText(String.valueOf(item.getPeriod()));

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(edit_mode ? "SAVE" : "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText_title.getText().toString()))
                            add();
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    public void add() {
        DatabaseReference reference;
        ExpiryDate item_new;
        String title = mEditText_title.getText().toString();
        int period = Integer.valueOf(mEdittext_period.getText().toString());
        if(edit_mode){
            reference = ((DatabaseReference)Data.QUERY_EXPIRY_DATES).child(item.getKey());
            item_new = new ExpiryDate(item.getKey(), title, period,item.getCreatedAt());
        }else {
            reference = ((DatabaseReference)Data.QUERY_EXPIRY_DATES).push();
            item_new = new ExpiryDate(reference.getKey(), title, period,ServerValue.TIMESTAMP);
        }
        reference.setValue(item_new);
    }
}