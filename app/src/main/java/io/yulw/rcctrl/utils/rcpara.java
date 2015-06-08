package io.yulw.rcctrl.utils;
import java.util.*;
import java.net.*;

import android.net.*;
import android.net.wifi.*;
import android.content.*;
import android.util.Log;
//@yulw, Parameters for the client
public class rcpara 
{
	private String m_hostname;
	private int m_port;
	private InetAddress m_broadcastAddr;
	private WifiManager m_wifi;
	private final String TAG="rcpara";
	public rcpara(WifiManager wifi,String hostname,int port) {
		m_wifi=wifi;
		m_hostname=hostname;
		m_port=port;
		Log.d(TAG,"rcpara init start.");
		try {
			if(hostname==null)
				m_broadcastAddr = getBroadcastAddr(m_wifi);
			else
				m_broadcastAddr=InetAddress.getByName(m_hostname);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG,"Error in calculate broadcast address.Error: "+e.getMessage());
		}
		Log.d(TAG,"rcpara init end.BroadCasteAddr:"+m_broadcastAddr.toString());
	}
	public WifiManager getWiifiManager()
	{
		return m_wifi;
	}
	public int getPort()
	{
		return m_port;
	}
	public InetAddress getHost()
	{
		return m_broadcastAddr;
	}
	InetAddress getBroadcastAddr(WifiManager wifi) throws UnknownHostException
	{
		DhcpInfo dhcp = m_wifi.getDhcpInfo();
		if (dhcp == null) {
			Log.d(TAG, "Could not get dhcp info");
			return null;
		}
		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        Log.d(TAG, "DHCP NetMask:\t"+dhcp.toString());
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
		{
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		}
        Log.d(TAG, "Calculated BroadcastAddr Successfully.");
		return InetAddress.getByAddress(quads);
	}
}
