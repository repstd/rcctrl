package io.yulw.rcctrl.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.view.LayoutInflater;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.fragments.NavigationFragment;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by yulw on 2015/6/10.
 */

public class NavigationFragmentAdapter extends BaseAdapter
{
    private ArrayList<String> mMenuTitles;
    private  LayoutInflater mInflater;
    private final String TAG="NavigationAdapter";
    public NavigationFragmentAdapter(ArrayList<String> titles,LayoutInflater inflater) {
        mMenuTitles=titles;
        mInflater=inflater;
    }
    @Override
    public int getCount() {
        return mMenuTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            TextView textView=new TextView(mInflater.getContext());
            textView.setText(mMenuTitles.get(position));
            textView.setTextSize(15);
            textView.setTextColor(mInflater.getContext().getResources().getColor(R.color.accent_material_light));
            return textView;
        }
        catch(NullPointerException npe) {
        }
        catch(Exception e) {
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
