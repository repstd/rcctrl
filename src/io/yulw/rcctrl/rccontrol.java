package io.yulw.rcctrl;
import java.util.*;
import java.net.*;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.content.*;
import android.service.*;
import java.io.IOException;
public class rccontrol 
{
	private HashMap<String, Integer> m_runningApps;
    private InetAddress m_host;
    private int m_port;
    private DatagramSocket m_socket;
    private final String TAG="rccontrol";
    private rcpara m_para;
    public rccontrol(rcpara para) {
    	m_para=para;
    	m_host=para.getHost();
    	m_port=para.getPort();
    	try {
			m_socket=new DatagramSocket(m_port);
			m_socket.setBroadcast(true);
			m_socket.setReuseAddress(true);
			m_socket.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public rcpara getPara() {
    	return m_para;
    }
    public boolean sendPacket(String msg)
    {
    	DatagramPacket packet=new DatagramPacket(msg.getBytes(),msg.length(),m_host,m_port);
    	try {
    		Log.d(TAG,"Sending data."+m_host.toString()+":"+m_port);
			m_socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG,"Error in Sending Message.Error: "+e.getMessage());
			return false;
		}
    	return true;
    }
    public String getPacket() 
    {
    	DatagramPacket packet=new DatagramPacket(new byte[100],100);
    	try {
			m_socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG,"Error in Getting Message.Error: "+e.getMessage());
			return null;
		}
    	return new String(packet.getData(),0,packet.getLength());
    }
}
