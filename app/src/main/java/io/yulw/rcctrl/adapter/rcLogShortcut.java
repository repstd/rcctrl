package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rcmanager;

/**
 * Created by yulw on 6/13/2015.
 */
public class rcLogShortcut extends rcShortcut {
    private final String TAG = "rcLogShortcut";
    private ImageView mLogImageView;
    private TextView mLogTextView;
    private Activity mActivity;
    private Handler mLogHandler ;
    private Toolbar mToolbar;
    private BroadcastReceiver mBroadcastReceiver;
    rcLogShortcut(Activity activity) {
        mActivity = activity;
        mLogHandler=new Handler( new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String newLog=msg.getData().getString("newLog");
                Log.d(TAG,"#"+newLog);
                return false;
            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_shortcut_detail_log;
    }

    @Override
    public String getToolbarTitle() {
        return "RCHost Log";
    }

    @Override
    public String getName() {
        return "rcLogShortcut";
    }

    @Override
    public void loadAdditionalComponents() {
        mLogTextView = (TextView) mActivity.findViewById(R.id.fragment_shortcut_detail_log_textView);
        mLogTextView.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        Log.d(TAG, "::rcLogShortCut::loadAdditionalComponents");
        try {

            loadToolbar(mActivity, R.id.fragment_shortcut_detail_log_screen_toolbar);
        } catch (NullPointerException e) {
            Log.d(TAG, "::loadAdditionalComponents#NullpointerException" + e.getMessage());

        } catch (Exception e) {
            Log.d(TAG, "::loadAdditionalComponents#Exception" + e.getMessage());
        }
    }
    @Override
    protected void loadToolbar(final Activity activity, int toolbarId) {
        final ActionBarActivity homeActivity = (ActionBarActivity) activity;
        mToolbar = (Toolbar) activity.findViewById(toolbarId);
        if (mToolbar != null) {
            homeActivity.setSupportActionBar(mToolbar);
            homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        try {
            mToolbar.setTitle(getToolbarTitle());
        }
        catch (NullPointerException e) {
            Log.d(TAG,"#loadToolbar#NullPointerException#"+e.getMessage()) ;
        }
        mToolbar.setTitleTextColor(activity.getResources().getColor(R.color.colorPrimary));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = homeActivity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fm.popBackStack();
                fm.executePendingTransactions();
            }
        });
    }

    private void addLogBroadcastReceiver() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("LogServer");
        mBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String log=intent.getExtras().getString("log");
                Log.d(TAG,"::BroadcastReceiver::onReceive#"+log);
                String text=mLogTextView.getText().toString();
                mLogTextView.setText(text+"\n"+log);
            }
        };
        rcmanager.instance().addLogServer(mActivity, 1, 100);
        LocalBroadcastManager.getInstance(mActivity.getApplicationContext()).registerReceiver(mBroadcastReceiver, intentFilter);
    }
    private void removeLogBroadcastReceiver() {
        LocalBroadcastManager.getInstance(mActivity.getApplicationContext()) .unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onRcStart() {

    }
    @Override
    public void onRcPause() {
        removeLogBroadcastReceiver();
    }
    @Override
    public void onRcResume() {
        addLogBroadcastReceiver();
        updateLogText();
    }
    @Override
    public void onRcStop() {

    }
    private void updateLogText() {
        mLogTextView.setText(mLogTextView.getText().toString()+"\n"+rcmanager.instance().getLog());
    }
}
