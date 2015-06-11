package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 2015/6/11.
 */
public class ShortcutsFragmentsExpandableViewAdapter extends BaseExpandableListAdapter {
    Activity mCallingActivity;
    private LayoutInflater mInflater;

    public ShortcutsFragmentsExpandableViewAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    public ShortcutsFragmentsExpandableViewAdapter() {
        super();
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getTextView("child" + Integer.toString(groupPosition) + "#" + Integer.toString(childPosition));
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getTextView("group" + Integer.toString(groupPosition));
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return getTextView("groupView" + Integer.toString(groupPosition));
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return getTextView("childView" + Integer.toString(groupPosition) + "#" + Integer.toString(childPosition));
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public int getChildTypeCount() {
        return 1;
    }

    private View getTextView(String info) {
        TextView v = new TextView(mInflater.getContext());
        v.setText(info);
        v.setTextSize(15);
        v.setTextColor(mInflater.getContext().getResources().getColor(R.color.accent_material_light));
        return v;
    }
}
