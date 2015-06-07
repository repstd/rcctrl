package io.yulw.test;
import io.yulw.rcctrl.rccontrol;
import io.yulw.rcctrl.rcpara;
import android.net.wifi.*;
public class testClient extends Thread
{
	public testClient(rcpara para) 
	{
		rccontrol.instance().reset(para);
	}
	public void run()
	{
		while(true)	{
			rccontrol.instance().sendPacket("SentFromRccontrol.");
		}
		
	}
}
