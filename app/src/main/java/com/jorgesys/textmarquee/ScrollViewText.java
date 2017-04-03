package com.jorgesys.textmarquee;


/*** Jorgesys**/

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.support.v7.widget.AppCompatTextView;


public class ScrollViewText extends AppCompatTextView {

    private Scroller mScroller;
    // the X offset when paused
    private int mXPaused = 0;
    // whether it's being paused
    private boolean mPaused = true;
    //milliseconds for a round of scrolling.
    private int mRoundDuration = 20000;

    public ScrollViewText(Context context) {
        this(context, null);
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    public ScrollViewText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    public ScrollViewText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    public void startScroll() {
        mXPaused = -1 * getWidth();
        mPaused = true;
        resumeScroll();
    }

    public void resumeScroll() {
        if (!mPaused) {
            return;
        }
        // Do not know why it would not scroll sometimes
        // if setHorizontallyScrolling is called in constructor.
        setHorizontallyScrolling(true);
        // use LinearInterpolator for steady scrolling
        mScroller = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mScroller);
        int scrollingLen = calculateScrollingLen();
        int distance = scrollingLen - (getWidth() + mXPaused);
        int duration = (new Double(mRoundDuration * distance * 1.00000/scrollingLen)).intValue();
        setVisibility(VISIBLE);
        mScroller.startScroll(mXPaused, 0, distance, 0, duration);
        invalidate();
        mPaused = false;
    }

    /**
     * calculate the scrolling length of the text in pixel
     *
     * @return the scrolling length in pixels
     */
    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.width() + getWidth();
        rect = null;
        return scrollingLen;
    }

    /**
     * pause scrolling the text
     */
    public void pauseScroll() {
        if (null == mScroller) {
            return;
        }

        if (mPaused) {
            return;
        }

        mPaused = true;
        // abortAnimation sets the current X to be the final X,
        // and sets isFinished to be true
        // so current position shall be saved
        mXPaused = mScroller.getCurrX();
        mScroller.abortAnimation();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == mScroller) return;
        if (mScroller.isFinished() && (!mPaused)) {
            this.startScroll();
        }
    }

    public int getRoundDuration() {
        return mRoundDuration;
    }

    public void setRndDuration(int duration) {
        this.mRoundDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }
}