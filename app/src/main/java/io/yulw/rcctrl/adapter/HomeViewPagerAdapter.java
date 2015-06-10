package io.yulw.rcctrl.adapter;

/**
 * Created by yulw on 6/9/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import io.yulw.rcctrl.fragments.DefaultFragment;
import io.yulw.rcctrl.fragments.SettingsFragment;
import io.yulw.rcctrl.fragments.ShortcutsFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private final String TAG = "HomeViewPagerAdapter";
    ArrayList<String> mPagerTitles;

    public HomeViewPagerAdapter(FragmentManager fm, ArrayList<String> pagerTitls) {
        super(fm);
        mPagerTitles = pagerTitls;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "::getPageTitle#" + Integer.toString(position));
        if (position >= 0 && position < getCount())
            return mPagerTitles.get(position);
        else
            return "";
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "::getItem#" + Integer.toString(position));
        switch (position) {
            case 0:
                return DefaultFragment.instance();
            case 1:
                return SettingsFragment.instance();
            case 2:
                return ShortcutsFragment.instance();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mPagerTitles.size();
    }
}
