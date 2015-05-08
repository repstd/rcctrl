package io.yulw.rcctrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.net.wifi.*;
import android.content.*;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import java.util.*;
public class MainActivity extends Activity 
{
	private final String TAG="MainActivity.";
	private Switch m_switchOp;
	private Button m_buttonSend;
	private EditText m_editTextCmd;
	private final int RCBROADCAST_PORT=20715;
	private String m_cmd_name="null";
	private String m_cmd_op="off";
	private HashMap<String,String> m_mapRunningApps=new HashMap<String,String>();

	private WifiManager m_wifi=null;
	private rcpara m_para;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_switchOp=(Switch)this.findViewById(R.id.switch_on_off);
		m_switchOp.setChecked(false);
		m_buttonSend=(Button)this.findViewById(R.id.button_send);
		m_editTextCmd=(EditText)this.findViewById(R.id.editText1);
		m_buttonSend.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String msg=encodeMsg();
				Log.d(TAG,"Onclick.Msg to be sent: "+msg);
				new worker(rccontrol.instance(),msg).start();
			}
		});
		m_switchOp.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) {
					m_cmd_op="on";
				}
				else {
					m_cmd_op="off";
				}
				Log.d(TAG,"Switch Changed to: "+m_cmd_op);
			}
		});
		try {
			m_wifi= (WifiManager) this .getSystemService(Context.WIFI_SERVICE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.d(TAG,"Error in quering WifiManager.Error: "+e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() 
	{
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG,"onStartIn");
		m_para=new rcpara(m_wifi,null,RCBROADCAST_PORT);
		rccontrol.instance().reset(m_para);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(TAG,"onRestart.");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG,"onResume.");
		if(rccontrol.instance().isClosed())
			rccontrol.instance().open();
	}
	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG,"onStop.");
		rccontrol.instance().close();
	}
	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG,"onDestroy.");
		rccontrol.instance().close();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public String encodeMsg()
	{
		Log.d(TAG,"eocodeMsg.current: "+m_cmd_name+" "+m_cmd_op);
		m_cmd_name=this.m_editTextCmd.getText().toString();
		/*@yulw,message to be sent to the rchost.
		 *eg.  rcrender_on
		 */
		String msg;

		if(m_mapRunningApps.get(m_cmd_name)==null&&m_cmd_op=="on") {
			m_mapRunningApps.put(m_cmd_name,m_cmd_op);
			msg=m_cmd_name+"#"+m_cmd_op;
		}
		else if(m_mapRunningApps.get(m_cmd_name)!=null&&m_cmd_op=="off"){
			m_mapRunningApps.remove(m_cmd_name);
			msg=m_cmd_name+"#"+m_cmd_op;
		}
		else
			msg="";
		return msg;
	}
	private class worker extends Thread
	{
		private rccontrol m_c;
		private String m_msg;
		public worker(rccontrol ctrl,String msg) {
			m_c=ctrl;
			m_msg=msg;
		}
		public void run() {
			m_c.sendPacket(m_msg);
		}
	}
}
