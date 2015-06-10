package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/10.
 */
public interface rcframe
{
    abstract  int getLayoutID();

    abstract String getToolbarTitle();

    abstract String getName();

    abstract  void loadAddtionalComponents();
}
