package io.yulw.rcctrl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import io.yulw.rcctrl.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.astuetz.PagerSlidingTabStrip;
import io.yulw.rcctrl.adapter.HomeViewPagerAdapter;
import android.util.Log;
/**
 * Created by yulw on 6/8/2015.
 */
public class HomeActivity extends BaseActivity
{
    private ArrayList<String> mViewPagerTabsTitles;
    private ArrayList<String> mFragmentMenuTitles;
    private PagerSlidingTabStrip mPagerTabStrip;
    private ViewPager mViewPager;
    private final String TAG="HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }
    protected  int getLayoutID() {
       return R.layout.screen_home;
    }
    protected void loadAddtionalComponents()
    {
        mViewPagerTabsTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_pager_tabs_titles)));
        mFragmentMenuTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
        mViewPager = (ViewPager) findViewById(R.id.screen_home_view_pager);
        mPagerTabStrip=(PagerSlidingTabStrip)findViewById(R.id.screen_home_pager_sliding_tab);
        Log.d(TAG,"Fragments Titles Loaded.");
        for(String s: mViewPagerTabsTitles)
            Log.d(TAG,"tab# "+s);
        for(String s: mFragmentMenuTitles)
            Log.d(TAG,"frag# "+s);
        try {
            HomeViewPagerAdapter adpt=new HomeViewPagerAdapter(getSupportFragmentManager(),mViewPagerTabsTitles);
            mViewPager.setAdapter(adpt);
        }
        catch (NullPointerException npe){
            Log.d(TAG,"NULL Point Exception."+npe.getMessage());
        }
        catch(Exception e) {
            Log.d(TAG,e.toString());
        }
        //mPagerTabStrip.setEnabled(true);
        mPagerTabStrip.setViewPager(mViewPager);
    }
    protected String getToolbarTitle() {
        return "RC CLIENT";
    }
}
