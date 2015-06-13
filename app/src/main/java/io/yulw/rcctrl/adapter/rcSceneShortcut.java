package io.yulw.rcctrl.adapter;

import android.app.Activity;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 6/13/2015.
 */
public class rcSceneShortcut extends rcShortcut
{
    private Activity mActivity;
    public rcSceneShortcut(Activity activity) {
        mActivity=activity;
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_shortcut_detail_scene;
    }

    @Override
    public String getName() {
        return "rcSceneShortcut";
    }

    @Override
    public String getToolbarTitle() {
        return "RCSceneRendring";
    }

    @Override
    public void loadAddtionalComponents() {
        //TODO
        loadToolbar(mActivity,R.id.fragment_shortcut_detail_scene_screen_toolbar);
    }
}
