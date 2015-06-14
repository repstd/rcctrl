package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.yulw.rcctrl.R;

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
    public void loadAddtionalComponents() {
        mLogTextView = (TextView) mActivity.findViewById(R.id.fragment_shortcut_detail_log_textView);
        Log.d(TAG, "::loadAdditionalComponents");
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
        mToolbar.setTitle(getToolbarTitle());
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
}
