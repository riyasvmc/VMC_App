package com.zeefive.vmcapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.activity.ActivityProjectDetail;
import com.zeefive.vmcapp.data.Data;


/**
 * Adds a new goal
 */
public class DialogFragment_DeleteProject extends DialogFragment {
    EditText mEditText;
    TextView mTextView;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_DeleteProject newInstance() {
        DialogFragment_DeleteProject fragment = new DialogFragment_DeleteProject();
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
        View rootView = inflater.inflate(R.layout.dialog_delete_project, null);
        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mEditText = (EditText) rootView.findViewById(R.id.editText);

        // set message to textView from html
        mTextView.setText(Html.fromHtml("<font color='#000'>Are you sure you want to delete this project?<br> Type </font><font color='#ffbb00'> Delete </font><font color='#000'> for confirm.</font>"));

       builder.setView(rootView)
               .setPositiveButton("Ok", null)
               .setNegativeButton("Cancel", null)
               .setTitle("Delete: " + ActivityProjectDetail.mProject.getTitle());

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

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mEditText.getText().toString().trim().equalsIgnoreCase(Data.PASSWORD_FOR_DELETE)) {
                ((DatabaseReference)Data.getQuery(getActivity(), Data.PROJECTS)).child(ActivityProjectDetail.mProject.getKey()).setValue(null);
                Toast.makeText(getActivity(), "Project Deleted!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                dismiss();
            }else{
                mEditText.setError("Confirmation text is incorrect");
            }
        }
    };
}