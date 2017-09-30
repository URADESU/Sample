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
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.label;
import static com.example.user.sample.R.id.listView;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    SearchView mSearchView;
    ArrayList<String> labelList = new ArrayList();
    CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
    private TextView label;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity","MainActivity始まり");

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(
                new Adapter(
                        getSupportFragmentManager()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.activity_search);

        for(int i=1;i<=20;i++){
            labelList.add("List Item"+i);
        }

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.toolbar_menu_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Log.d("MainActivity","送信時　入力文字列＝"+ s);
//
//                ListView lv = (ListView)findViewById(listView);
//
//                lv.setSelection(charSearch(s));



                Bundle bundle = new Bundle();

                bundle.putStringArrayList("matchList",charSearch(s));


                SearchResultFragment srf = new SearchResultFragment();
                srf.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_fragment, srf).addToBackStack(null).commit();

                return false;
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
        int position = 0;
        ArrayList<String> matchList = new ArrayList<>();

        for(int i = 0; i < labelList.size(); i++) {
            if (labelList.get(i).indexOf(s) != -1) {
                matchList.add(labelList.get(i));
            }
        }

        return matchList;
    }

}