package com.zeefive.vmcapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

public class ActivityBase extends AppCompatActivity{

    public static Context context;
    private static final float ACTIONBAR_ELEVATION = 0f;
    private ProgressDialog mProgressDialog;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    public void setUpActionBar(String title, boolean homeButtonEnabled){
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(title);
        mActionBar.setElevation(ACTIONBAR_ELEVATION);
        mActionBar.setHomeButtonEnabled(homeButtonEnabled);
        mActionBar.setDisplayHomeAsUpEnabled(homeButtonEnabled);
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public static void showPopupMenu(View view, int menu, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(menu, popup.getMenu());
        popup.setOnMenuItemClickListener(listener);
        popup.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
