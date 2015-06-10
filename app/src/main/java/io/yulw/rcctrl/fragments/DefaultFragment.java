package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 6/9/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import io.yulw.rcctrl.R;
public class DefaultFragment extends  BaseFragment
{
    private static DefaultFragment mInst=null;
    private final String TAG="DefaultFragment";
    public static DefaultFragment instance()
    {
        if(mInst==null)
            mInst=new DefaultFragment();
        return mInst;
    }
    public DefaultFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(),container,false);
    }
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    public String getToolbarTitle()  {
        return "RC CLIENT" ;
    }
    public String getName() {
        return "DefaultFragment";
    }

   public void loadAddtionalComponents() {
        return;
   }
}
