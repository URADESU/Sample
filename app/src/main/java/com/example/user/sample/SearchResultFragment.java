package com.example.user.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static android.view.View.GONE;
import static com.example.user.sample.R.id.listView;
import static com.example.user.sample.R.id.searchResultListView;

/**
 * Created by USER on 2017/09/30.
 * Author URA.
 */

public class SearchResultFragment extends Fragment {

    ArrayList<String> matchList=new ArrayList<>();

    private boolean showItemFlg;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search_result_frg, container,false);
        Bundle args = getArguments();

        matchList = args.getStringArrayList("matchList");

        return v;
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

                showItemFlg = true;

                Bundle bundle = new Bundle();
                bundle.putInt("selected", position);
                Fragment3 fg3 = new Fragment3();

                fg3.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.contents3, fg3).addToBackStack(null).commit();

                /*メニューバーのボタン制御*/
                MainActivity maActivity = (MainActivity) getActivity();
                maActivity.setScreenInformation(2);
                maActivity.changeButton();
            }
        });


        //リストアイテムの間の区切り線を非表示にする
        lv.setDivider(null);

//        getFragmentManager().popBackStack();


    }

    public boolean getShowItemFlg(){
        return showItemFlg;
    }

    public void setShowItemFlg(boolean flg){
        showItemFlg = flg;
    }

}
