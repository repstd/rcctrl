package io.yulw.rcctrl;

import android.content.Context;
import android.widget.Toast;

public class rcutil 
{
	static void showMessageAsToast(Context context,String message) 
	{
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

}
