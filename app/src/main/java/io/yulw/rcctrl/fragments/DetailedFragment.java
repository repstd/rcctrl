package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import io.yulw.rcctrl.R;
public class DetailedFragment extends BaseFragment
{
    private int mLayoutId;
    private String TAG="DetailedFragment";
    public DetailedFragment() {
        super();
        initLayout();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(),container,false);
    }
    @Override
    protected void initLayout()  {
        Bundle args=getArguments();
        try {
            mLayoutId=args.getInt("LayoutId");
            Log.d(TAG,"::inntLayout#"+Integer.toString(mLayoutId));
            //TODO:
            //Add a adapter implementaion, so specific interaction couled be added to this fragment
        }
        catch(Exception e) {
            Log.d(TAG,"::inntLayout#"+e.getMessage());
        }
        setLayoutId(mLayoutId);
    }
}
