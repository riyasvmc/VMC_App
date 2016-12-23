package com.zeefive.vmcapp.activity;

import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.adapter.HomeMenuAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.model.HomeMenuItem;

import java.io.File;

public class MainActivity extends ActivityBase {

    /**
     * todo separate anonymous adapter implementation to separate package
     * todo Add confirmation for every delete
     * todo unify context menu trigger
     * todo complete Sales context codes, add items(Paid, etc..); note: add tags to Sale items( like Rent, Material, etc...)
     * todo make a tags activity in general with tag title as key
     * todo Complete the purchase activity ( diesel, steel, petrol, etc...)
     * todo a method to log events activity
     * todo contacts storage, with photo
     *
     */

    public static final String KEY = "key";
    // Views
    private ListView mListView;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar("VMC", false);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_main_actionbar_title);

        mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<HomeMenuItem> mAdapter = new HomeMenuAdapter(this, R.layout.listitem, Data.HOME_MENU_ITEMS);
        mListView.setAdapter(mAdapter);
        mListView.setDividerHeight(0);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_about:
                goToNextActivity(AboutActivity.class);
                break;
        }
        return true;
    }

    private void showDocument(File file) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(mFile), "application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // initCollectionPicker();
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String title = Data.HOME_MENU_ITEMS[position].getTitle();

            if(title.equals(Data.ITEM_PROJECT.getTitle())){
                goToNextActivity(ActivityProject.class);
            }else if(title.equals(Data.ITEM_SALE.getTitle())){
                goToNextActivity(ActivitySale.class);
            }else if(title.equals(Data.ITEM_ACCOUNTS.getTitle())){
                goToNextActivity(ActivityAccount.class);
            }else if(title.equals(Data.ITEM_SHOPS.getTitle())){
                goToNextActivity(ActivityShop.class);
            }else if(title.equals(Data.ITEM_TODO.getTitle())){
                goToNextActivity(ActivityTodo.class);
            }else if(title.equals(Data.ITEM_TOOLS.getTitle())){
                goToNextActivity(ActivityTools.class);
            }else if(title.equals(Data.ITEM_CONCRETE_CALCULATOR.getTitle())){
                goToNextActivity(ActivityConcreteCalculator.class);
            }else if(title.equals(Data.ITEM_EXPIRY_DATES.getTitle())){
                goToNextActivity(ActivityExpiryDates.class);
            }
        }
    };

    private void goToNextActivity(Class mTargetActivity) {
        if(mTargetActivity != null){
            Intent i = new Intent(this, mTargetActivity);
            startActivity(i);
        }
    }

}
