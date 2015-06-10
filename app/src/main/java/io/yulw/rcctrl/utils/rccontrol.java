package io.yulw.rcctrl.utils;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class rccontrol {
    private static rccontrol instance = null;
    private final String TAG = "rccontrol";
    private HashMap<String, Integer> m_runningApps;
    private DatagramSocket m_socket;
    private rcpara m_para;

    private rccontrol() {
        Log.d(TAG, "rccontrol instance init.");
    }

    public static rccontrol instance() {
        if (instance == null) {
            instance = new rccontrol();
        }
        return instance;
    }

    public rcpara getPara() {
        return m_para;
    }

    public synchronized boolean sendPacket(String msg) {
        Log.d(TAG, "Trying to send# " + msg);
        if (msg.equals(""))
            return false;
        try {
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), getPara().getHost(), getPara().getPort());
            Log.d(TAG, "Sending data." + getPara().getHost().toString() + ":" + getPara().getPort() + " Size: " + msg.length() + 1);
            m_socket.send(packet);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            Log.d(TAG, "SocketTImedoutException in Sending Message.Error: " + e.getMessage());
        } catch (java.net.SocketException e) {
            e.printStackTrace();
            Log.d(TAG, "SocketException in Sending Message.Error: " + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "IOError in Sending Message.Error: " + e.getMessage());
        }
        return true;
    }

    public synchronized String getPacket() {
        DatagramPacket packet = new DatagramPacket(new byte[100], 100);
        try {
            m_socket.receive(packet);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "Error in Getting Message.Error: " + e.getMessage());
            return "Error";
        }
        //return "Test";
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void reset(rcpara para) {
        close();
        m_para = para;
        //reset the sockets
        open();
    }

    public synchronized void open() {
        Log.d(TAG, "open");
        try {
            //m_socket=new DatagramSocket(getPara().getPort(),getPara().getHost());
            m_socket = new DatagramSocket(getPara().getPort());
            m_socket.setBroadcast(true);
            m_socket.setReuseAddress(true);
            m_socket.setSoTimeout(0);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "Error in Open Socket.Error: " + e.getMessage());
        }
    }

    public boolean isClosed() {
        return m_socket.isClosed();
    }

    public synchronized void close() {
        Log.d(TAG, "close");
        try {
            if (m_socket != null && !m_socket.isClosed())
                m_socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.d(TAG, "Error in Closing The Socket.Error: " + e.getMessage());
        }
    }
}
