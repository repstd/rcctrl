package io.yulw.rcctrl;

import android.view.*;
import android.widget.TextView;
import java.util.*;
import java.net.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.*;

class rcreceiver extends Thread {
	private final String TAG = "rcreceiver.";
	rcserver m_cxt = null;
	DatagramSocket m_svr;

	rcreceiver(rcserver context) {
		m_cxt = context;
		try {
			m_svr = new DatagramSocket(8000);
			m_svr.setSoTimeout(0);
			m_svr.setReuseAddress(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (true)
		{
			byte[] buf = new byte[100];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				if (m_cxt != null)
					m_cxt.appendBuffer(new String("waiting..."));
				else
					m_cxt.appendBuffer(new String("rcserver is null"));
				 //m_svr.receive(packet);
				 //m_cxt.appendBuffer(packet.getAddress().toString()+"#"+packet.getData().toString());
				 String newbuf=rccontrol.instance().getPacket();
				 m_cxt.appendBuffer(newbuf);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "Error in write to logBuffer.Error: " + e.getMessage());
			}
		}
	}
}

public class rcserver {
	int m_thCapacity;
	int m_msgCapacity;
	Handler m_handler = null;
	HashMap<Integer, rcreceiver> m_workers;
	Queue<String> m_msg;
	private final String TAG = "rcserver";

	@SuppressLint("UseSparseArrays")
	rcserver(int threadCapacity, int msgCapacity) {
		m_thCapacity = threadCapacity;
		m_msgCapacity = msgCapacity;
		m_workers = new HashMap<Integer, rcreceiver>();
		m_msg = new LinkedList<String>();
	}

	@SuppressLint("UseSparseArrays")
	rcserver(Handler handler, int threadCapacity, int msgCapacity) {
		m_thCapacity = threadCapacity;
		m_msgCapacity = msgCapacity;
		m_workers = new HashMap<Integer, rcreceiver>();
		m_msg = new LinkedList<String>();
		m_handler = handler;
	}

	public int getThreadCapacity() {
		return m_thCapacity;
	}

	public int getMessageCapacity() {
		return m_msgCapacity;
	}

	private void create() {
		for (int id = 0; id < m_thCapacity; id++) {
			m_workers.put(id, new rcreceiver(this));
		}
	}

	public void start() {
		create();
		for (rcreceiver worker : m_workers.values()) {
			try {
				Log.d(TAG, "thread " + worker.getId() + " started");
				worker.start();
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "Error in starting thread " + worker.getId() + " "
						+ e.getMessage());
			}
		}
	}

	public void stop() {
		for (rcreceiver worker : m_workers.values()) {
			try {
				worker.stop();
				Log.d(TAG, "thread " + worker.getId() + " stoped.");
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "Error in stoping thread " + worker.getId() + " "
						+ e.getMessage());
			}
		}
	}

	public void suspend() {
		for (rcreceiver worker : m_workers.values()) {
			try {
				worker.suspend();
				Log.d(TAG, "thread " + worker.getId() + " stoped.");
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
				Log.d(TAG, "UnsupportedOperationException in stoping thread "
						+ worker.getId() + " " + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "Exception in stoping thread " + worker.getId()
						+ " " + e.getMessage());
			}
		}
	}

	synchronized void appendBuffer(byte[] newbuf) {
		if (m_msg.size() >= getMessageCapacity())
			m_msg.remove();
		m_msg.add(newbuf.toString());
		if (m_handler != null)
			notifyHandler(newbuf.toString());
	}

	synchronized void appendBuffer(String newbuf) {
		if (m_msg.size() >= getMessageCapacity())
			m_msg.remove();
		m_msg.add(newbuf);
		if (m_handler != null)
			notifyHandler(newbuf);
	}

	public String getLogs() {
		String result = "";
		for (String s : m_msg) {
			result += s+"\n";
		}
		return result;
	}

	private void notifyHandler(String log) {
		Log.d(TAG, "Trying to send: " + log);
		Bundle data = new Bundle();
		data.putString("newLog", log);
		Message msg = new Message();
		msg.setData(data);
		try {
			m_handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG,
					"Exception in sending data to handler.Error: "
							+ e.getMessage());
		}
	}
}
