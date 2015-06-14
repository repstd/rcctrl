package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/10.
 */

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

public class rcmanager {
    private static rcmanager mInst = null;
    private Handler mHomeActivityHandler;
    private String mLastHostName;
    private int mLastPort;
    private rcpara mCurPara;
    private WifiManager mWifi;
    private rcserver mSvr=null;
    private final String TAG="rcmanager";
    rcmanager()
    {
        //@yulw,default paremeters
        mLastPort = 20714;
        mLastHostName = "10.108.61.29";
        //mLastHostName = "10.108.59.160";
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
    }
    synchronized  public void addLogServer() {
        Log.d(TAG,"Start");
        mSvr=new rcserver(10,100);
        mSvr.start();
    }
    synchronized  public void removeServer() {
        if(mSvr!=null) {
            mSvr.stop();
            mSvr=null;
        }
        Log.d(TAG,"All Stoped.");
    }
}
