package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/11.
 */
public class rcworker<T extends  rctasks> extends Thread
{
    private T mTaskImp;
    public rcworker(T taskImpl) {
        mTaskImp=taskImpl;
    }
    @Override
    public void run()  {
        getImpl().execute();
    }
    public T getImpl() {
        return mTaskImp;
    }
}
