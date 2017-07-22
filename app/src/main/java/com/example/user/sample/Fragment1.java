package com.example.user.sample;

import com.example.user.sample.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment1, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //listをlistViewに結び付ける
        ListView list=(ListView)view.findViewById(R.id.listView);

        //リストアイテムのラベルを格納する ArrayListをインスタンス化
        ArrayList<String>labeList=new ArrayList<String>();

        //「List Item " "」を20個リストに追加
        for(int i=1;i<=20;i++){
            labeList.add("List Item"+i);
        }

        //Adapterのインスタンス化
        //第3引数にlabelListを渡す
        CustomAdapter mAdapter=new CustomAdapter(getActivity(),0,labeList);

        //リストにAdapterをセット
        list.setAdapter(mAdapter);

        //リストアイテムの間の区切り線を非表示にする
        list.setDivider(null);
    }

}
