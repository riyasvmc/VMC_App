package com.zeefive.vmcapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Sale;


/**
 * Adds a new goal
 */
public class DialogFragment_AddSale extends DialogFragment {
    EditText mEditText_title;
    EditText mEditText_customer;
    EditText mEditText_amount;
    CheckBox mCheckBox_paid;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_AddSale newInstance() {
        DialogFragment_AddSale fragment = new DialogFragment_AddSale();
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_sale, null);
        mEditText_title = (EditText) rootView.findViewById(R.id.editText_title);
        mEditText_customer = (EditText) rootView.findViewById(R.id.editText_customer);
        mEditText_amount = (EditText) rootView.findViewById(R.id.editText_amount);
        mCheckBox_paid = (CheckBox) rootView.findViewById(R.id.checkbox_paid);

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText_title.getText().toString()))
                            add();
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    /**
     * Add new active list
     */
    public void add() {
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(getActivity(), Data.SALES)).push();
        String key = reference.getKey();
        Sale item = new Sale(key, mEditText_title.getText().toString(), mEditText_customer.getText().toString(),0 , Integer.parseInt(mEditText_amount.getText().toString()), false, ServerValue.TIMESTAMP, false);
        reference.setValue(item);
    }
}