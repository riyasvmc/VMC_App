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
import com.zeefive.vmcapp.activity.ActivityAccountDetail;
import com.zeefive.vmcapp.activity.ActivityProjectDetail;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Payment;
import com.zeefive.vmcapp.model.Purchase;

import hugo.weaving.DebugLog;

/**
 * Adds a new goal
 */
public class DialogFragment_AddPayment extends DialogFragment {
    private Payment item;
    private boolean editMode = true;
    EditText mEditText_PaidAmount;
    EditText mEditText_PayableAmount;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    @DebugLog
    public static DialogFragment_AddPayment newInstance() {
        DialogFragment_AddPayment fragment = new DialogFragment_AddPayment();
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
        if(bundle != null) item = (Payment) bundle.getSerializable("item");
        if(item == null){
            item = new Payment();
            editMode = false;
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_payment, null);

        mEditText_PaidAmount = (EditText) rootView.findViewById(R.id.editText_paid);
        mEditText_PaidAmount.setText(String.valueOf(item.getPayableAmount()));

        mEditText_PayableAmount = (EditText) rootView.findViewById(R.id.editText_payable);
        mEditText_PayableAmount.setText(String.valueOf(item.getPaidAmount()));

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setTitle("Add Payment")
                .setPositiveButton((editMode)? "SAVE" : "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText_PaidAmount.getText().toString()))
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

        DatabaseReference query = (DatabaseReference) Data.QUERY_PAYMENT;

        if(editMode){
            DatabaseReference reference = query.child(item.getKey());
            Payment new_item = new Payment(item.getKey(), ActivityAccountDetail.mItem, mEditText_PayableAmount.getText().toString(), mEditText_PaidAmount.getText().toString(), ServerValue.TIMESTAMP);
            reference.setValue(new_item);
        }else {
            DatabaseReference reference = query.push();
            String key = reference.getKey();
            Payment new_item = new Payment(key, ActivityAccountDetail.mItem, mEditText_PayableAmount.getText().toString(), mEditText_PaidAmount.getText().toString(), ServerValue.TIMESTAMP);
            reference.setValue(new_item);
        }
    }
}