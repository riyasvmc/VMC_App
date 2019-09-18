package com.zeefive.vmcapp.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.adapter.ProjectDetailPagerAdapter;
import com.zeefive.vmcapp.animation.PageTransformerPopUp;
import com.zeefive.vmcapp.dialog.DialogFragment_DeleteProject;
import com.zeefive.vmcapp.model.Project;

public class ActivityProjectDetail extends ActivityBase {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ProjectDetailPagerAdapter mProjectDetailPagerAdapter;
    public static Project mProject = new Project();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        // get passed item
        mProject = (Project) getIntent().getSerializableExtra(Project.ITEM);

        setUpActionBar(mProject.getTitle(), true);

        // Initialize views
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Setting view pager
        mProjectDetailPagerAdapter = new ProjectDetailPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mProjectDetailPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new PageTransformerPopUp(PageTransformerPopUp.TransformType.ZOOM));

        mTabLayout.setupWithViewPager(mViewPager);

        /*mTabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        mTabLayout.getTabAt(0).setText("");
        mTabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
        mTabLayout.getTabAt(1).setText("");
        mTabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);
        mTabLayout.getTabAt(2).setText("");*/
        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        switch (id){
            case R.id.action_remove:
                showDeleteConformationDialog();
        }
        return true;
    }

    private void showDeleteConformationDialog() {
        DialogFragment dialog = DialogFragment_DeleteProject.newInstance();
        dialog.show(ActivityProjectDetail.this.getFragmentManager(), "Delete");
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            /*mViewPager.setCurrentItem(tab.getPosition());
            getSupportActionBar().setTitle(TAB_TITLES[tab.getPosition()]);

            switch (tab.getPosition()){
                case 0 : tab.setIcon(R.drawable.ic_work_accent_24dp);
                    break;
                case 1 : tab.setIcon(R.drawable.ic_account_accent_24dp); break;
                case 2 : tab.setIcon(R.drawable.ic_settings_accent_24dp); break;
            }*/
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            /*switch (tab.getPosition()){
                case 0 : tab.setIcon(R.drawable.ic_work_white_24dp); break;
                case 1 : tab.setIcon(R.drawable.ic_account_white_24dp); break;
                case 2 : tab.setIcon(R.drawable.ic_settings_white_24dp); break;
            }*/
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };
}