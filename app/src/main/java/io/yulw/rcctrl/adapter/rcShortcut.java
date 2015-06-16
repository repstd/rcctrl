package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rcframe;

/**
 * Created by yulw on 6/13/2015.
 */
public abstract class rcShortcut implements rcframe {
    private Toolbar mToolbar;
    private final String TAG="rcShortcut";
    //load a common toolbar and register an onClickListener
    protected void loadToolbar(final Activity activity, int toolbarId) {
        final ActionBarActivity homeActivity = (ActionBarActivity) activity;
        try {
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
        catch (NullPointerException e) {
                Log.d(TAG, "#loadToolbar#NullPointerException#" + e.getMessage()) ;
        }
    }
}
