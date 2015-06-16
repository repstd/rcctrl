package io.yulw.rcctrl.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.NavigationFragmentAdapter;

/**
 * Created by yulw on 6/9/2015.
 */
public class NavigationFragment extends BaseFragment {
    private static NavigationFragment mInst = null;
    private final String TAG = "NavigationFragment";
    ArrayList<String> mFragmentsList;
    private LayoutInflater mInflater = null;

    public NavigationFragment() {
        super();
    }

    public static NavigationFragment instance() {
        if (mInst == null)
            mInst = new NavigationFragment();
        return mInst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAdditionalComponents();
    }

    public int getLayoutID() {
        return R.layout.screen_home_navigation_fragment_menu;
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public String getName() {
        return "NavigationFragment";
    }

    public void loadAdditionalComponents() {
        ListView menuList=null;
        try {
            View view=getView();
            menuList = (ListView) view.findViewById(R.id.screen_home_fragment_menu_listView);
        } catch (NullPointerException e) {
            Log.d(TAG, "#loadAdditionalComponents#NullPointerException#" + e.getMessage());
        }
        if(menuList==null)
            return;
        mFragmentsList = new ArrayList<String>(Arrays.asList(menuList.getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
        menuList.setAdapter(new NavigationFragmentAdapter(mFragmentsList, mInflater));
        return;
    }
}
