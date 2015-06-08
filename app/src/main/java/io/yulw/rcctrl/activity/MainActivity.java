package io.yulw.rcctrl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.net.wifi.*;
import android.content.*;
import android.content.DialogInterface.OnClickListener;
import android.util.*;
import android.app.AlertDialog.*;
import java.util.*;
import io.yulw.rcctrl.utils.rcpara;
import io.yulw.rcctrl.utils.rcserver;
import io.yulw.rcctrl.utils.rccontrol;
import io.yulw.rcctrl.utils.rcutil;
import io.yulw.rcctrl.R;
@SuppressLint("InflateParams")
public class MainActivity extends Activity {
	private final String TAG = "MainActivity.";
	private Switch m_switchOp;
	private Button m_buttonSend;
	private EditText m_editTextCmd;
	private final int RCBROADCAST_PORT = 20715;
	private int m_last_port = RCBROADCAST_PORT;
	private String m_last_host = "";
	private String m_cmd_name = "null";
	private String m_cmd_op = "off";
	private HashMap<String, String> m_mapRunningApps = new HashMap<String, String>();
	private WifiManager m_wifi = null;
	private rcpara m_para;
	rcserver m_logUpdatingSvr;
	Handler m_logUpdatingHandler = null;

	private void initViews() {
		m_switchOp = (Switch) this.findViewById(R.id.switch_on_off);
		m_switchOp.setChecked(false);
		m_buttonSend = (Button) this.findViewById(R.id.button_send);
		m_editTextCmd = (EditText) this.findViewById(R.id.editText1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		try {
			m_wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.d(TAG, "Error in quering WifiManager.Error: " + e.getMessage());
		}
		m_buttonSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String msg = encodeMsg();
				Log.d(TAG, "Onclick.Msg to be sent: " + msg);
				new worker(rccontrol.instance(), msg).start();
				rcutil.showMessageAsToast(getApplicationContext(), "Sending "
						+ msg + " to " + getLastHost().toString() + ":"
						+ getLastPort());
			}
		});
		m_switchOp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					m_cmd_op = "on";
				} else {
					m_cmd_op = "off";
				}
				Log.d(TAG, "Switch Changed to: " + m_cmd_op);
			}
		});
		// getLayoutInflater().inflate(R.layout.activity_main_logwindow, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStartIn");
		m_para = new rcpara(m_wifi, "", RCBROADCAST_PORT);
		rccontrol.instance().reset(m_para);
		m_logUpdatingHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				String newLog = msg.getData().getString("newLog");
				Log.d(TAG, "newLog: " + newLog);
				try {
					View viewLogs = (View) io.yulw.rcctrl.activity.MainActivity.this
							.getLayoutInflater().inflate(
									R.layout.activity_main_logwindow, null);
					TextView tv = (TextView) viewLogs
							.findViewById(R.id.logs_text);
					tv.setText(newLog);
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(TAG, e.getMessage());
				}
			};
		};
		try {
			m_logUpdatingSvr = new rcserver(m_logUpdatingHandler, 1, 100);
			m_logUpdatingSvr.start();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG,
					"Exception in starting rcserver.Error: " + e.getMessage());
		}
		// m_logUpdatingSvr.start();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(TAG, "onRestart.");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume.");
		if (rccontrol.instance().isClosed())
			rccontrol.instance().open();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop.");
		rccontrol.instance().close();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onDestroy.");
		rccontrol.instance().close();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			openSettingsInput();
			return true;
		} else if (id == R.id.action_log) {
			openLogWindow();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String encodeMsg() {
		Log.d(TAG, "eocodeMsg.current: " + m_cmd_name + " " + m_cmd_op);
		m_cmd_name = this.m_editTextCmd.getText().toString();
		/*
		 * @yulw,message to be sent to the rchost.eg. rcrender_on
		 */
		String msg;
		if (m_mapRunningApps.get(m_cmd_name) == null && m_cmd_op == "on") {
			m_mapRunningApps.put(m_cmd_name, m_cmd_op);
			msg = m_cmd_name + "#" + m_cmd_op;
		} else if (m_mapRunningApps.get(m_cmd_name) != null
				&& m_cmd_op == "off") {
			m_mapRunningApps.remove(m_cmd_name);
			msg = m_cmd_name + "#" + m_cmd_op;
		} else
			msg = "";
		return msg;
	}

	private void openSettingsInput() {
		Builder builder = new Builder(io.yulw.rcctrl.activity.MainActivity.this);
		final View viewSettings = (View) this.getLayoutInflater().inflate(
				R.layout.activity_main_settings, null);
		builder.setView(viewSettings);
		builder.setTitle("Set the client parameters");
		builder.setPositiveButton("Set", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try {
					EditText addrText = (EditText) viewSettings
							.findViewById(R.id.main_settings_editText_addr);
					EditText portText = (EditText) viewSettings
							.findViewById(R.id.main_settings_editText_port);
					String addr = addrText.getText().toString();
					int port = Integer.parseInt(portText.getText().toString());
					update(addr, port);
					m_para = new rcpara(m_wifi, getLastHost(), getLastPort());
					rccontrol.instance().reset(m_para);
				} catch (Exception e) {
					// TODO: handle exception
					Log.d(TAG,
							"Error in getting editText.Error: "
									+ e.getMessage());
				}
			}
		});
		builder.show();
		return;
	}

	private void openLogWindow() {
		Builder builder = new Builder(this);
		View viewLogs = (View) this.getLayoutInflater().inflate(
				R.layout.activity_main_logwindow, null);
		// View
		// viewLogs=(View)this.findViewById(R.layout.activity_main_logwindow);
		builder.setTitle("MessageFromHosts");
		((TextView) viewLogs.findViewById(R.id.logs_text))
				.append(m_logUpdatingSvr.getLogs());
		builder.setView(viewLogs);
		builder.setPositiveButton("Close", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();
		return;
	}

	private class worker extends Thread {
		private rccontrol m_c;
		private String m_msg;

		public worker(rccontrol ctrl, String msg) {
			m_c = ctrl;
			m_msg = msg;
		}

		public void run() {
			m_c.sendPacket(m_msg);
		}
	}

	private int getLastPort() {
		return m_last_port;
	}

	private void updateLastPort(int port) {
		m_last_port = port;
	}

	private String getLastHost() {
		return m_last_host;
	}

	private void updateLastHost(String h) {
		m_last_host = h;
	}

	private void update(String h, int port) {
		if (h != null)
			updateLastHost(h);
		updateLastPort(port);
	}
}
