package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/10.
 */
public interface rcframe {
    int getLayoutID();

    String getToolbarTitle();

    String getName();

    void loadAdditionalComponents();

    void onRcStart();

    void onRcPause();

    void onRcResume();

    void onRcStop();

}
