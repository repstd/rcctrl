package io.yulw.rcctrl.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Arrays;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.HomeViewPagerAdapter;
import io.yulw.rcctrl.utils.rcutil;

/**
 * Created by yulw on 2015/6/10.
 */
public class ContainerFragment extends BaseFragment
{
    private final String TAG="ContainerFragment";
    private static ContainerFragment mInst=null;
    private ArrayList<String> mViewPagerTabsTitles;
    private ArrayList<String> mFragmentMenuTitles;
    private PagerSlidingTabStrip mPagerTabStrip;
    private ViewPager mViewPager;
    public static ContainerFragment instance() {
        if(mInst==null)
            mInst=new ContainerFragment();
        return mInst;
    }
    public ContainerFragment() {
    }
    @Override
    protected void initLayout()  {
        setLayoutId(R.layout.screen_home_fragment_container);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        return inflater.inflate(getLayoutID(),container,false);
    }
    protected void loadAddtionalComponents()
    {
        mViewPagerTabsTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_pager_tabs_titles)));
        mFragmentMenuTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
        mViewPager = (ViewPager) getView().findViewById(R.id.screen_home_view_pager);
        mPagerTabStrip=(PagerSlidingTabStrip)getView().findViewById(R.id.screen_home_pager_sliding_tab);
        Log.d(TAG, "Fragments Titles Loaded.");
        for(String s: mViewPagerTabsTitles)
            Log.d(TAG,"tab# "+s);
        for(String s: mFragmentMenuTitles)
            Log.d(TAG,"frag# "+s);
        try {
            HomeViewPagerAdapter adpt=new HomeViewPagerAdapter(getActivity().getSupportFragmentManager(),mViewPagerTabsTitles);
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
        rcutil.showMessageAsToast(getActivity().getApplicationContext(), "AdditionalComponentesLoaded.");
    }
    protected String getToolbarTitle() {
        return "RC CLIENT";
    }
}
