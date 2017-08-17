package com.example.chuboy.viewpagertablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ChuBoy on 2017/8/8.
 */

public class ViewFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){       
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("ViewFragment1");
        return view;
    }
}
