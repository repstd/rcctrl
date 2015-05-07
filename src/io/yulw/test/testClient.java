package io.yulw.test;
import io.yulw.rcctrl.rccontrol;
import io.yulw.rcctrl.rcpara;
import android.net.wifi.*;
public class testClient extends Thread
{
	private rccontrol m_controller;
	public testClient(rcpara para) 
	{
		m_controller=new rccontrol(para);
	}
	public void run()
	{
		while(true)	{
			m_controller.sendPacket("SentFromRccontrol.");
		}
		
	}
}
