package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import io.yulw.rcctrl.R;
public class SettingsFragment extends BaseFragment
{
    private static SettingsFragment mInst=null;
    public static SettingsFragment instance()
    {
        if(mInst==null)
            mInst=new SettingsFragment();
        return mInst;
    }
    public SettingsFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(),container,false);
    }
    public int getLayoutID() {
        return R.layout.fragment_system_settings;
    }
    public String getToolbarTitle()  {
        return "RC CLIENT" ;
    }
    public String getName() {
        return "SettingsFragments";
    }
    public void loadAddtionalComponents() {
        return;
    }
}
