package com.example.user.sample;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * リストから単語を選択時に表示される画面のクラスです。
 */
public class Fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int selected = args.getInt("selected");
        String selectedTango = args.getString("tango");

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

//        TextView tv = (TextView)view.findViewById(R.id.textView3);
//        // listviewで選択された単語の名称を設定して表示させている。
//        tv.setText(selectedTango);

        /* リスト選択時に画像を表示させる */
        ImageView imageview = (ImageView)view.findViewById(R.id.list_album_art);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.array_EngDrawable);
        Drawable drawable = typedArray.getDrawable(selected);
        imageview.setImageDrawable(drawable);


        return view;


    }




}