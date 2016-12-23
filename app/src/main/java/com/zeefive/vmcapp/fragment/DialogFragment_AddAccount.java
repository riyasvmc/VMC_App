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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Account;
import com.zeefive.vmcapp.model.Shop;

import hugo.weaving.DebugLog;

/**
 * Adds a new goal
 */
public class DialogFragment_AddAccount extends DialogFragment {

    EditText mEditText_title;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    @DebugLog
    public static DialogFragment_AddAccount newInstance() {
        DialogFragment_AddAccount fragment = new DialogFragment_AddAccount();
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

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_account, null);
        mEditText_title = (EditText) rootView.findViewById(R.id.editText_title);

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .setTitle("New");


        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(mOnClickListener);
            }
        });
        return alertDialog;
    }

    /**
     * Add new active list
     */
    public void add() {
        DatabaseReference reference = ((DatabaseReference)Data.QUERY_ACCOUNTS).push();
        String key = reference.getKey();
        Account item = new Account(key, mEditText_title.getText().toString());
        reference.setValue(item);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!TextUtils.isEmpty(mEditText_title.getText().toString())) {
                add();
                dismiss();
            }else{
                Toast.makeText(getActivity(), "Please Complete the Form.", Toast.LENGTH_SHORT).show();
            }
        }
    };
}