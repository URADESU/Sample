package com.example.user.sample;


        import com.example.user.sample.Fragment1;
        import com.example.user.sample.Fragment2;
        import com.example.user.sample.Fragment3;

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
            case 1:
                return new Fragment2();
            default:
                return new Fragment3();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}