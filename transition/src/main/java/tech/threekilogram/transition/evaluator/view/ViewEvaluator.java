package tech.threekilogram.transition.evaluator.view;

/**
 * @author wuxio 2018-06-23:12:16
 */

import android.view.View;
import tech.threekilogram.transition.evaluator.Evaluator;

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
      /**
       * 记录当前进度
       */
      protected float   mProcess;

      protected ViewEvaluator ( View view ) {

            mView = view;
      }

      @Override
      public void evaluate ( float process ) {

            mProcess = process;
      }

      /**
       * return which view to set Fraction
       *
       * @return target : which view to set Fraction
       */
      @Override
      public View getTarget ( ) {

            return mView;
      }

      /**
       * 设置是否反转,同时将当前进度应用到view上,改变显示状态
       *
       * @param reversed true:反转
       */
      public void setReversed ( boolean reversed ) {

            if( reversed != isReversed ) {
                  isReversed = reversed;
                  evaluate( mProcess );
            }
      }

      /**
       * 设置是否反转的标记,不会改变显示状态
       *
       * @param reversed true:反转
       */
      public void justReversed ( boolean reversed ) {

            if( reversed != isReversed ) {
                  isReversed = reversed;
            }
      }

      /**
       * 当前是否反转了
       *
       * @return {@link #isReversed}的值
       */
      public boolean isReversed ( ) {

            return isReversed;
      }

      public float getProcess ( ) {

            return mProcess;
      }
}
