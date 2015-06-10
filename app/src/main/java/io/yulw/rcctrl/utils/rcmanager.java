package io.yulw.rcctrl.utils;

/**
 * Created by yulw on 2015/6/10.
 */
import android.os.Handler;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
public class rcmanager
{
    private static rcmanager mInst=null;
    private Handler mHomeActivityHandler;
    private String mLastHostName;
    private int mLastPort;
    private rcpara mCurPara;
    private WifiManager mWifi;
    public static rcmanager instance()
    {
        if(mInst==null)
            mInst=new rcmanager();
        return mInst;
    }
    rcmanager()  {
        //@yulw,default paremeters
        mLastPort=20714;
        mLastHostName="10.108.61.29";
        mWifi=null;
        mCurPara=null;
    }
    void resetHandler(Handler handler) {
        mHomeActivityHandler=handler;
    }
    public void setHostName(String hostName) {
        mLastHostName=hostName;
    }
    public void  setPort(int port) {
        onWifiStateChanged(mWifi);
    }
    public int getPort()
    {
        return mLastPort;
    }
    public String getHostName()
    {
       return mLastHostName;
    }
    public void setHost(String name,int port) {
        setHostName(name);
        setPort(port);
    }
    public void onWifiStateChanged() {
            onWifiStateChanged(mWifi);
    }
    public void onWifiStateChanged(WifiManager wifi)  {
        if(wifi==null)
            return;
        mWifi=wifi;
        mCurPara=new rcpara(wifi,mLastHostName,mLastPort);
        rccontrol.instance().reset(mCurPara);
    }
}
