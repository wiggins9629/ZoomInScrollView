package com.wiggins.zoominscrollview.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * @Description 自定义ScrollView实现放大回弹效果
 * @Author 一花一世界
 */
public class ZoomInScrollView extends ScrollView {

    private View mHeaderView;
    private int mHeaderWidth;
    private int mHeaderHeight;

    // 是否正在下拉
    private boolean mIsPulling;
    // 记录首次按下位置
    private int mLastY;

    // 最大的放大倍数
    private float mScaleTimes = 2.0f;
    // 滑动放大系数：系数越大，滑动时放大程度越大
    private float mScaleRatio = 0.4f;
    // 回弹时间系数：系数越小，回弹越快
    private float mReplyRatio = 0.5f;

    // 当前坐标值
    private float currentX = 0;
    private float currentY = 0;
    // 移动坐标值
    private float distanceX = 0;
    private float distanceY = 0;
    // 最后坐标值
    private float lastX = 0;
    private float lastY = 0;
    // 上下滑动标记
    private boolean upDownSlide = false;

    private OnScrollListener onScrollListener;

    public ZoomInScrollView(Context context) {
        this(context, null);
    }

    public ZoomInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 设置不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
        View child = getChildAt(0);
        if (child != null && child instanceof ViewGroup) {
            // 获取默认第一个子View
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                mHeaderView = vg.getChildAt(0);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        currentX = ev.getX();
        currentY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                if (Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > 12) {
                    upDownSlide = true;
                }
                break;
        }

        lastX = currentX;
        lastY = currentY;

        if (upDownSlide && mHeaderView != null) {
            commOnTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @Description 触摸事件
     */
    private void commOnTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                // 手指离开后头部恢复图片
                mIsPulling = false;
                replyView();
                clear();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsPulling) {
                    // 第一次下拉
                    if (getScrollY() == 0) {
                        // 滚动到顶部时记录位置，否则正常返回
                        mLastY = (int) ev.getY();
                    } else {
                        break;
                    }
                }

                int distance = (int) ((ev.getY() - mLastY) * mScaleRatio);
                // 当前位置比记录位置要小时正常返回
                if (distance < 0) {
                    break;
                }
                mIsPulling = true;
                setZoom(distance);
                break;
        }
    }

    /**
     * @Description 头部缩放
     */
    private void setZoom(float s) {
        float scaleTimes = (float) ((mHeaderWidth + s) / (mHeaderWidth * 1.0));
        // 如超过最大放大倍数则直接返回
        if (scaleTimes > mScaleTimes) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.width = (int) (mHeaderWidth + s);
        layoutParams.height = (int) (mHeaderHeight * ((mHeaderWidth + s) / mHeaderWidth));
        // 设置控件水平居中
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mHeaderWidth) / 2, 0, 0, 0);
        mHeaderView.setLayoutParams(layoutParams);
    }

    /**
     * @Description 回弹动画
     */
    private void replyView() {
        final float distance = mHeaderView.getMeasuredWidth() - mHeaderWidth;
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    /**
     * @Description 清除属性值
     */
    private void clear() {
        lastX = 0;
        lastY = 0;
        distanceX = 0;
        distanceY = 0;
        upDownSlide = false;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * @Description 滑动监听
     */
    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
