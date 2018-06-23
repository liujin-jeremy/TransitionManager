package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-21:18:14
 */

import android.view.View;

/**
 * @author wuxio
 */
@SuppressWarnings("WeakerAccess")
class ViewRelayout {

    private static final String TAG = "ViewVisionState";


    ViewRelayout(View view) {

        this.view = view;
    }


    /**
     * view 需要使用动画的view
     */
    View view;

    /**
     * 坐标
     */
    int leftChanged;
    int topChanged;
    int rightChanged;
    int bottomChanged;

    /**
     * 旋转角度
     */
    float rotationChanged;

    /**
     * alphaChanged
     */
    float alphaChanged;

    /**
     * 用于在变化过程中执行额外的操作
     */
    TransitionFactory.OnTransitionChangeListener onTransitionChangeListener;

    /**
     * 如果设置为true {@link #layoutView(float)}时会重新测量view,即:变化发生过程中不断重新测量
     */
    boolean mRemeasureWhenLayoutView;


    void setRemeasureWhenLayoutView(boolean remeasureWhenLayoutView) {

        this.mRemeasureWhenLayoutView = remeasureWhenLayoutView;
    }


    /**
     * {@link TransitionFactory.ViewLocationAnimatorUpdateListener} 每次更新时回调该方法,重新布局view
     */
    void layoutView(float process) {

        /* 通过重新布局view的位置实现变化效果 */
        /* 注意:进度变化时计算的是这个进度和上一个进度的差值,所以下面使用加法增加该差值
         * 使用差值的原因参考 tech.threekilogram.transition.TransitionFactory.ViewLocationEvaluator 说明
         * */

        int leftNew = view.getLeft() + leftChanged;
        int topNew = view.getTop() + topChanged;
        int rightNew = view.getRight() + rightChanged;
        int bottomNew = view.getBottom() + bottomChanged;
        float rotation = view.getRotation() + this.rotationChanged;
        float alpha = view.getAlpha() + this.alphaChanged;

        if (mRemeasureWhenLayoutView) {

            TransitionFactory.remeasureViewWithExactSpec(
                    view,
                    rightNew - leftNew,
                    bottomNew - topNew
            );
        }

        view.layout(
                leftNew,
                topNew,
                rightNew,
                bottomNew
        );

        view.setRotation(rotation);
        view.setAlpha(alpha);

        if (onTransitionChangeListener != null) {
            onTransitionChangeListener.onChange(
                    view,
                    process,
                    leftNew,
                    topNew,
                    rightNew,
                    bottomNew,
                    rotation,
                    alpha
            );
        }
    }
}