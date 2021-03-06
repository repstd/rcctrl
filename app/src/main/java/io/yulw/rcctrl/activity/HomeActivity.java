package io.yulw.rcctrl.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rcmanager;
import io.yulw.rcctrl.utils.rcutil;

/**
 * Created by yulw on 6/8/2015.
 */
public class HomeActivity extends BaseActivity {
    private final String TAG = "HomeActivity";
    IntentFilter mIntentFilter;
    BroadcastReceiver mBroadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @Override
    public int getLayoutID() {
        return R.layout.screen_home;
    }

    @Override
    public String getName() {
        return "HomeActivity";
    }

    @Override
    public void loadAdditionalComponents() {
        rcutil.showMessageAsToast(getApplicationContext(), "AdditionalComponentsLoaded.");
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    void removeWifiStateFilter() {
        unregisterReceiver(mBroadReceiver);
    }

    void addWifiStateChangedFilter() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mBroadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "::addWifiStateChangedFilter::receiver::OnReceived");
                if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    try {
                        WifiManager wifi = (WifiManager) io.yulw.rcctrl.activity.HomeActivity.this.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo info = wifi.getConnectionInfo();
                        //WifiInfo info=intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                        if (info != null) {
                            int connected = info.getNetworkId();
                            if (connected != -1) {
                                rcutil.showMessageAsToast(getApplicationContext(), "WifiConnected.Now Reset the socket client.");
                                rcmanager.instance().onWifiStateChanged(wifi);
                            } else {
                                rcutil.showMessageAsToast(getApplicationContext(), "WifiDisconnected.");
                            }
                        }
                    } catch (IllegalStateException ile) {
                        String msg = "::addWifiStateChangedFilter::receiver::OnReceive.IllegalStateException: " + ile.getMessage();
                        Log.d(TAG, msg);
                        rcutil.showMessageAsToast(getApplicationContext(), msg);
                    } catch (Exception e) {
                        String msg = "::addWifiStateChangedFilter::receiver::OnReceive.Exception:" + e.getMessage();
                        Log.d(TAG, msg);
                    }
                }
            }
        };
        registerReceiver(mBroadReceiver, mIntentFilter);
    }
}
