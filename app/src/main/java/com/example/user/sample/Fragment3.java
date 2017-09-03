package com.example.user.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        int selected = args.getInt("selected");

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        TextView tv = (TextView)view.findViewById(R.id.textView3);
        tv.setText(String.valueOf(selected) + "番が選択されました。");

        return inflater.inflate(R.layout.fragment_fragment3, null);
    }

}