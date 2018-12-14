package com.things.smartcam.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class DelEditText extends AppCompatEditText implements TextWatcher {

    private final static String TAG = "EditTextWithDel";
    //    private Drawable imgAble;
    Drawable[] drawables;

    public DelEditText(Context context) {
        super(context);
        init(context, null);
    }

    public DelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        int resid = R.mipmap.edit_text_del_icon_delete_gray;
//        if (attrs != null) {
//            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DelEditText);
//            resid = typedArray.getResourceId(R.styleable.DelEditText_del_icon, R.mipmap.edit_text_del_icon_delete_gray);
//            typedArray.recycle();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            imgAble = context.getDrawable(resid);
//        } else {
//            imgAble = context.getResources().getDrawable(resid);
//        }
        addTextChangedListener(this);
        drawables = getCompoundDrawables();
        setDrawable();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void setDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        drawables[0] = left;
        drawables[1] = top;
        drawables[2] = right;
        drawables[3] = bottom;
        setDrawable();
    }

    @Override
    public void afterTextChanged(Editable s) {
        setDrawable();
        Log.i(TAG, "触发");
        if (listener != null) {
            Log.i(TAG, "监听");
            listener.onTextChanged(s.toString());
        }
    }

    // 设置删除图片
    private void setDrawable() {
        if (length() == 0) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 判断触碰是否结束
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            // 判断所触碰的位置是否为清除的按钮
            if (event.getX() > (getWidth() - getTotalPaddingRight())
                    && event.getX() < (getWidth() - getPaddingRight())) {
                // 将editText里面的内容清除
                setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        // 判断焦点失去和得到时的操作
        if (focused && length() != 0) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private OnTextChangedListener listener;

    public void setListener(OnTextChangedListener listener) {
        this.listener = listener;
    }

    public interface OnTextChangedListener {
        void onTextChanged(String txt);
    }
}