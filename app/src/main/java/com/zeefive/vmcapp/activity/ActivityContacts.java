package com.zeefive.vmcapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ContactRecyclerAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Contact;

import java.io.File;

public class ActivityContacts extends ActivityBase {

    private StorageReference storageReference;
    private static final int RESULT_LOAD_IMG = 1;
    private static int RESULT_CROP = 2;
    private static final String TITLE = "Contacts";
    private ContactRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Contact seletedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_fab);
        setUpActionBar(TITLE, true);

        storageReference = FirebaseStorage.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.showEditorDialog(null);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ContactRecyclerAdapter(getQuery(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

/*

    private void showPopupMenu(View view, final Contact item) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_popup_expiry_date, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_cycle:
                        // todo put condition that for cycling that a date privious to today can only be cycled, and also show a
                        // animation of cycling, it will be co
                        cycleAnItem(item);
                        return true;
                    case R.id.action_edit:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ExpiryDate.ITEM, item);
                        showAddDialog(bundle);
                        return true;
                    case R.id.action_edit_date:
                        showEditDateDialog(item);
                        return true;
                    case R.id.action_delete:
                        ((DatabaseReference)Data.QUERY_EXPIRY_DATES).child(item.getKey()).setValue(null);
                        Toast.makeText(ActivityContacts.this, "Deleted!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void cycleAnItem(ExpiryDate item){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime((long) item.getCreatedAt());
        calendar.setTime(addDaysToDate(date, item.getPeriod()));
        ((DatabaseReference)Data.QUERY_EXPIRY_DATES).child(item.getKey()).child(Data.KEY_CREATED_AT).setValue(calendar.getTimeInMillis());
    }

    private static Date addDaysToDate(Date baseDate, int daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }
*/

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected Query getQuery(){
        return Data.getQuery(getBaseContext(), Data.CONTACTS);
    }
}
