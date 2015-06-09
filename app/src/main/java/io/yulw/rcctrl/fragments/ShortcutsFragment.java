package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import android.util.Log ;
import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.ShortcutsFragmentAdapter;

public class ShortcutsFragment extends BaseFragment {
    private static ShortcutsFragment mInst = null;
    private ListView mShortcutsListView;
    private View mDrawer;
    ArrayList<String> mShortcutsTitle;
    private final String TAG="ShortcutFragment";
    public static ShortcutsFragment instance() {
        if (mInst == null)
            mInst = new ShortcutsFragment();
        return mInst;
    }

    public ShortcutsFragment() {
        super();
        setName("ShortcutsFragment");
        initLayout();
    }

    @Override
    protected void initLayout() {
        setLayoutId(R.layout.fragment_shortcuts);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawer=inflater.inflate(getLayoutID(),container,false);
        return mDrawer;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mShortcutsListView = (ListView) view.findViewById(R.id.fragment_shortcut_listView);
            mShortcutsTitle=new ArrayList<String>(Arrays.asList(view.getResources().getStringArray(R.array.fragment_shortcuts_titles)));
            ShortcutsFragmentAdapter adapter=new ShortcutsFragmentAdapter(mShortcutsTitle) ;
            adapter.setInflater(getLayoutInflater(savedInstanceState));
            mShortcutsListView.setAdapter(adapter);
        }
        catch (NullPointerException e)  {
            Log.d(TAG,"::initLayout#"+e.getMessage());
        }
    }
}
