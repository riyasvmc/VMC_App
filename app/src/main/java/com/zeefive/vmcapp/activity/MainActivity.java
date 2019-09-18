package com.zeefive.vmcapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.HomePagerAdapter;
import com.zeefive.vmcapp.animation.PageTransformerPopUp;
import com.zeefive.vmcapp.view.NonSwipableViewPager;

public class MainActivity extends ActivityBase implements
        GoogleApiClient.OnConnectionFailedListener{

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

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private NonSwipableViewPager viewPager;
    private static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar("VMC Manager", false);
        // getSupportActionBar().hide();

        /*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_main_actionbar_title);*/

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_todo:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_settings:
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });

        textView = findViewById(R.id.username);
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new PageTransformerPopUp(PageTransformerPopUp.TransformType.FLOW));
        viewPager.setOnTouchListener(null);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user loged in
                } else {
                    // Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                    goToLogInActivity();
                }
                // ...
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_logout:
                revokeAccess();
                break;
            case R.id.action_about:
                goToNextActivity(ActivityAbout.class);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void goToNextActivity(Class mTargetActivity) {
        if(mTargetActivity != null){
            Intent i = new Intent(this, mTargetActivity);
            startActivity(i);
        }
    }

    public static void revokeAccess() {
        mAuth.signOut();
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void goToLogInActivity(){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}