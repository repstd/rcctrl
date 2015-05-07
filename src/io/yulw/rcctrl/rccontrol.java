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
    private DatagramSocket m_socket;
    private int m_port;
    private final String TAG="rccontrol";
    String m_hostname="localhost";
    public int getPort() {
    	return m_port;
    }
    rccontrol(int port) {
    	m_port=port;
    }
    void send(String msg) {
    	DatagramPacket packet=new DatagramPacket(msg.getBytes(),msg.length(),m_host,m_port);
        packet.setLength(msg.length());
    }
    InetAddress getBroadcastAddress() throws IOException 
    {
    	WifiManager wifi = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if (dhcp == null) {
          Log.d(TAG, "Could not get dhcp info");
          return null;
        }
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
          quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);

    }

}
