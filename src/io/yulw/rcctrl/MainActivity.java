package io.yulw.rcctrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import io.yulw.test.*;
import android.net.*;
import android.net.wifi.*;
import android.content.*;
import android.util.*;
public class MainActivity extends Activity 
{
	private final String TAG="MainActivity.";
	private WifiManager m_wifi=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			m_wifi= (WifiManager) this
					.getSystemService(Context.WIFI_SERVICE);
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG,"onStartIn");
		//test
        rcpara para=new rcpara(m_wifi,null,6000);
        testClient test=new testClient(para);
        test.start();
		Log.d(TAG,"onStartExit");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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
}
