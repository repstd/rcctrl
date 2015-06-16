package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 2015/6/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rcmanager;
import io.yulw.rcctrl.utils.rcutil;

public class SettingsFragment extends BaseFragment {
    private static SettingsFragment mInst = null;
    private final String TAG = "SettingsFragment";
    private EditText mEditTextAddr;
    private EditText mEditTextPort;
    private Button mButtonFinish;

    public SettingsFragment() {
        super();
        mEditTextAddr = null;
        mEditTextPort = null;
        mButtonFinish = null;

    }

    public static SettingsFragment instance() {
        if (mInst == null)
            mInst = new SettingsFragment();
        return mInst;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAdditionalComponents();
    }

    public int getLayoutID() {
        return R.layout.fragment_system_settings;
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public String getName() {
        return "SettingsFragments";
    }

    public void loadAdditionalComponents() {
        loadUIComponents();
        addAdaptersOrListeners();
    }

    private void loadUIComponents() {
        try{
            mEditTextAddr = (EditText) getView().findViewById(R.id.fragment_system_setting_editText_addr);
            mEditTextPort = (EditText) getView().findViewById(R.id.fragment_system_setting_editText_port);
            mButtonFinish = (Button) getView().findViewById(R.id.fragment_system_setting_button_finish);
        }
        catch (NullPointerException e) {
            Log.d(TAG,"::loadUIComponents#NullPointerException#"+e.getMessage());
        }
    }

    private void addAdaptersOrListeners() {
        try {
            mButtonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strAddr = mEditTextAddr.getText().toString();
                    String strPort = mEditTextPort.getText().toString();
                    if (!strAddr.equals(""))
                        rcmanager.instance().setHostName(strAddr);
                    if (!strPort.equals(""))
                        rcmanager.instance().setPort(Integer.parseInt(strPort));
                    rcmanager.instance().onWifiStateChanged();
                    rcutil.showMessageAsToast(getActivity().getApplicationContext(),"parameters updated.");
                }
            });
        } catch (NullPointerException npe) {
            Log.d(TAG, "::addAdapterOrListeners#NullPointerException#" + npe.getMessage());
        }
    }
}
