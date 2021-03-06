package io.yulw.rcctrl.activity;

/**
 * Created by yulw on 2015/6/8.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rcframe;

public abstract class BaseActivity extends ActionBarActivity implements rcframe {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        loadToolbar();
        loadAdditionalComponents();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void loadToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar = (Toolbar) findViewById(R.id.screen_toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        mToolbar.setTitle(getToolbarTitle());
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public void onRcStart() {
    }

    public void onRcPause() {
    }

    public void onRcResume() {
    }

    public void onRcStop() {
    }
}
