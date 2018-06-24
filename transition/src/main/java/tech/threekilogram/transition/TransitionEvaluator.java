package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-23:12:16
 */

import android.view.View;

/**
 * 使用该类制作静态变化效果,不能根据时间变化,只能根据进度变化
 *
 * @author wuxio
 */
public class TransitionEvaluator implements Evaluator {

    private static final String TAG = "TransitionEvaluator";

    private View            mView;
    /**
     * 起始状态
     */
    private ViewVisionState mBegin;
    /**
     * 结束状态
     */
    private ViewVisionState mEnd;

    /**
     * 当{@link #setFraction(float)}时会重新布局view,如果此值为true,那么布局时就会重新测量
     */
    private boolean isRemeasureWhenFractionChanged;


    public TransitionEvaluator(View view, int endLeft, int endTop, int endRight, int endBottom) {

        ViewVisionState end = new ViewVisionState(view, endLeft, endTop, endRight, endBottom);
        setField(view, end);
    }


    public TransitionEvaluator(View view, ViewVisionState end) {

        setField(view, end);
    }


    private void setField(View view, ViewVisionState end) {

        mView = view;
        mBegin = new ViewVisionState(view);
        mEnd = end;
    }


    private void evaluate(float fraction, ViewVisionState startValue, ViewVisionState endValue) {

        /* 计算出当前的进度的值 */

        int left = (int) (startValue.left + (endValue.left - startValue.left) * fraction);
        int top = (int) (startValue.top + (endValue.top - startValue.top) * fraction);
        int right = (int) (startValue.right + (endValue.right - startValue.right) * fraction);
        int bottom = (int) (startValue.bottom + (endValue.bottom - startValue.bottom) * fraction);
        float rotation = startValue.rotation + (endValue.rotation - startValue.rotation) * fraction;
        float alpha = startValue.alpha + (endValue.alpha - startValue.alpha) * fraction;

        if (isRemeasureWhenFractionChanged) {

            TransitionFactory.remeasureViewWithExactSpec(
                    mView,
                    Math.abs(right - left),
                    Math.abs(bottom - top)
            );
        }

        mView.layout(left, top, right, bottom);
        mView.setRotation(rotation);
        mView.setAlpha(alpha);
    }


    @Override
    public void setFraction(float fraction) {

        evaluate(fraction, mBegin, mEnd);
    }


    /**
     * 当{@link #setFraction(float)}时会重新布局view,如果设置为true,那么布局时就会重新测量
     *
     * @param remeasureWhenFractionChanged true:布局时重新测量
     */
    public void setRemeasureWhenFractionChanged(boolean remeasureWhenFractionChanged) {

        isRemeasureWhenFractionChanged = remeasureWhenFractionChanged;
    }
}
