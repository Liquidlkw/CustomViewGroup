package com.example.customviewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 1.measureChildren与setMeasuredDimension与resolveMeasureSize的关系
 * 2.measureChildren与measureChildWithMargins区别
 * 3.onMeasure与onLayout
 * 4.MeasureSpec系统测量规则的设计与优势（性能）
 *
 * @author Liquid
 */
public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //这里假设只有一个子View
        //是否有性能问题？
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 我想要多大
        View view = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
        int width = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + getPaddingLeft() + getPaddingRight()+10000;
        int height = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin + getPaddingTop() + getPaddingBottom();

        //是否有性能问题？

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
        int left = getPaddingLeft() + lp.leftMargin;
        int top = getPaddingTop() + lp.topMargin;
        view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            if (view != null && view.getVisibility() != GONE) {
                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
            }
        }
    }
}
