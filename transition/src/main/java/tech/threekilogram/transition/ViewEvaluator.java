package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-23:12:16
 */

import android.view.View;

/**
 * 通用进度动画接口
 *
 * @author liujin
 */
public abstract class ViewEvaluator implements Evaluator {

      /**
       * 需要使用动画的view
       */
      protected View    mView;
      /**
       * 是否反转动画
       */
      protected boolean isReversed;

      protected ViewEvaluator ( View view ) {

            mView = view;
      }

      /**
       * return which view to set Fraction
       *
       * @return target : which view to set Fraction
       */
      public View getTarget ( ) {

            return mView;
      }

      /**
       * 设置是否反转
       *
       * @param reversed true:反转
       */
      public void setReversed ( boolean reversed ) {

            isReversed = reversed;
      }

      public boolean isReversed ( ) {

            return isReversed;
      }
}
