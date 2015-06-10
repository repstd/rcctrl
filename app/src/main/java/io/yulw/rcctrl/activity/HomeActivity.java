package io.yulw.rcctrl.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Handler;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.net.NetworkInfo;
import io.yulw.rcctrl.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.astuetz.PagerSlidingTabStrip;
import io.yulw.rcctrl.adapter.HomeViewPagerAdapter;
import android.util.Log;
import io.yulw.rcctrl.utils.rcmanager;
import io.yulw.rcctrl.utils.rcutil;
/**
 * Created by yulw on 6/8/2015.
 */
public class HomeActivity extends BaseActivity
{
    private ArrayList<String> mViewPagerTabsTitles;
    private ArrayList<String> mFragmentMenuTitles;
    private PagerSlidingTabStrip mPagerTabStrip;
    private ViewPager mViewPager;
    IntentFilter mIntentFilter;
    BroadcastReceiver mBroadReceiver;
    private final String TAG="HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addWifiStateChangedFilter();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeWifiStateFilter();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }
    protected  int getLayoutID() {
       return R.layout.screen_home;
    }

    @Override
    protected void loadAddtionalComponents()
    {
        mViewPagerTabsTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_pager_tabs_titles)));
        mFragmentMenuTitles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
        mViewPager = (ViewPager) findViewById(R.id.screen_home_view_pager);
        mPagerTabStrip=(PagerSlidingTabStrip)findViewById(R.id.screen_home_pager_sliding_tab);
        Log.d(TAG,"Fragments Titles Loaded.");
        for(String s: mViewPagerTabsTitles)
            Log.d(TAG,"tab# "+s);
        for(String s: mFragmentMenuTitles)
            Log.d(TAG,"frag# "+s);
        try {
            HomeViewPagerAdapter adpt=new HomeViewPagerAdapter(getSupportFragmentManager(),mViewPagerTabsTitles);
            mViewPager.setAdapter(adpt);
        }
        catch (NullPointerException npe){
            Log.d(TAG,"NULL Point Exception."+npe.getMessage());
        }
        catch(Exception e) {
            Log.d(TAG,e.toString());
        }
        //mPagerTabStrip.setEnabled(true);
        mPagerTabStrip.setViewPager(mViewPager);
        rcutil.showMessageAsToast(getApplicationContext(), "AdditionalComponentesLoaded.");
    }
    protected String getToolbarTitle() {
        return "RC CLIENT";
    }
    void removeWifiStateFilter() {
        unregisterReceiver(mBroadReceiver);
    }
    void addWifiStateChangedFilter()
    {
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mBroadReceiver=new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.d(TAG,"::addWifiStateChangedFilter::receiver::OnReceived");
                if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    try {
                        WifiManager wifi=(WifiManager)io.yulw.rcctrl.activity.HomeActivity.this.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo info=wifi.getConnectionInfo();
                        //WifiInfo info=intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                        if(info!=null) {
                            int connected=info.getNetworkId();
                            if(connected!=-1) {
                                rcutil.showMessageAsToast(getApplicationContext(),"WifiConnected.Now Reset the socket client.");
                                rcmanager.instance().onWifiStateChanged(wifi);
                            }
                            else {
                                rcutil.showMessageAsToast(getApplicationContext(),"WifiDisconnected.");
                            }
                        }
                    }
                    catch(IllegalStateException ile) {
                        String msg=new String("::addWifiStateChangedFilter::receiver::OnReceive.IllegalStateException: "+ile.getMessage());
                        Log.d(TAG,msg);
                        rcutil.showMessageAsToast(getApplicationContext(),msg);
                    }
                    catch(Exception e) {
                        String msg=new String("::addWifiStateChangedFilter::receiver::OnReceive.Exception:"+e.getMessage());
                        Log.d(TAG,msg);
                    }
                }
            }
        };
        registerReceiver(mBroadReceiver,mIntentFilter);
    }
}
