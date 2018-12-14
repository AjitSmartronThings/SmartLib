package com.things.smartcam.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.things.smartcam.R;


public class EdittextWithDel extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    public EdittextWithDel(Context context) {
        this(context,null);
    }

    public EdittextWithDel(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public EdittextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.edittext_clear);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());

        setDelBtnVisible(false);

        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mClearDrawable != null && event.getAction() == MotionEvent.ACTION_UP){
            int x = (int) event.getX();

            boolean isInnerWidth = (x > (getWidth()-getTotalPaddingRight()))&&(x < (getWidth() - getPaddingRight()));

            Rect rect = mClearDrawable.getBounds();

            int height = rect.height();
            int y = (int) event.getY();

            int distance = (getHeight() - height) / 2;

            boolean isInnerHeigh = (y > distance)&&(y < height + distance);
            if (isInnerHeigh && isInnerWidth){
                setText("");
            }
        }

        return super.onTouchEvent(event);
    }


    private void setDelBtnVisible(boolean b) {
        Drawable right = b?mClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            setDelBtnVisible(getText().length() > 0);
        }else {
            setDelBtnVisible(hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()){
            setDelBtnVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void setShakeAnimation(){
        startAnimation(shakeAnimation(5));
    }


    private Animation shakeAnimation(int count) {
        TranslateAnimation animation = new TranslateAnimation(0,10,0,0);
        animation.setInterpolator(new CycleInterpolator(count));
        animation.setDuration(1000);
        return animation;
    }
}