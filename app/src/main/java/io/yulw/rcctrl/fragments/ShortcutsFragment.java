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
import android.widget.ExpandableListView;
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
    private ExpandableListView mShortcutExpandMenuView;

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
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "::onViewCreated");
        loadAddtionalComponents();
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
        try {
            loadUIComponents();
            addAdaptersOrListeners();
        } catch (NullPointerException e) {
            Log.d(TAG, "::initLayout#" + e.getMessage());
        }
    }

    private void loadUIComponents() throws NullPointerException {
        mShortcutsListView = (ListView) getView().findViewById(R.id.fragment_shortcut_listView);
    }

    private void addAdaptersOrListeners() throws NullPointerException {
        mShortcutsTitle = new ArrayList<String>(Arrays.asList(getView().getResources().getStringArray(R.array.fragment_shortcuts_titles)));
        ShortcutsFragmentAdapter adapter = new ShortcutsFragmentAdapter(mShortcutsTitle);
        adapter.setInflater(getActivity().getLayoutInflater());
        mShortcutsListView.offsetTopAndBottom(5);
        mShortcutsListView.setAdapter(adapter);

//        DetailedFragment previewFrag = (DetailedFragment) mShortcutsListView.getAdapter().getItem(1);
//        if(previewFrag==null) {
//            Log.d(TAG, "Can't find DetailedFrag#PreviewListView.");
//            return;
//        }
//        mShortcutExpandMenuView = (ExpandableListView) previewFrag.getView().findViewById(R.id.fragment_shortcut_expand_menu_view);
//        if (mShortcutExpandMenuView == null)
//            Log.d(TAG, "ExpandListViewNotFound.");
//        else {
//            Log.d(TAG, "ExpandListViewFound.");
//            //mShortcutExpandMenuView.setBackground((Drawable) getActivity().getResources().getDrawable(R.drawable.background));
//            mShortcutExpandMenuView.setAdapter(new ShortcutsFragmentsExpandableViewAdapter(getActivity().getLayoutInflater()));
//        }
    }
}
