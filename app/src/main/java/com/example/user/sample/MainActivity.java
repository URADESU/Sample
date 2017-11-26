package com.example.user.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;
import static android.view.View.GONE;

public class MainActivity extends FragmentActivity implements Fragment1.OnClickItemListener, Serializable{

    //ページの数だけ右左に画面
    private ViewPager viewPager;
    //検索画面
    private SearchView mSearchView;
    //ラベルリスト
    private ArrayList<String> labelListEnglish = new ArrayList();
    private ArrayList<String> labelListJapanese = new ArrayList();

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

    //表示画面情報
    private int screenInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();

        bundle.putSerializable("Activity",this);

        frg1.setArguments(bundle);

        viewPager = (ViewPager) findViewById(R.id.pager);

        //ViewPagerにアダプターをセット
        viewPager.setAdapter(currentAdapt);

        //Wikiボタン
        wiki = (ImageView) findViewById(R.id.wiki_button);
        wiki.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){


                //wikiダイアログ表示処理
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setTitle("IPアドレス")
                        .setMessage("IPアドレス（アイピーアドレス、英: Internet Protocol address）とは、IPにおいてパケットを送受信する機器を判別するための番号である。")
                        .setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
// ボタンをクリックしたときの動作
                            }
                        });
                builder.show();

            }
        });

        //検索ボタン
        search = (ImageView) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //押下時の処理

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
            labelListEnglish.add(englishWord);
        }

        final String[] listJapanese = getResources().getStringArray(R.array.array_japanese);
        //array.xmlの記載している単語の数だけ表示させるリストを作成する。
        for( String japaneseWord: listJapanese ){
            labelListJapanese.add(japaneseWord);
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
                    if(currentAdapt.getCurrentFragment()==frg1){
                        bundle.putInt("fragment", 1);
                    } else{
                        bundle.putInt("fragment", 2);
                    }
                    bundle.putStringArrayList("matchList", charSearch(s));
                    srf = new SearchResultFragment();

                    srf.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.contents3, srf).addToBackStack(null).commit();

                        /*メニューバーのボタン制御*/
                    setScreenInformation(1);
                    changeButton();
                }

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /* 初期表示時のポジション */
    int startPosition[] = new int[30];

    //検索された文字列をリスト内から探す
    public ArrayList charSearch(String s){
        ArrayList<String> matchList = new ArrayList<>();
        int a = 0;

        if(currentAdapt.getCurrentFragment().equals(frg1)){
            for(int i = 0; i < labelListEnglish.size(); i++) {
                if (labelListEnglish.get(i).indexOf(s) != -1) {
                    matchList.add(labelListEnglish.get(i));
                    // 検索対象の初期表示ポジションを保持
                    startPosition[a] = i;
                    a++;
                }
            }
        }else{
            for(int i = 0; i < labelListJapanese.size(); i++) {
                if (labelListJapanese.get(i).indexOf(s) != -1) {
                    matchList.add(labelListJapanese.get(i));
                    // 検索対象の初期表示ポジションを保持
                    startPosition[a] = i;
                    a++;
                }
            }

        }
        return matchList;
    }

    /**
     * 検索後に選択したリストの初期表示のポジションを取得
     */
    protected int getStartPosition(int position){
        return startPosition[position];
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
        if(getScreenInformation() >= 2){
            setScreenInformation(getScreenInformation() -1);
        }
        changeButton();
    }

    /**
     * 表示ボタンの切り替え
     */
    protected void changeButton() {
        if (getScreenInformation() == 1 && search.getVisibility() == View.GONE ){ //検索ボタン表示
            wiki.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
        } else if (getScreenInformation() == 2 ) { // 詳細ボタン表示
            search.setVisibility(View.GONE);
            wiki.setVisibility(View.VISIBLE);
            wiki.setEnabled(true);
        }
    }

    /**
     * 現在の表示画面情報を設定
     * page　1:一覧画面　2:詳細画面　3:説明表示
     */
    protected void setScreenInformation(int page) {
        screenInformation = page ;
    }

    /**
     * 現在の表示画面情報を取得
     */
    protected int getScreenInformation(){
        return screenInformation;
    }

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

    @Override
    public void setItemNum(int num){
        int hoge = num;
        Log.d("setItem= ",""+hoge);
    }

}