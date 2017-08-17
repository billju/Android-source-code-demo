package com.example.chuboy.viewpagertablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static int lastPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ViewFragment1(), "Title 1");
        adapter.addFragment(new ViewFragment2(), "Title 2");
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_image);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_image);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> TitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position){
            return FragmentList.get(position);
        }
        @Override
        public int getCount(){
            return FragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            FragmentList.add(fragment);
            TitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return TitleList.get(position);
        }
    }

}
