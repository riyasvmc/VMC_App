package com.zeefive.vmcapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.Contact;

import java.io.IOException;

public class ActivityContactsDetail extends ActivityBase {
    private static final String TITLE = "Detail";
    private Contact item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setUpActionBar(TITLE, true);

        item = (Contact) getIntent().getExtras().getSerializable(Contact.ITEM);
        if(item == null){
            item  = new Contact();
        }

        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.action_dial:
                startActivity(Utilities.getDialIntent(item.getPhone()));
                return true;

            case R.id.action_edit:
                return true;

            case R.id.action_delete:
                deleteItem();
                return true;
        }
        return false;
    }

    private void deleteItem(){
        ((DatabaseReference) Data.getQuery(this, Data.CONTACTS)).child(item.getKey()).setValue(null);
        finish();
    }
}
