package com.jorgesys.textmarquee;

/*** Jorgesys**/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Very important, to activate the marquee effect we must select the view!.
        findViewById(R.id.txtPoem).setSelected(true);


        //Creating programatically a TextView with marquee effect to modify their speed.
        final ScrollViewText scrollTextView = (ScrollViewText) findViewById(R.id.txtPoemProgramatically);
        scrollTextView.startScroll();

        //Pause ACTION_DOWN, resume scroll otherwise.
        scrollTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //https://developer.android.com/training/gestures/detector.html
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollTextView.pauseScroll();
                    Log.i(TAG, "Round duration: " + scrollTextView.getRoundDuration());
                    return true;
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    scrollTextView.resumeScroll();
                    return true;
                }
                 return false;
            }
        });
    }
}
