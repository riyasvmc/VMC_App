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
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Contact;

/**
 * Adds a new goal
 */
public class DialogFragment_AddContact extends DialogFragment {
    private Contact item = new Contact();
    EditText mEditText_title;
    EditText mEditText_designation;
    EditText mEdittext_phone;
    private Boolean edit_mode = false;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_AddContact newInstance() {
        DialogFragment_AddContact fragment = new DialogFragment_AddContact();
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
            item = (Contact) bundle.getSerializable(Contact.ITEM);
            edit_mode = true;
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_contacts, null);
        mEditText_title = (EditText) rootView.findViewById(R.id.editText_title);
        mEditText_designation = (EditText) rootView.findViewById(R.id.editText_designation);
        mEdittext_phone = (EditText) rootView.findViewById(R.id.editText_period);
        mEditText_title.setText(item.getTitle());
        mEditText_designation.setText(item.getDesignation());
        mEdittext_phone.setText(item.getPhone());

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setTitle("Add Contact")
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
        Contact item_new;
        String title = mEditText_title.getText().toString();
        String designation = mEditText_designation.getText().toString();
        String phone = mEdittext_phone.getText().toString();
        if(edit_mode){
            reference = ((DatabaseReference) Data.getQuery(getActivity(), Data.CONTACTS)).child(item.getKey());
            item_new = new Contact(item.getKey(), title, designation,  phone, "", item.getCreatedAt());
        }else {
            reference = ((DatabaseReference) Data.getQuery(getActivity(), Data.CONTACTS)).push();
            item_new = new Contact(reference.getKey(), title, designation, phone, "", ServerValue.TIMESTAMP);
        }
        reference.setValue(item_new);
    }
}