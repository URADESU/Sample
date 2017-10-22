package com.example.user.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.label;
import static android.view.View.GONE;
import static com.example.user.sample.R.id.listView;
import android.view.View;
import android.widget.ImageView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    SearchView mSearchView;
    ArrayList<String> labelList = new ArrayList();
    CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
    private TextView label;
    View view;

    private SearchResultFragment srf = new SearchResultFragment();
    ImageView search;
    ImageView wiki;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity","MainActivity始まり");

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.activity_search);

        final String[] listEnglish = getResources().getStringArray(R.array.array_japanese);
        //array.xmlの記載している単語の数だけ表示させるリストを作成する。
        for( String englishWord: listEnglish ){
            labelList.add(englishWord);
        }

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.toolbar_menu_search).getActionView();
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Log.d("MainActivity","送信時　入力文字列＝"+ s);
//
//                ListView lv = (ListView)findViewById(listView);
//
//                lv.setSelection(charSearch(s));
                ArrayList<String> searchResult = charSearch(s);

                if(searchResult.size() > 0) {


                    Bundle bundle = new Bundle();

                    bundle.putStringArrayList("matchList", charSearch(s));

                    srf = new SearchResultFragment();
                    srf.setArguments(bundle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_fragment, srf).addToBackStack(null).commit();
                }

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("MainActivity","変更時");
                return false;
            }
        });
    }

    //検索された文字列をリスト内から探す
    public ArrayList charSearch(String s){
//        int position = 0;
        ArrayList<String> matchList = new ArrayList<>();

        for(int i = 0; i < labelList.size(); i++) {
            if (labelList.get(i).indexOf(s) != -1) {
                matchList.add(labelList.get(i));
            }
        }

        return matchList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment1 frg1 = new Fragment1();
        Fragment2 frg2 = new Fragment2();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_fragment, frg2).commit();
            return false;
    }


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