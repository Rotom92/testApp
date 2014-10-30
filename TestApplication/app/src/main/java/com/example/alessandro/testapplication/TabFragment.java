package com.example.alessandro.testapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alessandro on 23/10/2014.
 */
public class TabFragment extends Fragment{

    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle data=getArguments();
        index=data.getInt("idx");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);//inflater.inflate(R.layout.fragment_my, null);
        TextView textView=(TextView)v.findViewById(R.id.tabtextview);
        if((index+1)==1) {
            textView.setText("cazzo ");
        }
        else
        {
            if((index+1)==2)
            {
                textView.setText("di");
            }
            else
            {
                textView.setText("budda");
            }
        }

        return v;
    }
}
