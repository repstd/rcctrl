package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/10.
 */

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import java.util.Queue;

public class rcmanager {
    private static rcmanager mInst = null;
    private Handler mHomeActivityHandler;
    private String mLastHostName;
    private int mLastPort;
    private rcpara mCurPara;
    private WifiManager mWifi;
    private rcLogServer mSvr = null;
    private final String TAG="rcmanager";
    private final int mLogServerPort=20716;
    private boolean mIsLogServerRunning=false;
    private Activity mActivity;
    rcmanager()
    {
        //@yulw,default paremeters
        mLastPort = 20714;
        //mLastHostName = "10.108.61.29";
        mLastHostName = "10.108.59.160";
        mWifi = null;
        mCurPara = null;
    }

    public static rcmanager instance() {
        if (mInst == null)
            mInst = new rcmanager();
        return mInst;
    }

    void resetHandler(Handler handler) {
        mHomeActivityHandler = handler;
    }

    public int getPort() {
        return mLastPort;
    }

    public void setPort(int port) {
        mLastPort = port;
    }

    public String getHostName() {
        return mLastHostName;
    }

    public void setHostName(String hostName) {

        mLastHostName = hostName;
    }
    public rcpara getPara() {
        return mCurPara;
    }
    public void setHost(String name, int port) {
        setHostName(name);
        setPort(port);
    }

    public void onWifiStateChanged() {
        onWifiStateChanged(mWifi);
    }

    public void onWifiStateChanged(WifiManager wifi) {
        if (wifi == null)
            return;
        mWifi = wifi;
        mCurPara = new rcpara(wifi, mLastHostName, mLastPort);
        rccontrol.instance().reset(mCurPara);
        if(mIsLogServerRunning) {
            removeServer();
            addLogServer();
        }
    }
    synchronized  public void addLogServer() {
        Log.d(TAG,"Start");
        mSvr = new rcLogServer(mActivity, 10, 100);
        mSvr.start();
        mIsLogServerRunning=true;
    }
    synchronized  public void addLogServer(Activity activity,int threadCnt,int bufMsgCnt) {
        mActivity=activity;
        Log.d(TAG,"Start");
        mSvr = new rcLogServer(mActivity, threadCnt, bufMsgCnt);
        mSvr.start();
        mIsLogServerRunning=true;
    }
    synchronized  public void removeServer() {
        if(mSvr!=null) {
            mSvr.stop();
            mSvr=null;
        }
        mIsLogServerRunning=false;
    }
    public int getLogServerPort() {
        return mLogServerPort;
    }
    public String getLog() {
        return mSvr.getLogs();
    }

}
