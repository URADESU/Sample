package com.example.user.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;
import static android.view.View.GONE;

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

    private Fragment1 frg1 = new Fragment1();

    private Toolbar toolbar;

    //Adapter
    private Adapter currentAdapt = new Adapter(getSupportFragmentManager(),frg1);

    //画像が表示中かを判断するフラグ
    private boolean showItemFlg;

    //SearchBarの表示、非表示を管理するフラグ
    private boolean showSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivityの始まり確認のためのlog
        d("MainActivity","MainActivity始まり");
        viewPager = (ViewPager) findViewById(R.id.pager);

        //ViewPagerにアダプターをセット
        viewPager.setAdapter(currentAdapt);


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
                findViewById(R.id.search_button).setVisibility(GONE);

                /*
                * SearchBar表示中→非表示
                * SearchBar非表示中→表示
                 */
                if(!showSB) {
                    toolbar.setVisibility(View.VISIBLE);
                    showSB = true;
                }else{
                    toolbar.setVisibility(GONE);
                    showSB = false;
                }

            }
        });

        //上のツールバー
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.activity_search);
        toolbar.setVisibility(GONE);

        //英語リストの中身
        final String[] listEnglish = getResources().getStringArray(R.array.array_english);
        //array.xmlの記載している単語の数だけ表示させるリストを作成する。
        for( String englishWord: listEnglish ){
            labelList.add(englishWord);
        }

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.toolbar_menu_search).getActionView();
        mSearchView.setIconified(false);
        mSearchView.clearFocus();   //キーボード隠してる
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //検索後、キーボード隠してる
                mSearchView.clearFocus();


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
        //TODO Activityが終了しないよう、コメントアウト中
//        super.onBackPressed();

        //検索バーを隠す
        toolbar.setVisibility(GONE);

        Log.d("MainActivity","showItemFlg=" + srf.getShowItemFlg());

        //画像表示中の場合は１つのFragmentだけpop
        if(srf.getShowItemFlg()) {

            getSupportFragmentManager().popBackStack();


        }else{
            //バックスタックにいくつFragmentが入っているかを取得
            int backStackCnt = getSupportFragmentManager().getBackStackEntryCount();
            //1以上Fragmentがある場合、その数分、ポップを行い元の画面へ戻る
            if (backStackCnt != 0) {
                for (int i = 0; i < backStackCnt; i++) {
                    getSupportFragmentManager().popBackStack();
                }
            }

        }

        srf.setShowItemFlg(false);

        wiki.setClickable(true);
        findViewById(R.id.wiki_button).setVisibility(GONE);
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

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())

                d("","リターンされました！");
                return fragment;
        }
        return null;
    }

    void setSearchBarVisible(boolean flg){
        if(flg){
            mSearchView.requestFocus();
            mSearchView.setIconified(false);
        }else{

        }
    }

}