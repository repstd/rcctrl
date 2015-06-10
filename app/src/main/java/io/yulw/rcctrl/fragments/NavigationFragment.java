package io.yulw.rcctrl.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.adapter.NavigationFragmentAdapter;
/**
 * Created by yulw on 6/9/2015.
 */
public class NavigationFragment extends BaseFragment
{
    ArrayList<String> mFragmentsList;
    private final String TAG="NavigationFragment";
    private LayoutInflater mInflater=null;
    public NavigationFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater=inflater;
        return inflater.inflate(getLayoutID(),container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAddtionalComponents();
    }

    public int getLayoutID() {
        return R.layout.screen_home_navigation_fragment_menu;
    }
    public String getToolbarTitle()  {
        return "RC CLIENT" ;
    }
    public String getName() {
        return "NavigationFragment";
    }
    public void loadAddtionalComponents()
    {
        try {
            ListView menuList=(ListView)getView().findViewById(R.id.screen_home_fragment_menu_listView);
            mFragmentsList=new ArrayList<String>(Arrays.asList(menuList.getResources().getStringArray(R.array.screen_home_view_fragment_menu_titles)));
            menuList.setAdapter(new NavigationFragmentAdapter(mFragmentsList,mInflater));
        }
        catch(NullPointerException npe) {
            Log.d(TAG,"#loadAddtionalComponents#NullPointerException#"+ npe.getMessage());
        }
        return;
    }
}
