package io.yulw.rcctrl.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private static ContainerFragment mInst = null;
    private final String TAG = "ContainerFragment";
    private ArrayList<String> mViewPagerTabsTitles = null;
    private ArrayList<String> mFragmentMenuTitles = null;
    private PagerSlidingTabStrip mPagerTabStrip = null;
    private ViewPager mViewPager = null;
    private Toolbar mToolbar;

    public ContainerFragment() {
        super();
    }

    public static ContainerFragment instance() {
        if (mInst == null)
            mInst = new ContainerFragment();
        return mInst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAddtionalComponents();
    }

    public void loadAddtionalComponents() {
        mViewPagerTabsTitles = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.screen_home_view_pager_tabs_titles)));
        mFragmentMenuTitles = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
        mViewPager = (ViewPager) getView().findViewById(R.id.screen_home_fragment_container_view_pager);
        mPagerTabStrip = (PagerSlidingTabStrip) getView().findViewById(R.id.screen_home_fragment_container_pager_sliding_tab);
        mToolbar = (Toolbar) getView().findViewById(R.id.screen_toolbar);
        mToolbar.setTitle(getToolbarTitle());
        mToolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

        //mToolbar.setScrollbarFadingEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("container");
                ft.replace(R.id.screen_home_fragment_container, NavigationFragment.instance());
                ft.commit();
            }
        });
        Log.d(TAG, "Fragments Titles Loaded.");
        for (String s : mViewPagerTabsTitles)
            Log.d(TAG, "tab# " + s);
        for (String s : mFragmentMenuTitles)
            Log.d(TAG, "frag# " + s);
        try {
            HomeViewPagerAdapter adpt = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager(), mViewPagerTabsTitles);
            mViewPager.setAdapter(adpt);
        } catch (NullPointerException npe) {
            Log.d(TAG, "NULL Point Exception." + npe.getMessage());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        //mPagerTabStrip.setEnabled(true);
        mPagerTabStrip.setViewPager(mViewPager);
        rcutil.showMessageAsToast(getActivity().getApplicationContext(), "AdditionalComponentesLoaded.");
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public int getLayoutID() {
        return R.layout.screen_home_fragment_container;
    }

    public String getName() {
        return "ContainerFragment";
    }
    public PagerSlidingTabStrip getSlidingTap()  {
        return mPagerTabStrip;
    }
    public ViewPager getmViewPager()  {
        return mViewPager;
    }
}
