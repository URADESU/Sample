package com.example.user.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    SearchView mSearchView;

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

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.toolbar_menu_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("MainActivity","送信時");

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("MainActivity","変更時");
                return false;
            }
        });
    }

}