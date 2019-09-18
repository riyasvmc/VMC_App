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
import com.google.firebase.database.FirebaseDatabase;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Tool;


/**
 * Adds a new goal
 */
public class DialogFragment_AddTool extends DialogFragment {
    private Tool item = new Tool();
    private Boolean edit_mode = false;
    EditText mEditText_title;
    EditText mEditText_location;
    CheckBox mCheckBox;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_AddTool newInstance() {
        DialogFragment_AddTool fragment = new DialogFragment_AddTool();
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

        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (Tool) bundle.getSerializable("item");
            edit_mode = true;
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_tool, null);
        mEditText_title = (EditText) rootView.findViewById(R.id.editText_title);
        mEditText_title.setText(item.getTitle());
        mEditText_location = (EditText) rootView.findViewById(R.id.editText_location);
        mEditText_location.setText(item.getLocation());
        mCheckBox = (CheckBox) rootView.findViewById(R.id.checkBox_Concrete);

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton((edit_mode)? "SAVE" : "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText_title.getText().toString()))
                            add();
                    }
                });

        return builder.create();
    }

    /**
     * Add new active list
     */
    public void add() {
        if(edit_mode){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("tools").child(item.getKey());
            Tool new_tool = new Tool(item.getKey(),mEditText_title.getText().toString(), mEditText_location.getText().toString());
            reference.setValue(new_tool);
        }else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("tools").push();
            String key = reference.getKey();
            Tool tool = new Tool(key ,mEditText_title.getText().toString(), mEditText_location.getText().toString());
            reference.setValue(tool);
        }
    }
}