package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ProjectPagerAdapter;
import com.zeefive.vmcapp.animation.PageTransformerPopUp;
import com.zeefive.vmcapp.dialog.DialogFragment_AddProject;

public class ActivityProject extends ActivityBase {

    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    private static final String TITLE = "Projects";
    private ProjectPagerAdapter mProjectPagerAdapter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        setUpActionBar(TITLE, true);

        // Initialize views
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_current:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_completed:
                        mViewPager.setCurrentItem(1);
                        return true;
                }
                return false;
            }
        });

        // Setting view pager
        mProjectPagerAdapter = new ProjectPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mProjectPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new PageTransformerPopUp(PageTransformerPopUp.TransformType.ZOOM));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_project:
                showAddDialog(); break;
        }
        return true;
    }

    public void showAddDialog() {
        DialogFragment dialog = DialogFragment_AddProject.newInstance();
        dialog.show(ActivityProject.this.getFragmentManager(), "Add");
    }
}