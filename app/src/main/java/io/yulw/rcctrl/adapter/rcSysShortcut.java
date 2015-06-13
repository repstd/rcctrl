package io.yulw.rcctrl.adapter;

import android.app.Activity;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 6/13/2015.
 */
public class rcSysShortcut extends rcShortcut {
    private Activity mActivity;

    public rcSysShortcut(Activity activity) {
       mActivity=activity;
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_shortcut_detail_sys;
    }

    @Override
    public String getName() {
        return "rcSysShortcut";
    }

    @Override
    public String getToolbarTitle() {
        return "RCHostSystem RemoteControlling";
    }

    @Override
    public void loadAddtionalComponents() {
        //TODO
        loadToolbar(mActivity,R.id.fragment_shortcut_detail_sys_screen_toolbar);
    }
}
