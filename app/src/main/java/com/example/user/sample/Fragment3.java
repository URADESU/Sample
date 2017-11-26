package com.example.user.sample;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        try{
            /* リスト選択時に画像を表示させる */
            ImageView imageview = (ImageView)view.findViewById(R.id.list_album_art);
            TypedArray typedArray = getResources().obtainTypedArray(R.array.array_EngDrawable);
            Drawable drawable = typedArray.getDrawable(selected);
            imageview.setImageDrawable(drawable);
        }catch(ArrayIndexOutOfBoundsException e){
            TextView error = (TextView)view.findViewById(R.id.error1);
            error.setText("画像が存在していません。ご問い合わせください。");

    }

        return view;


    }




}