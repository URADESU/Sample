package com.example.user.sample;


        import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Adapter
        extends FragmentStatePagerAdapter {

    public Adapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return new Fragment1();
            default:
                return new Fragment2();

        }

    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "Page " + position;
    }

}