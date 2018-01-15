package bkdev.testorder.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import bkdev.testorder.Model.Request;
import bkdev.testorder.Model.User;

/**
 * Created by User on 10/26/2017.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static  String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "สั่งซื้อแล้ว";
        else if (status.equals("1"))
            return "กำลังดำเนินการ";
        else
            return "จัดส่งแล้ว";
    }

    public static final String DELETE = "ลบ";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}