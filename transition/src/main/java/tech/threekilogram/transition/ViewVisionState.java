package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-21:18:14
 */

import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.view.View;

/**
 * record view location,use this to make anim
 *
 * @author wuxio
 */
@SuppressWarnings("WeakerAccess")
public class ViewVisionState {

    private static final String TAG = "ViewVisionState";


    ViewVisionState() {

    }


    /**
     * 创建一个当前的可见状态
     */
    public ViewVisionState(View view) {

        this(
                view,
                view.getLeft(),
                view.getTop(),
                view.getRight(),
                view.getBottom(),
                view.getRotation(),
                view.getAlpha()
        );
    }


    /**
     * 根据另一个可视状态创建一个一模一样的
     */
    public ViewVisionState(ViewVisionState visionState) {

        this(
                visionState.getView(),
                visionState.getLeft(),
                visionState.getTop(),
                visionState.getRight(),
                visionState.getBottom(),
                visionState.getRotation(),
                visionState.getAlpha()
        );
    }


    /**
     * 创建一个未来的可见状态
     */
    public ViewVisionState(
            View view,
            Rect rect) {

        this(
                view,
                rect.left,
                rect.top,
                rect.right,
                rect.bottom,
                view.getRotation(),
                view.getAlpha()
        );
    }


    /**
     * 创建一个未来的可见状态
     */
    public ViewVisionState(
            View view,
            Rect rect,
            float rotation,
            float alpha) {

        this(
                view,
                rect.left,
                rect.top,
                rect.right,
                rect.bottom,
                rotation,
                alpha
        );
    }


    /**
     * 创建一个未来的可见状态
     */
    public ViewVisionState(
            View view,
            int left,
            int top,
            int right,
            int bottom) {

        this.view = view;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.rotation = view.getRotation();
        this.alpha = view.getAlpha();
    }


    /**
     * 创建一个未来的可见状态
     */
    public ViewVisionState(
            View view,
            int left,
            int top,
            int right,
            int bottom,
            float rotation,
            float alpha) {

        this.view = view;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.rotation = rotation;
        this.alpha = alpha;
    }


    /**
     * view 需要使用动画的view
     */
    View view;

    /**
     * 坐标
     */
    int left;
    int top;
    int right;
    int bottom;

    /**
     * 旋转角度
     */
    float rotation;

    /**
     * alpha
     */
    float alpha;

    /**
     * 用于在变化过程中执行额外的操作
     */
    OnTransitionChangeListener mOnTransitionChangeListener;

    /**
     * 如果设置为true {@link #layoutView(float)}时会重新测量view,即:变化发生过程中不断重新测量
     */
    private boolean mRemeasureWhenLayoutView;


    public void setRemeasureWhenLayoutView(boolean remeasureWhenLayoutView) {

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

        int leftNew = view.getLeft() + left;
        int topNew = view.getTop() + top;
        int rightNew = view.getRight() + right;
        int bottomNew = view.getBottom() + bottom;
        float rotation = view.getRotation() + this.rotation;
        float alpha = view.getAlpha() + this.alpha;

        view.layout(
                leftNew,
                topNew,
                rightNew,
                bottomNew
        );

        view.setRotation(rotation);
        view.setAlpha(alpha);

        if (mRemeasureWhenLayoutView) {

            TransitionFactory.remeasureViewWithExactSpec(
                    view,
                    rightNew - leftNew,
                    bottomNew - topNew
            );
        }

        if (mOnTransitionChangeListener != null) {
            mOnTransitionChangeListener.onChange(view, process, leftNew, topNew, rightNew, bottomNew,
                    rotation, alpha);
        }
    }


    public int getLeft() {

        return left;
    }


    public int getTop() {

        return top;
    }


    public int getRight() {

        return right;
    }


    public int getBottom() {

        return bottom;
    }


    public float getRotation() {

        return rotation;
    }


    public float getAlpha() {

        return alpha;
    }


    public View getView() {

        return view;
    }


    public void setLeft(int left) {

        this.left = left;
    }


    public void setTop(int top) {

        this.top = top;
    }


    public void setRight(int right) {

        this.right = right;
    }


    public void setBottom(int bottom) {

        this.bottom = bottom;
    }


    public void setRotation(float rotation) {

        this.rotation = rotation;
    }


    public void setAlpha(@FloatRange(from = 0, to = 1f) float alpha) {

        this.alpha = alpha;
    }


    public void setView(View view) {

        this.view = view;
    }


    public OnTransitionChangeListener getOnTransitionChangeListener() {

        return mOnTransitionChangeListener;
    }


    public void setOnTransitionChangeListener(OnTransitionChangeListener onTransitionChangeListener) {

        this.mOnTransitionChangeListener = onTransitionChangeListener;
    }
}