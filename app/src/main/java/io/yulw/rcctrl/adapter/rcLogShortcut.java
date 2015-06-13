package io.yulw.rcctrl.adapter;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import io.yulw.rcctrl.R;

/**
 * Created by yulw on 6/13/2015.
 */
public class rcLogShortcut extends rcShortcut
{
    private ImageView mLogImageView;
    private TextView mLogTextView;
    private Activity mActivity;
    private final String TAG="rcLogShortcut";
    rcLogShortcut(Activity activity) {
        mActivity=activity;
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_shortcut_detail_log;
    }

    @Override
    public String getToolbarTitle() {
        return "RCHost Log";
    }

    @Override
    public String getName() {
        return "rcLogShortcut";
    }

    @Override
    public void loadAddtionalComponents() {
        mLogTextView=(TextView)mActivity.findViewById(R.id.fragment_shortcut_detail_log_textView);
        Log.d(TAG,"::loadAdditionalComponents");
        try {

            loadToolbar(mActivity,R.id.fragment_shortcut_detail_log_screen_toolbar);
        } catch(NullPointerException e)  {
            Log.d(TAG,"::loadAdditionalComponents#NullpointerException"+e.getMessage());

        } catch(Exception e) {
            Log.d(TAG,"::loadAdditionalComponents#Exception"+e.getMessage());
        }
    }
}
