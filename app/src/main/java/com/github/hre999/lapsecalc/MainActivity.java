package com.github.hre999.lapsecalc;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /////////////
    // Constants
    static final int pages = 3;

    //////////
    // Classes

    // PagerAdapter for plugging seperate XML into the ViewPager
    private class XMLPagerAdapter extends PagerAdapter {

        // Listeners...apparently we put these here now?
        TextView.OnEditorActionListener p1ActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(MainActivity.this, v.getText(), Toast.LENGTH_SHORT).show();
                return false;   // return false so we still get the focus shift
            }
        };

        public int getCount() {
            return pages;
        }

        public Object instantiateItem(View collection, int position) {

            // prep
            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int resId = 0;
            List<Integer> inputIds = new ArrayList();

            // page details
            switch (position) {
                case 0:
                    resId = R.layout.page1;
                    inputIds.add(R.id.p1Capture);
                    inputIds.add(R.id.p1Clip);
                    inputIds.add(R.id.p1Fps);
                    break;
                case 1:
                    resId = R.layout.page2;
                    break;
                case 2:
                    resId = R.layout.page3;
                    break;
            }

            // Inflate and add layout
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view, 0);

            // grab our inputs and attach the ActionListener
            for(int id:inputIds){
                EditText input = findViewById(id);
                input.setOnEditorActionListener(p1ActionListener);
            }

            return view;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

    }


    ///////////
    // Methods

    // OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the ViewPager adapter
        XMLPagerAdapter adapter = new XMLPagerAdapter();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

    }

}
