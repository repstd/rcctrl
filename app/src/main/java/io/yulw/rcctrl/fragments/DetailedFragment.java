package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.yulw.rcctrl.R;

public class DetailedFragment extends BaseFragment {
    private int mLayoutId;
    private String TAG = "DetailedFragment";

    public DetailedFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    public int getLayoutID() {
        Bundle args=getArguments();
        if(args!=null) {
            if(args.get("LayoutId")!=null) {
                return args.getInt("LayoutId");
            }
        }
        return R.layout.fragment_system_settings;
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public String getName() {
        return "DetailedFragments";
    }

    public void loadAddtionalComponents() {
        return;
    }

}
