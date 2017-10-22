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

import static android.view.View.VISIBLE;
import static com.example.user.sample.R.id.listView;
import static com.example.user.sample.R.id.toolbar;

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
        final ArrayList<String>labelList = new ArrayList<>();

        //listをlistViewに結び付ける
        ListView lv = (ListView)view.findViewById(listView);

        final String[] listEnglish = getResources().getStringArray(R.array.array_english);
        //array.xmlの記載している単語の数だけ表示させるリストを作成する。
        for( String englishWord: listEnglish ){
            labelList.add(englishWord);
        }

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
                bundle.putString("tango", labelList.get(position));

                fg3.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                /* フラグメント置き換え時のアニメーション設定 */
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.contents2, fg3);
                /* 戻るボタンを押すと、一つ前のフラグメントに戻る */
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });




        //リストアイテムの間の区切り線を非表示にする
        lv.setDivider(null);
    }

}
