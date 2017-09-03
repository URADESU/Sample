package com.example.user.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.user.sample.R.id.listView;

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


        //リストアイテムのラベルを格納する ArrayListをインスタンス化
        ArrayList<String>labelList=new ArrayList<>();

        //「List Item " "」を20個リストに追加
        for(int i=1;i<=20;i++){
            labelList.add("List Item"+i);
        }

        //listをlistViewに結び付ける
        ListView lv = (ListView)view.findViewById(listView);

        //Adapterのインスタンス化
        //第3引数にlabelListを渡す
        CustomAdapter mAdapter = new CustomAdapter(getActivity(),0,labelList);

        //リストにAdapterをセット
        lv.setAdapter(mAdapter);


        //リストが選択された時の処理
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Fragment3 fg3 = new Fragment3();
                Bundle bundle = new Bundle();
                bundle.putInt("selected", position);
                fg3.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_fragment, fg3);

                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        //リストアイテムの間の区切り線を非表示にする
        lv.setDivider(null);
    }

}
