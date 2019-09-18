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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Shop;


/**
 * Adds a new goal
 */
public class DialogFragment_AddShop extends DialogFragment {

    EditText mEditText_title;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_AddShop newInstance() {
        DialogFragment_AddShop fragment = new DialogFragment_AddShop();
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
        View rootView = inflater.inflate(R.layout.dialog_add_shop, null);
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
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(mOnClickListener);
            }
        });
        return alertDialog;
    }

    /**
     * Add new active list
     */
    public void add() {
        DatabaseReference reference = ((DatabaseReference)Data.getQuery(getActivity(), Data.SHOPS)).push();
        String key = reference.getKey();
        Shop item = new Shop(key, mEditText_title.getText().toString());
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