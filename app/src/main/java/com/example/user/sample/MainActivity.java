package com.example.user.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;
import static android.view.View.GONE;

//TODO 大文字、小文字を区別せずに検索がヒットするよう修正する

public class MainActivity extends FragmentActivity {

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
    private Fragment2 frg2 = new Fragment2();

    private Toolbar toolbar;

    //Adapter
    private Adapter currentAdapt = new Adapter(getSupportFragmentManager(),frg1);

    //画像が表示中かを判断するフラグ
    private boolean showItemFlg;

    //SearchBarの表示、非表示を管理するフラグ
    private boolean showSB;

    //表示画面情報
    private int screenInformation;
    //押下されたリストの番号
    private int itemNum;

    //検索ヒットフラグ
    private boolean hitFlg;
    //検索ヒット時のインデックス番号を保持
    private int hitItemNum;
    //画面にどのページ(リスト)が表示されているか判断するフラグ
    private boolean witchPageFlg;

    String[] title;
    String[] text;
    String[] url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.pager);

        //ViewPagerにアダプターをセット
        viewPager.setAdapter(currentAdapt);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**　現在表示されているページのID(state)を取得し、処理を行う
             * position = 0 : アルファベッド画面
             * position = 1 : 日本語画面
             */
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    witchPageFlg = false;
                    Log.d("","アルファベッド画面");
                }else{
                    witchPageFlg = true;
                    Log.d("","日本語画面");
                }
            }

            //↓使わない関数
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Wikiボタン
        wiki = (ImageView) findViewById(R.id.wiki_button);
        wiki.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                //検索ヒット時にリストとダイアログ内容の整合性をとる
                if(hitFlg){
                    itemNum = hitItemNum;
                    hitFlg = false;         //ヒットフラグ初期化
                }

                //リソースファイルから、ダイアログの内容を取得
                if(!witchPageFlg) {
                    title = getResources().getStringArray(R.array.array_english);
                    text = getResources().getStringArray(R.array.ArrayEngText);
                    url = getResources().getStringArray(R.array.ArrayEngURL);
                }else{
                    title = getResources().getStringArray(R.array.array_japanese);
                    text = getResources().getStringArray(R.array.ArrayJapaneseText);
                    url = getResources().getStringArray(R.array.arrayJapaneseURL);
                }

                TextView hoge = new TextView(MainActivity.this);
                hoge.setPadding(100,0,100,0);
                hoge.setAutoLinkMask(Linkify.WEB_URLS);
                hoge.setText(url[itemNum]);
                //wikiダイアログ表示処理
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder
                        .setTitle(title[itemNum])
                        .setMessage(text[itemNum] + "\n\n" + "URL")
                        .setView(hoge)
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
                    hitFlg = true;
                    hitItemNum = i;
                    startPosition[a] = i;
                    a++;
                }
            }
        }else{
            for(int i = 0; i < labelListJapanese.size(); i++) {
                if (labelListJapanese.get(i).indexOf(s) != -1) {
                    matchList.add(labelListJapanese.get(i));
                    // 検索対象の初期表示ポジションを保持
                    hitFlg = true;
                    hitItemNum = i;
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
        //TODO Activityが終了しないよう、コメントアウト
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

    public void setItemNum(int num){
        itemNum = num;
    }

}