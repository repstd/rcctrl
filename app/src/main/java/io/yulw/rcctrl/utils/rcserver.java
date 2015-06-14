package io.yulw.rcctrl.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class rcReceiverTask implements rctasks
{
    private final String TAG="rcReceiverTask";
    private boolean mIsFinished;
    private Context m_context;
    private rcserver m_svr;
    private static  DatagramSocket m_socket;
    static {
        try {
            DatagramSocket m_socket=new DatagramSocket(8000,null);
            m_socket.setSoTimeout(0);
        }catch (Exception e) {

        }
    }
    public rcReceiverTask(Context context,rcserver svr) {
        m_context=context;
        m_svr=svr;
        mIsFinished=false;
    }
    @Override
    public void execute()  {
        try {
            //Log.d(TAG,"execting....");
            DatagramPacket packet=new DatagramPacket(new byte[100],100);
            m_socket.receive(packet);
            String log=new String(packet.getData(),0,packet.getLength());
            Intent intent=new Intent();
            intent.setAction("LogServer");
            intent.putExtra("log", log);
            m_svr.appendBuffer(log);
            Log.d(TAG, "::execute#" + log);
            //LocalBroadcastManager.getInstance(m_context).sendBroadcast(intent);
        }catch (NullPointerException e) {
            Log.d(TAG,"::execute#NullPointerException."+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG,"::execute#Exception."+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Object getTask() {
        return null;
    }

    @Override
    public boolean isFinished() {
        if(rccontrol.instance().isClosed())
            return true;
        else
            return false;
    }
}

public class rcserver
{
    private final String TAG = "rcserver";
    int m_thCapacity;
    int m_msgCapacity;
    Handler m_handler = null;
    HashMap<Integer, rcworker<rcReceiverTask>> m_workers;
    Queue<String> m_msg;

    @SuppressLint("UseSparseArrays")
    public rcserver(int threadCapacity, int msgCapacity) {
        m_thCapacity = threadCapacity;
        m_msgCapacity = msgCapacity;
        m_workers = new HashMap<Integer, rcworker<rcReceiverTask>>();
        m_msg = new LinkedList<String>();
    }

    @SuppressLint("UseSparseArrays")
    public rcserver(Handler handler, int threadCapacity, int msgCapacity) {
        m_thCapacity = threadCapacity;
        m_msgCapacity = msgCapacity;
        m_workers = new HashMap<Integer, rcworker<rcReceiverTask>>();
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
}
