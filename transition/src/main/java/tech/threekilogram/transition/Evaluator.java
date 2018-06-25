package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-23:12:16
 */

import android.view.View;

/**
 * 通用静态动画接口
 */
public interface Evaluator {

    /**
     * 设置动画进度
     *
     * @param fraction 进度
     */
    void setFraction(float fraction);

    /**
     * return which view to set Fraction
     *
     * @return target : which view to set Fraction
     */
    View getTarget();
}
