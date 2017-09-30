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
import static com.example.user.sample.R.id.searchResultListView;

/**
 * Created by USER on 2017/09/30.
 * Author URA.
 */

public class SearchResultFragment extends Fragment {

    ArrayList<String> matchList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();

        matchList = args.getStringArrayList("matchList");

        return inflater.inflate(R.layout.search_result_frg, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //listをlistViewに結び付ける
        ListView lv = (ListView)view.findViewById(searchResultListView);

        //Adapterのインスタンス化
        //第3引数にlabelListを渡す
        CustomAdapter mAdapter = new CustomAdapter(getActivity(),0,matchList);

        //リストにAdapterをセット
        lv.setAdapter(mAdapter);


        //リストが選択された時の処理
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("selected", position);
                Fragment3 fg3 = new Fragment3();

                fg3.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_fragment, fg3).addToBackStack(null).commit();
            }
        });


        //リストアイテムの間の区切り線を非表示にする
        lv.setDivider(null);
    }

}
