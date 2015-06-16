package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.ShortcutFragmentRecyclerViewAdapter;

public class ShortcutsFragment extends BaseFragment {
    private static ShortcutsFragment mInst = null;
    private final String TAG = "ShortcutFragment";
    RecyclerView mShortcutRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        loadAdditionalComponents();
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

    public void loadAdditionalComponents() {
        try {
            loadUIComponents();
            addAdaptersOrListeners();
        } catch (NullPointerException e) {
            Log.d(TAG, "::initLayout#" + e.getMessage());
        }
    }

    private void loadUIComponents() throws NullPointerException {
        try {
            mShortcutRecyclerview = (RecyclerView) getView().findViewById(R.id.fragment_shortcuts_recycler_view);
        }
        catch (NullPointerException e) {
            Log.d(TAG,"::loadUIComponents#NullPointerException#"+e.getMessage());
        }
    }

    private void addAdaptersOrListeners() throws NullPointerException {
        mShortcutRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mShortcutRecyclerview.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        String[] titles = getActivity().getResources().getStringArray(R.array.fragment_shortcuts_titles);
        mAdapter = new ShortcutFragmentRecyclerViewAdapter(getActivity(), titles);
        mShortcutRecyclerview.setAdapter(mAdapter);
        mShortcutRecyclerview.setEnabled(true);
    }
}
