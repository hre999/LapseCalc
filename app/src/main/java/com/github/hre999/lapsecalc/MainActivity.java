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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /////////////
    // Attribs

    // constants
    static final int pages = 3;

    // Listeners
    TextView.OnEditorActionListener p1ActionListener = new TextView.OnEditorActionListener() {

        // Function for converting 01:01:00 to 3660 seconds
        // return seconds or return 0 for empty strings
        private int timestrToSec(String time) {
            if ( time.isEmpty() ) return 0;

            String[] units = time.split(":");
            int seconds = 0;

            for(int i = 0; i < units.length; i++){
                seconds += Integer.parseInt(units[i]) * Math.pow(60,units.length-i-1);
            }

            return seconds;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Handles
            TextView resultFrames = findViewById(R.id.p1ResultFrames);
            TextView resultInterval = findViewById(R.id.p1ResultInterval);
            TextView Capture= findViewById(R.id.p1Capture);
            TextView Clip = findViewById(R.id.p1Clip);
            TextView FPS = findViewById(R.id.p1Fps);

            // some vars to work with
            int secs_capture = timestrToSec(Capture.getText().toString());
            int secs_clip = timestrToSec(Clip.getText().toString());
            float fps = 30;
            if ( ! FPS.getText().toString().isEmpty() ) fps = Float.parseFloat(FPS.getText().toString());

            // fill the results
            if ( secs_capture == 0 | secs_clip == 0 ){
                resultFrames.setText("?");
                resultInterval.setText("?");
                return false;
            }

            float frames = secs_clip * fps;
            resultFrames.setText(String.format("%.0f", frames));
            resultInterval.setText(String.format("%.2f", secs_capture / frames));

            //resultFrames.setText(String.valueOf(secs_clip * fps));

            return false;   // return false so we still get the focus shift
        }
    };  // end of p1ActionListener

    //////////
    // Nested Classes

    // PagerAdapter for plugging separate XML into the ViewPager
    private class XMLPagerAdapter extends PagerAdapter {

        public int getCount() {
            return pages;
        }

        public Object instantiateItem(ViewGroup collection, int position) {

            // prep
            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int resId = 0;
            List<Integer> inputIds = new ArrayList<>();

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
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

    }   // End of XMLPagerAdapter


    ///////////
    // Methods

    // OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the ViewPager adapter
        XMLPagerAdapter adapter = new XMLPagerAdapter();
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

    }   // End of OnCreate

}
