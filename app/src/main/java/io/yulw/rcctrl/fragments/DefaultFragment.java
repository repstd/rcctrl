package io.yulw.rcctrl.fragments;

/**
 * Created by yulw on 6/9/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.HashMap;

import io.yulw.rcctrl.R;
import io.yulw.rcctrl.utils.rccontrol;
import io.yulw.rcctrl.utils.rctask;
import io.yulw.rcctrl.utils.rcutil;
import io.yulw.rcctrl.utils.rcworker;

public class DefaultFragment extends BaseFragment {
    private static DefaultFragment mInst = null;
    private final String TAG = "DefaultFragment";
    private EditText mEditTextProgram;
    private Switch mSwitch;
    private Button mButtonSend;
    private String mCmd_name = "null";
    private String mCmd_op = "off";
    private HashMap<String, String> mMapRunningApps = new HashMap<String, String>();

    public DefaultFragment() {
        super();
    }

    public static DefaultFragment instance() {
        if (mInst == null)
            mInst = new DefaultFragment();
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
        return R.layout.fragment_default;
    }

    public String getToolbarTitle() {
        return "RC CLIENT";
    }

    public String getName() {
        return "DefaultFragment";
    }

    public void loadAdditionalComponents() {
        loadUIComponents();
        addAdaptersOrListeners();
    }

    private void loadUIComponents() {
        try {

            mEditTextProgram = (EditText) getView().findViewById(R.id.fragment_default_editText);
            mSwitch = (Switch) getView().findViewById(R.id.fragment_default_switch_on_off);
            mButtonSend = (Button) getView().findViewById(R.id.fragment_default_button_send);
        }
        catch (NullPointerException e) {
            Log.d(TAG,"::loadUIComponents#NullPointerException#"+e.getMessage());
        }
    }

    private void addAdaptersOrListeners() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    mCmd_op = "on";
                } else {
                    mCmd_op = "off";
                }
                Log.d(TAG, "Switch Changed to: " + mCmd_op);
            }
        });
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new rcworker<backgroundMessengerTask>(new backgroundMessengerTask(encodeMsg())).start();
            }
        });

    }

    private String encodeMsg() {
        mCmd_name = this.mEditTextProgram.getText().toString();
        rcutil.showMessageAsToast(getActivity().getApplicationContext(), "encodeMsg.current: " + mCmd_name + " " + mCmd_op);
        /*
         * @yulw,message to be sent to the rchost.eg. rcrender_on
		 */
        String msg;
        if (mMapRunningApps.get(mCmd_name) == null && mCmd_op == "on") {
            mMapRunningApps.put(mCmd_name, mCmd_op);
            msg = mCmd_name + "#" + mCmd_op;
            Log.d(TAG, "encodeMsg.current: " + msg);
        } else if (mMapRunningApps.get(mCmd_name) != null
                && mCmd_op == "off") {
            mMapRunningApps.remove(mCmd_name);
            msg = mCmd_name + "#" + mCmd_op;
            Log.d(TAG, "encodeMsg.current: " + msg);
        } else
            msg = "";
        return msg;
    }
}

class backgroundMessengerTask implements rctask {
    private String mMsg;
    private boolean mIsFinished;
    public backgroundMessengerTask(String msg) {
        mMsg = msg;
        mIsFinished=false;
    }

    public void execute() {
        rccontrol.instance().sendPacket(mMsg);
        mIsFinished=true;
    }

    public Object getTask() {
        return mMsg;
    }
    public boolean isFinished() {
        return mIsFinished;
    }
}
