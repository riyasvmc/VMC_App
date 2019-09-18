package com.zeefive.vmcapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.MySpinnerArrayAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Project;
import com.zeefive.vmcapp.model.Purchase;

import java.util.ArrayList;
import java.util.List;


/**
 * Adds a new goal
 */
public class DialogFragment_AddPurchase extends DialogFragment {
    private Purchase item;
    private boolean editMode = true;
    EditText mEditText;
    EditText mEditText_From;
    EditText mEditText_Quantity;
    EditText mEditText_Rate;
    EditText mEditText_Amount;
    Spinner mSpinnerFrom;
    private List<String> items = new ArrayList<>();
    private List<Project> list = new ArrayList<>();
    ArrayAdapter<Project> mAdapter;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static DialogFragment_AddPurchase newInstance() {
        DialogFragment_AddPurchase fragment = new DialogFragment_AddPurchase();
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
        if(bundle != null) item = (Purchase) bundle.getSerializable("item");
        if(item == null){
            item = new Purchase();
            editMode = false;
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_purchase, null);
        mEditText = (EditText) rootView.findViewById(R.id.editText_title);
        mEditText.setText(item.getTitle());
        mEditText_From = (EditText) rootView.findViewById(R.id.editText_from);
        mEditText_From.setText(item.getFrom());

        mEditText_Quantity = (EditText) rootView.findViewById(R.id.editText_qty);
        mEditText_Quantity.setText(item.getQuantity());
        mEditText_Quantity.addTextChangedListener(textWatcher);

        mEditText_Rate = (EditText) rootView.findViewById(R.id.editText_rate);
        mEditText_Rate.setText(item.getRate());
        mEditText_Rate.addTextChangedListener(textWatcher);

        mEditText_Amount = (EditText) rootView.findViewById(R.id.editText_amount);
        mEditText_Amount.setText(String.valueOf(item.getAmount()));

        mSpinnerFrom = (Spinner) rootView.findViewById(R.id.spinner_from);
        mAdapter = new MySpinnerArrayAdapter(getActivity(), R.layout.listitem, list);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerFrom.setAdapter(mAdapter);

        final Project project = item.getProject();

        ((DatabaseReference)Data.getQuery(getContext(), Data.PROJECTS)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int position = 0;
                items.clear();
                int i = 0;
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    Project item = areaSnapshot.getValue(Project.class);
                    list.add(item);

                    // Toast.makeText(getActivity(), project.getKey()+ " : " + item.getKey(), Toast.LENGTH_LONG).show();
                    if(project != null && item.getKey().equals(project.getKey())){
                            position = i;
                        Toast.makeText(getActivity(), "Done.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Failed.", Toast.LENGTH_SHORT).show();
                    }

                    i++;
                }
                mAdapter.notifyDataSetChanged();
                mSpinnerFrom.setSelection(position, true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setTitle("Add Purchase")
                .setPositiveButton((editMode)? "SAVE" : "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!TextUtils.isEmpty(mEditText.getText().toString()))
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
        DatabaseReference reference;
        Object createdAt;
        if(editMode){
            reference = ((DatabaseReference)Data.getQuery(getActivity(), Data.PURCHASE)).child(item.getKey());
            createdAt = item.getCreatedAt();
        }else {
            reference = ((DatabaseReference)Data.getQuery(getActivity(), Data.PURCHASE)).push();
            createdAt = ServerValue.TIMESTAMP;
        }
        String key = reference.getKey();
        Purchase new_item = new Purchase(key,
                mEditText.getText().toString(),
                ((Project)mSpinnerFrom.getSelectedItem()).getKey(),
                (Project) mSpinnerFrom.getSelectedItem(),
                mEditText_Quantity.getText().toString(),
                mEditText_Rate.getText().toString(),
                Integer.parseInt(mEditText_Amount.getText().toString()),
                mEditText_From.getText().toString(),
                item.getShop(),
                createdAt);
        reference.setValue(new_item);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                float quantity = Float.valueOf(mEditText_Quantity.getText().toString());
                float rate = Float.valueOf(mEditText_Rate.getText().toString());
                int amount = (int) (quantity * rate);
                mEditText_Amount.setText(String.valueOf(amount));
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Invalid Number format", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
            }
        }
    };
}