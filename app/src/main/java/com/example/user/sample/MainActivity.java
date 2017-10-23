package com.example.user.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    //ページの数だけ右左に画面
    private ViewPager viewPager;
    //検索画面
    private SearchView mSearchView;
    //ラベルリスト
    private ArrayList<String> labelList = new ArrayList();
//    private CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
//    private TextView label;
//    private View view;

    //検索結果画面
    private SearchResultFragment srf = new SearchResultFragment();
    //検索ボタンイメージ
    private ImageView search;
    //wikiボタンイメージ
    private ImageView wiki;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivityの始まり確認のためのlog
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
                /* wikiボタン押下時、クリック不可にする。 */
                wiki.setClickable(false);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contents3,fragment4 );
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

        //上のツールバー
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.activity_search);

        //英語リストの中身
        final String[] listEnglish = getResources().getStringArray(R.array.array_english);
        //array.xmlの記載している単語の数だけ表示させるリストを作成する。
        for( String englishWord: listEnglish ){
            labelList.add(englishWord);
        }

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.toolbar_menu_search).getActionView();
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


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
    public void onBackPressed(){
        super.onBackPressed();

        //バックスタックにいくつFragmentが入っているかを取得
        int backStackCnt = getSupportFragmentManager().getBackStackEntryCount();
        //1以上Fragmentがある場合、その数分、ポップを行い元の画面へ戻る
        if (backStackCnt != 0) {
            for(int i = 0;i < backStackCnt;i++) {
                getSupportFragmentManager().popBackStack();
            }
        }


        wiki.setClickable(true);
        findViewById(R.id.wiki_button).setVisibility(View.GONE);
        findViewById(R.id.search_button).setVisibility(View.VISIBLE);
    }

    /**
     * 表示ボタンの切り替え
     */
    /*
    protected void changebuttom() {
        if (リストを表示) {//検索ボタン
            findViewById(R.id.wiki_button).setVisibility(View.GONE);
            findViewById(R.id.search_button).setVisibility(View.VISIBLE);
        } else if (詳細を表示) { // 詳細ボタン
            findViewById(R.id.wiki_button).setVisibility(View.VISIBLE);
            findViewById(R.id.search_button).setVisibility(View.GONE);
        }
    }*/
}