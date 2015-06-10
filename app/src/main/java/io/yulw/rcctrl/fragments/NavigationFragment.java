package io.yulw.rcctrl.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.yulw.rcctrl.R;
/**
 * Created by yulw on 6/9/2015.
 */
public class NavigationFragment extends BaseFragment
{
    public NavigationFragment()
    {
        setName("NavigationFragment");
        initLayout();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(),container,false);
    }
    @Override
    protected void initLayout() {
        setLayoutId(R.layout.screen_home_navigation_fragment_menu);
    }
}
