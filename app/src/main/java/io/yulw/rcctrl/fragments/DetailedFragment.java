package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.yulw.rcctrl.utils.rcframe;

public class DetailedFragment<T extends rcframe> extends BaseFragment {
    private T mDetailedImpl;
    private String TAG = "DetailedFragment";

    public DetailedFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getImpl().loadAddtionalComponents();
    }

    public int getLayoutID() {
        return getImpl().getLayoutID();
    }

    public String getToolbarTitle() {
        return getImpl().getToolbarTitle();
    }

    public String getName() {
        return getImpl().getName();
    }

    public void loadAddtionalComponents() {
        getImpl().loadAddtionalComponents();
        ;
    }

    public T getImpl() {
        return mDetailedImpl;
    }

    public void setImpl(T impl) {
        mDetailedImpl = impl;
    }

}
