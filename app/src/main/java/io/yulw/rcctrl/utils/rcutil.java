package io.yulw.rcctrl.utils;

import android.content.Context;
import android.widget.Toast;

public class rcutil {
    static public void showMessageAsToast(Context context, String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
