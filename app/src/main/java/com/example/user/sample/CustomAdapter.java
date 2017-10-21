package com.example.user.sample;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//文字列を継承するArrayAdapter<String>でCustomAdapterクラスを作成
public class CustomAdapter extends ArrayAdapter<String>{

    private LayoutInflater inflater;

    static class ViewHolder{
        TextView labelText;
    }

    //コンストラクタ
    public CustomAdapter(Context context, int textViewResourceId,ArrayList<String>labelList) {
        super(context,textViewResourceId,labelList);
    }

    //convertView：前回使用したViewオブジェクト
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        ViewHolder holder;
        View view = convertView;

        //Viewを再利用している場合は新たにViewを作らない
        if (view == null) {

            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout,null);
            TextView label = (TextView)view.findViewById(R.id.txtBase);

            holder = new ViewHolder();
            holder.labelText=label;
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        //特定の行のデータを取得
        String str = getItem(position);

        if (!TextUtils.isEmpty(str)) {
            //テキストビューにラベルをセット
            holder.labelText.setText(str);
        }

        //行ごとに背景色を変える
        if (position%2 == 0) {		//2,4,6,8,...の偶数行の場合(背景は濃いグレー)
            holder.labelText.setBackgroundColor(Color.parseColor("#333333"));
        } else {					//1,3,5,7,...の奇数行の場合(背景は薄いグレー)
            holder.labelText.setBackgroundColor(Color.parseColor("#666666"));
        }

        //xmlファイルで定義したアニメーションを読み込む
        Animation anim=AnimationUtils.loadAnimation(getContext(),R.anim.item_animation);
        //リストアイテムのアニメーションを開始
        view.startAnimation(anim);

        return view;

    }
}