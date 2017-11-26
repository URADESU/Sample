package com.example.user.sample;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class Adapter
        extends FragmentStatePagerAdapter {

    private Fragment mCurrentFragment;

    private Activity activity;

    private boolean showItemFlg;

    public Adapter(FragmentManager fm,Fragment frg) {

        super(fm);

        mCurrentFragment = frg;
    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return mCurrentFragment;
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

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment(){
        return mCurrentFragment;
    }

}