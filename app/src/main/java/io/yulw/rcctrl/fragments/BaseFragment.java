package io.yulw.rcctrl.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public abstract  class BaseFragment extends Fragment {
    protected Context mContext;
    protected Activity mActivity;
    protected String mName;
    protected Integer mLayoutId;
    public BaseFragment() {
        super();
        mName="BaseFragment";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mContext = null;
    }
    protected void setName(String name) {
        mName=name;
    }
    public String getName() {
        return mName;
    }
    protected void setLayoutId(Integer id) {
        mLayoutId=id;
    }
    public int getLayoutID() {
        return mLayoutId;
    }
    protected abstract  void initLayout();
}
