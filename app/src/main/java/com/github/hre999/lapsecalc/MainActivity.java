package com.github.hre999.lapsecalc;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Page Adapter for plugging the Nested XML into the ViewPager
    class NestedXMLPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.group1;
                    break;
                case 1:
                    resId = R.id.group2;
                    break;
                case 2:
                    resId = R.id.group3;
                    break;
            }
            return findViewById(resId);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the ViewPager adapter
        NestedXMLPagerAdapter adapter = new NestedXMLPagerAdapter();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(3);
    }


}
