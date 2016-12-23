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
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityProjectDetail;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Work;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Adds a new goal
 */
public class DialogFragment_AddWork extends DialogFragment {
    private static final String CONCRETE = Data.TAG_CONCRETE;
    private Work item;
    private Boolean edit_mode = false;
    EditText mEditText;
    CheckBox mCheckBox_Concrete;
    CheckBox mCheckBox_Tipper;
    CheckBox mCheckBox_Jcb;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    @DebugLog
    public static DialogFragment_AddWork newInstance() {
        DialogFragment_AddWork fragment = new DialogFragment_AddWork();
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
            item = (Work) bundle.getSerializable("item");
            edit_mode = true;
        }
        if(item == null){
            item = new Work();
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_work, null);
        mEditText = (EditText) rootView.findViewById(R.id.editText_title);
        mCheckBox_Concrete = (CheckBox) rootView.findViewById(R.id.checkBox_Concrete);
        mCheckBox_Tipper = (CheckBox) rootView.findViewById(R.id.checkBox_Tipper);
        mCheckBox_Jcb = (CheckBox) rootView.findViewById(R.id.checkBox_Jcb);
        if(item.getTags().contains(CONCRETE)){
            mCheckBox_Concrete.setChecked(true);
        }
        if(item.getTags().contains(Data.TAG_TIPPER)){
            mCheckBox_Tipper.setChecked(true);
        }
        if(item.getTags().contains(Data.TAG_JCB)){
            mCheckBox_Jcb.setChecked(true);
        }
        mEditText.setText(item.getTitle());

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton((edit_mode)? "SAVE" : "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText.getText().toString()))
                            add();
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    public void add() {
        List<String> tags = new ArrayList<>();
        if(mCheckBox_Concrete.isChecked()){
            tags.add(CONCRETE);
        }else{
            tags.remove(CONCRETE);
        }

        if(mCheckBox_Tipper.isChecked()){
            tags.add(Data.TAG_TIPPER);
        }else{
            tags.remove(Data.TAG_TIPPER);
        }
        if(mCheckBox_Jcb.isChecked()){
            tags.add(Data.TAG_JCB);
        }else{
            tags.remove(Data.TAG_JCB);
        }

        if(edit_mode){
            DatabaseReference reference = ((DatabaseReference)Data.QUERY_WORKS).child(item.getKey());
            Work new_item = new Work(item.getKey(), mEditText.getText().toString(), ActivityProjectDetail.mProject.getKey(), tags, item.getCreatedAt());
            reference.setValue(new_item);
        }else {
            DatabaseReference reference = ((DatabaseReference)Data.QUERY_WORKS).push();
            String key = reference.getKey();
            Work new_item = new Work(key, mEditText.getText().toString(), ActivityProjectDetail.mProject.getKey(), tags, ServerValue.TIMESTAMP);
            reference.setValue(new_item);
        }
    }

}