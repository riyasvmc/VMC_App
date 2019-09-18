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
import com.zeefive.vmcapp.model.Tool;


/**
 * Adds a new goal
 */
public class DialogFragment_ToolLocator extends DialogFragment {

    EditText mEditText_location;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_ToolLocator newInstance() {
        DialogFragment_ToolLocator fragment = new DialogFragment_ToolLocator();
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
        View rootView = inflater.inflate(R.layout.dialog_tool_locator, null);
        mEditText_location = (EditText) rootView.findViewById(R.id.editText_location);

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .setTitle("Enter Location");


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
    public void setLocation() {
        Object[] list = (Object[]) getArguments().getSerializable("key");
        for(Object object: list) {
            Tool item = (Tool) object;
            ((DatabaseReference)Data.getQuery(getActivity(), Data.TOOLS)).child(item.getKey()).child(Data.KEY_LOCATION).setValue(mEditText_location.getText().toString());
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!TextUtils.isEmpty(mEditText_location.getText().toString())) {
                setLocation();
                dismiss();
            }else{
                Toast.makeText(getActivity(), "Enter Location.", Toast.LENGTH_SHORT).show();
            }
        }
    };
}