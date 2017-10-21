package com.example.user.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    ImageView search;
    ImageView wiki;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(
                new Adapter(
                        getSupportFragmentManager()));

        //Wikiボタン
        wiki = (ImageView) findViewById(R.id.wiki_button);
        wiki.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //押下時の処理
                Fragment4 fragment4 = new Fragment4();

                wiki.setClickable(false);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contents,fragment4 );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        //検索ボタン
        search = (ImageView) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //押下時の処理
                findViewById(R.id.wiki_button).setVisibility(View.VISIBLE);
                findViewById(R.id.search_button).setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        wiki.setClickable(true);
        findViewById(R.id.wiki_button).setVisibility(View.GONE);
        findViewById(R.id.search_button).setVisibility(View.VISIBLE);
    }

    /**
     * 表示ボタンの切り替え
     */
    /*protected void changebuttom() {
        if (リストを表示) {//検索ボタン
            findViewById(R.id.wiki_button).setVisibility(View.GONE);
            findViewById(R.id.search_button).setVisibility(View.VISIBLE);
        } else if (詳細を表示) { // 詳細ボタン
            findViewById(R.id.wiki_button).setVisibility(View.VISIBLE);
            findViewById(R.id.search_button).setVisibility(View.GONE);
        }
    }*/
}