package com.example.alessandro.testapplication;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alessandro on 30/10/2014.
 */
public class Message {
    public static void message(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
