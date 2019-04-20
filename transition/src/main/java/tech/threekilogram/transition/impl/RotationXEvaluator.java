package tech.threekilogram.transition.impl;

import android.view.View;
import tech.threekilogram.transition.Evaluator;

/**
 * 根据进度旋转view角度
 *
 * @author wuxio 2018-06-24:9:24
 */
public class RotationXEvaluator implements Evaluator {

      /**
       * 起始角度
       */
      private float mRotationBegin;
      /**
       * 结束角度
       */
      private float mRotationEnd;

      /**
       * 作用于该view
       */
      private View mView;

      public RotationXEvaluator ( View view, float rotationEnd ) {

            this.mRotationBegin = view.getRotation();
            this.mRotationEnd = rotationEnd;

            mView = view;
      }

      @Override
      public void setFraction ( float fraction ) {

            float current = mRotationBegin + ( mRotationEnd - mRotationBegin ) * fraction;
            mView.setRotationX( current );
      }

      @Override
      public View getTarget ( ) {

            return mView;
      }

      public void setRotationBegin ( float rotationBegin ) {

            mRotationBegin = rotationBegin;
      }

      public void setRotationEnd ( float rotationEnd ) {

            mRotationEnd = rotationEnd;
      }
}
