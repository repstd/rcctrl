package io.yulw.rcctrl.adapter;

import android.app.Activity;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 6/13/2015.
 */
public class rcVideoShortcut extends  rcShortcut
{
    private Activity mActivity;
    public rcVideoShortcut(Activity activity) {
        super();
        mActivity=activity;
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_shortcut_detail_video;
    }

    @Override
    public String getToolbarTitle() {
        return "RCVideoPlaying";
    }

    @Override
    public String getName() {
        return "rcVideoShortcut";
    }

    @Override
    public void loadAddtionalComponents() {
        //TODO
        loadToolbar(mActivity,R.id.fragment_shortcut_detail_video_screen_toolbar);
    }
}
