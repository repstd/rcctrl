package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/11.
 */
public class rcworker<T extends rctasks> extends Thread
{
    private T mTaskImp;
    private volatile Thread mBlinker;
    private Thread mCurrent;
    public rcworker(T taskImpl) {
        mTaskImp = taskImpl;
        mBlinker=new Thread();
        mCurrent=null;
    }

    @Override
    public void run() {
        while(!getImpl().isFinished()&&!isStop()) {
            getImpl().execute();
        }
    }

    public T getImpl() {
        return mTaskImp;
    }
    public void rcSafeStop() {
        mBlinker=null;
    }
    @Override
    public void start() {
        mBlinker.start();
        mCurrent=Thread.currentThread();
        super.start();
    }
    private boolean isStop() {
        if(mBlinker!=null)
            return false;
        else
            return true;
    }
}
