package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 2015/6/11.
 */
public class ShortcutFragmentRecyclerViewAdapter extends RecyclerView.Adapter<ShortcutFragmentRecyclerViewAdapter.ViewHolder> {
    private String[] mDataset;
    private Activity mActivity;

    public ShortcutFragmentRecyclerViewAdapter(Activity activity, String[] dataset){
        this.mDataset=dataset;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TextView textView=new TextView(mActivity);
        return new ViewHolder(mActivity,textView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        private Activity mActivity;
        public ViewHolder(Activity activity , View itemView) {
            super(itemView);
            mTextView=(TextView)itemView;
            mActivity = activity;
            mTextView.setTextSize(15);
            mTextView.setTextColor(mActivity.getLayoutInflater().getContext().getResources().getColor(R.color.accent_material_light));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mActivity.startActivity(new Intent(mActivity, DetailsActivity.class));
                }
            });
        }

    }
}

