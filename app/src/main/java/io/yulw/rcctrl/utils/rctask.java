package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/11.
 */
public interface rctask {
    void execute();

    Object getTask();

    boolean isFinished();
}
