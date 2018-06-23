package tech.threekilogram.transition;

import android.view.View;

/**
 * 该监听用于{@link TransitionFactory}执行过程中的监听
 *
 * @author wuxio 2018-06-22:10:39
 */
public interface OnTransitionChangeListener {

    /**
     * 监听view 变化过程
     *
     * @param view     view
     * @param process  当前进度
     * @param left     当前left
     * @param top      当前top
     * @param right    当前right
     * @param bottom   当前bottom
     * @param rotation 当前rotation
     * @param alpha    当前alpha
     */
    void onChange(
            View view,
            float process,
            int left,
            int top,
            int right,
            int bottom,
            float rotation,
            float alpha
    );
}
