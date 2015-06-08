package io.yulw.rcctrl.adapter;

/**
 * Created by yulw on 6/9/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

import io.yulw.rcctrl.fragments.DefaultFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter
{
    ArrayList<String> mPagerTitles;
    public HomeViewPagerAdapter(FragmentManager fm,ArrayList<String> pagerTitls) {
        super(fm);
        mPagerTitles=pagerTitls;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position>=0&& position<getCount())
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
        switch(position)
        {
            case 0:
                return DefaultFragment.instance();
            default:
                return DefaultFragment.instance();
        }
    }
    @Override
    public int getCount() {
        return mPagerTitles.size();
    }
}
