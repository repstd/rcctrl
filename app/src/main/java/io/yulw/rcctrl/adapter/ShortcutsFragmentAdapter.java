package io.yulw.rcctrl.adapter;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.fragments.DetailedFragment;

/**
 * Created by yulw on 2015/6/9.
 */
public class ShortcutsFragmentAdapter extends BaseAdapter {
    private final String TAG = "ShortcutsAdapter";
    ArrayList<String> mShortcutsNames;
    private LayoutInflater mInflater = null;

    public ShortcutsFragmentAdapter(ArrayList<String> shortcutsNames) {
        super();
        mShortcutsNames = shortcutsNames;
        Log.d(TAG, "::Construct.");
    }

    public void setInflater(LayoutInflater flater) {
        mInflater = flater;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mShortcutsNames.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "::getItem");
        DetailedFragment frag = new DetailedFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putInt("LayoutId", R.layout.fragment_shortcur_graphics);
                frag.setArguments(args);
                return frag;
            case 1:
                args.putInt("LayoutId", R.layout.fragment_shortcut_video);
                frag.setArguments(args);
                return frag;
            default:
                break;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "::getView#" + Integer.toString(position));
        try {
            switch (position) {
                case 0:
                    return mInflater.inflate(R.layout.fragment_system_settings, parent, false);
                case 1:
                    return mInflater.inflate(R.layout.fragment_system_settings, parent, false);
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, "::getView#Error#" + e.getMessage());
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "::getViewTypeCount");
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
