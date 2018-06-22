package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-21:18:14
 */

import android.util.Log;
import android.view.View;

/**
 * record view location,use this to make anim
 *
 * @author wuxio
 */
@SuppressWarnings("WeakerAccess")
public class ViewLocation {

    private static final String TAG = "ViewLocation";


    ViewLocation() {

    }


    public ViewLocation(
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


    public ViewLocation(View view) {

        this.view = view;
        this.left = view.getLeft();
        this.top = view.getTop();
        this.right = view.getRight();
        this.bottom = view.getBottom();
        this.rotation = view.getRotation();
        this.alpha = view.getAlpha();
    }


    public ViewLocation(ViewLocation location) {

        this.view = location.view;
        this.left = location.getLeft();
        this.top = location.getTop();
        this.right = location.getRight();
        this.bottom = location.getBottom();
        this.rotation = location.getRotation();
        this.alpha = location.getAlpha();
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
    TransitionAction actionInTransition;

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
        Log.i(TAG, "layoutView:setAlpha: " + alpha);
        view.setAlpha(alpha);

        if (actionInTransition != null) {
            actionInTransition.onChange(view, process, leftNew, topNew, rightNew, bottomNew, rotation);
        }

        if (mRemeasureWhenLayoutView) {

            TransitionFactory.remeasureViewWithExactSpec(
                    view,
                    rightNew - leftNew,
                    bottomNew - topNew
            );
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


    public void setLeftOffset(int offset) {

        left += offset;
    }


    public void setTopOffset(int offset) {

        top += offset;
    }


    public void setRightOffset(int offset) {

        right += offset;
    }


    public void setBottomOffset(int offset) {

        bottom += offset;
    }


    public void setRotation(float rotation) {

        this.rotation = rotation;
    }


    public void setAlpha(float alpha) {

        this.alpha = alpha;

        if (this.alpha < 0) {
            this.alpha = 0;
        }

        if (this.alpha > 1) {
            this.alpha = 1;
        }
    }


    public void setView(View view) {

        this.view = view;
    }


    public void setOffset(int xOffset, int yOffset) {

        setOffset(xOffset, yOffset, xOffset, yOffset);
    }


    public void setOffset(int leftOffset, int topOffset, int rightOffset, int bottomOffset) {

        setLeftOffset(leftOffset);
        setTopOffset(topOffset);
        setRightOffset(rightOffset);
        setBottomOffset(bottomOffset);
    }


    public void setActionInTransition(TransitionAction actionInTransition) {

        this.actionInTransition = actionInTransition;
    }


    public TransitionAction getActionInTransition() {

        return actionInTransition;
    }
}