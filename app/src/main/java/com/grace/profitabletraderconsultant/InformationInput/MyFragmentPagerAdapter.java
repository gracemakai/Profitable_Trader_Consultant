package com.grace.profitabletraderconsultant.InformationInput;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new CompanyInfo();
            case 1:
                return new ProductInfo();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
