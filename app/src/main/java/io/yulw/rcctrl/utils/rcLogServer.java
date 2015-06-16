package io.yulw.rcctrl.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class rcReceiverTask implements rctask
{
    private final String TAG="rcReceiverTask";
    private boolean mIsFinished;
    private Context m_context;
    private rcLogServer m_svr;
    private DatagramSocket m_socket;
    private int mTimeout;

    public rcReceiverTask(Context context, rcLogServer svr) {
        m_context=context;
        m_svr=svr;
        mIsFinished=false;
        mTimeout = 30;
        try  {
            m_socket=new DatagramSocket(rcmanager.instance().getLogServerPort());
            m_socket.setReuseAddress(true);
            m_socket.setBroadcast(false);
            m_socket.setSoTimeout(mTimeout);
        }
        catch (SocketException e) {
            m_socket=null;
        }
        m_context=m_svr.getContext();
    }
    @Override
    public void execute()  {
        try {
            //Log.d(TAG,"execting....");
            DatagramPacket packet=requestLog();
            respond(packet);
        }catch (NullPointerException e) {
            Log.d(TAG,"::execute#NullPointerException."+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG,"::execute#Exception."+e.getMessage());
            e.printStackTrace();
        }
    }
    public DatagramPacket requestLog() {
        DatagramPacket inPacket=null;
        try {
            String msg="androidClient#"+rcmanager.instance().getPara().getHost().getLocalHost().toString();
            DatagramPacket outPacket=new DatagramPacket(msg.getBytes(),msg.length(),rcmanager.instance().getPara().getHost(),rcmanager.instance().getLogServerPort());
            inPacket=new DatagramPacket(new byte[100],100);
            m_socket.send(outPacket);
            m_socket.receive(inPacket);
        }catch (SocketException e) {
            Log.d(TAG,"::requestLog#SocketException#"+e.getMessage()) ;
        }
        catch (UnknownHostException e) {
            Log.d(TAG,"::requestLog#UnknowmHostException#"+e.getMessage());
        }
        catch (IOException e) {
            Log.d(TAG,"::requestLog#IOException#"+e.getMessage()) ;
        }
        return inPacket;
    }
    private void respond(DatagramPacket packet)
    {
        if(packet==null||packet.getPort()==-1)
            return;
        String log=new String(packet.getData(),0,packet.getLength());
        Intent intent=new Intent();
        intent.setAction("LogServer");
        intent.putExtra("log", log);
        m_svr.appendBuffer(log);
        Log.d(TAG, "::execute#" + log);
        rcutil.showMessageAsToast(m_context, "newLog:#" + log);
        if(m_context!=null)
            LocalBroadcastManager.getInstance(m_context).sendBroadcast(intent);
    }
    @Override
    public Object getTask() {
        return null;
    }

    @Override
    public boolean isFinished() {
        return rccontrol.instance().isClosed();
    }
}

public class rcLogServer
{
    private final String TAG = "rcLogServer";
    int m_thCapacity;
    int m_msgCapacity;
    Handler m_handler = null;
    HashMap<Integer, rcworker<rcReceiverTask>> m_workers;
    Queue<String> m_msg;
    Activity m_Activity;
    @SuppressLint("UseSparseArrays")
    public rcLogServer(int threadCapacity, int msgCapacity) {
        m_thCapacity = threadCapacity;
        m_msgCapacity = msgCapacity;
        m_workers = new HashMap<Integer, rcworker<rcReceiverTask>>();
        m_msg = new LinkedList<String>();
    }

    public rcLogServer(Handler handler, int threadCapacity, int msgCapacity) {
        m_thCapacity = threadCapacity;
        m_msgCapacity = msgCapacity;
        m_workers = new HashMap<Integer, rcworker<rcReceiverTask>>();
        m_msg = new LinkedList<String>();
        m_handler = handler;
    }

    public rcLogServer(Activity activity, int threadCapacity, int msgCapacity) {
        m_thCapacity = threadCapacity;
        m_msgCapacity = msgCapacity;
        m_workers = new HashMap<Integer, rcworker<rcReceiverTask>>();
        m_msg = new LinkedList<String>();
        m_Activity=activity;
    }
    public int getThreadCapacity() {
        return m_thCapacity;
    }

    public int getMessageCapacity() {
        return m_msgCapacity;
    }

    private void create() {
        for (int id = 0; id < m_thCapacity; id++) {
            m_workers.put(id, new rcworker<rcReceiverTask>(new rcReceiverTask(null,this)));
        }
    }

    synchronized public void start() {
        create();
        for (rcworker<?> worker : m_workers.values()) {
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

    synchronized public void stop() {
        for (rcworker<?> worker : m_workers.values()) {
            try {
                if(worker!=null) {
                    worker.rcSafeStop();
                    Log.d(TAG, "thread " + worker.getId() + " stoped.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Error in stoping thread " + worker.getId() + " "
                        + e.getMessage());
            }
        }
    }

    synchronized public void suspend() {
        for (rcworker<?> worker : m_workers.values()) {
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
        m_msg.add(new String(newbuf));
        if (m_handler != null)
            notifyHandler(new String(newbuf));
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
            result += s + "\n";
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
            Log.d(TAG, "Exception in sending data to handler.Error: " + e.getMessage());
        }
    }
    synchronized public Context getContext() {
        if(m_Activity!=null)
            return m_Activity.getApplicationContext();
        else
            return null;
    }
}
