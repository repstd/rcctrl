package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 2015/6/11.
 */

enum SHORTCUTS  {
    SHORTCUT_VIDEO,SHORTCUT_SCENE,SHORTCUT_SYS,SHORTCUT_LOG
}
public class ShortcutFragmentRecyclerViewAdapter extends RecyclerView.Adapter<ShortcutFragmentRecyclerViewAdapter.PreviewItemViewHolder> {
    private Activity mActivity;
    private String[] mItemDataSet;
    public ShortcutFragmentRecyclerViewAdapter(Activity activity, String[] dataSet){
        mItemDataSet=dataSet;
        this.mActivity = activity;
    }
    @Override
    public PreviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View shortcutItemLayout=mActivity.getLayoutInflater().inflate(R.layout.fragment_shortcut_preview,parent,false);
        return new PreviewItemViewHolder(mActivity,shortcutItemLayout);
    }

    @Override
    public void onBindViewHolder(final PreviewItemViewHolder holder, int position) {
        shortcutPreviewItem data=new shortcutPreviewItem(mItemDataSet[position]);
        holder.onBind(data);
    }

    @Override
    public int getItemCount() {
        return mItemDataSet.length;
    }
    public static class PreviewItemViewHolder extends RecyclerView.ViewHolder
    {
        private CardView mCardView;
        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mDescTextView;
        private final String TAG="PreviewItemViewHolder";
        public PreviewItemViewHolder(final Activity activity , View itemView)
        {
            super(itemView);
            try
            {
                mCardView=(CardView)itemView.findViewById(R.id.fragment_shortcut_preview_cardView);
                mCardView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm=((ActionBarActivity)activity).getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.addToBackStack("back");
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.replace(R.id.screen_drawer,detailedFragmentManager.instance().createFragment(activity,getPosition()));
                        ft.commit();
                    }
                } );
                mImageView=(ImageView)itemView.findViewById(R.id.fragment_shortcut_preview_image);
                mTitleTextView=(TextView)itemView.findViewById(R.id.fragment_shortcut_preview_title);
                mDescTextView=(TextView)itemView.findViewById(R.id.fragment_shortcut_preview_description);
            }
            catch(NullPointerException npe) {
                Log.d(TAG,"::PreviewItemViewHolder#NullPointerException#"+ npe.getMessage());
            }
        }
        public CardView getCardView() {
            return mCardView;
        }
        public ImageView getItemImageView() {
            return mImageView;
        }
        public TextView getItemTitleTextView() {
            return mTitleTextView;
        }
        public TextView getItemDescTextView() {
            return mDescTextView;
        }
        public void onBind(shortcutPreviewItem itemData) {
            try {
                getItemImageView().setImageDrawable(getCardView().getResources().getDrawable(itemData.getImageId()));
                getItemTitleTextView().setText(itemData.getTitle());
                getItemTitleTextView().setTextColor(getCardView().getResources().getColor(R.color.colorPrimary));
                getItemDescTextView().setText(itemData.getDesc());
                getItemDescTextView().setTextColor(getCardView().getResources().getColor(R.color.colorPrimary));
            } catch (NullPointerException npe) {
                Log.d(TAG,"::onBind#NullPointerException#"+npe.getMessage());
            } catch(Exception e) {
                Log.d(TAG,"::onBind#Exception#"+e.getMessage());
            }
        }
    }
}

class shortcutPreviewItem
{
    private int mDrawableId;
    private String mItemTitle;
    private String mItemDesc;
    private final String TAG="shortcutPreviewItem";
    public shortcutPreviewItem(int drawableId,String itemTitle,String itemDescription) {
        mDrawableId=drawableId;
        mItemTitle=itemTitle;
        mItemDesc=itemDescription;
    }
    public shortcutPreviewItem(String item) {
        mDrawableId=R.drawable.ic_person_black_48dp;
        String[] splitInfo=item.split("#");
        if(splitInfo.length==2) {
            mItemTitle=splitInfo[0];
            mItemDesc=splitInfo[1];
        } else {
            Log.d(TAG,"shortcutPreviewItem(String)#Input dataset is of wrong format.[title]#[desc] expected");
            mItemTitle="ShortcutItemTitle[Default]";
            mItemDesc="ShortcutItemDesc[Default]";
        }
    }
    public int getImageId() {
        return mDrawableId;
    }
    public String getTitle() {
        return mItemTitle;
    }
    public String getDesc() {
        return mItemDesc;
    }
}
