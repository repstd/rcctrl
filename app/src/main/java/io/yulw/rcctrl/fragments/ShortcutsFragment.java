package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.ShortcutsFragmentAdapter;

public class ShortcutsFragment extends BaseFragment {
    private static ShortcutsFragment mInst = null;
    private final String TAG = "ShortcutFragment";
    ArrayList<String> mShortcutsTitle;
    private ListView mShortcutsListView;
    private View mDrawer;

    public ShortcutsFragment() {
        super();
    }

    public static ShortcutsFragment instance() {
        if (mInst == null)
            mInst = new ShortcutsFragment();
        return mInst;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawer = inflater.inflate(getLayoutID(), container, false);
        return mDrawer;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mShortcutsListView = (ListView) view.findViewById(R.id.fragment_shortcut_listView);
            mShortcutsTitle = new ArrayList<String>(Arrays.asList(view.getResources().getStringArray(R.array.fragment_shortcuts_titles)));
            ShortcutsFragmentAdapter adapter = new ShortcutsFragmentAdapter(mShortcutsTitle);
            adapter.setInflater(getLayoutInflater(savedInstanceState));
            mShortcutsListView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.d(TAG, "::initLayout#" + e.getMessage());
        }
    }

    public int getLayoutID() {
        return R.layout.fragment_shortcuts;
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public String getName() {
        return "ShortcutsFragments";
    }

    public void loadAddtionalComponents() {
        return;
    }
}
