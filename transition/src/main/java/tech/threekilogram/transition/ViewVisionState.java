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
            Rect rect,
            float rotation,
            float alpha) {

        this(
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
            int left,
            int top,
            int right,
            int bottom,
            float rotation,
            float alpha) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.rotation = rotation;
        this.alpha = alpha;
    }


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
     * alphaChanged
     */
    float alpha;


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
}