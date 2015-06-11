package io.yulw.rcctrl.adapter;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

import io.yulw.rcctrl.R;

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
        return mShortcutsNames.get(position);
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
        View view = null;
        try{
            view=createItemView(position,convertView,parent);
            parseLayoutComponents(view, position);
        } catch (NullPointerException npe) {
            Log.d(TAG, "::getView#NullPointerException#" + npe.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "::getView#Exception#" + e.getMessage());
        } finally {
            return view;
        }
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

    private void parseLayoutComponents(View view, int position) {
        if (view == null)
            return;
        switch (position) {
            case 0:
                parseShortcutPreviewLayout(view);
                break;
            case 1:
                parseSystemSettingLayout(view);
            default:
                break;
        }
    }

    private void parseSystemSettingLayout(View view) {
    }

    private void parseShortcutPreviewLayout(View view) {
        try {
                ExpandableListView emv=(ExpandableListView)view.findViewById(R.id.fragment_shortcut_expand_view);
                emv.setAdapter(new ShortcutsFragmentsExpandableViewAdapter(mInflater));
        }
        catch(NullPointerException npe) {
            Log.d(TAG,"::parseShortcutPreviewLayout#NullPointerException#"+npe.getMessage());
        }
        catch(Exception e) {
            Log.d(TAG, "::parseShortcutPreviewLayout#Exception#" + e.getMessage());
        }
    }
    private View createItemView(int position, View convertView, ViewGroup parent) {
        return mInflater.inflate(R.layout.fragment_system_settings,parent,false);
    }
}
