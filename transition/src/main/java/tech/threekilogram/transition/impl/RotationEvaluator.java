package tech.threekilogram.transition.impl;

import android.view.View;
import tech.threekilogram.transition.ViewEvaluator;

/**
 * 根据进度旋转view角度
 *
 * @author wuxio 2018-06-24:9:24
 */
public class RotationEvaluator extends ViewEvaluator {

      /**
       * 起始角度
       */
      private float mRotationBegin;
      /**
       * 结束角度
       */
      private float mRotationEnd;

      public RotationEvaluator ( View view, float rotationEnd ) {

            super( view );

            this.mRotationBegin = view.getRotation();
            this.mRotationEnd = rotationEnd;

            mView = view;
      }

      @Override
      public void setFraction ( float fraction ) {

            if( isReversed ) {
                  float current = mRotationEnd + ( mRotationBegin - mRotationEnd ) * fraction;
                  mView.setRotation( current );
            } else {
                  float current = mRotationBegin + ( mRotationEnd - mRotationBegin ) * fraction;
                  mView.setRotation( current );
            }
      }

      public void setRotationBegin ( float rotationBegin ) {

            mRotationBegin = rotationBegin;
      }

      public void setRotationEnd ( float rotationEnd ) {

            mRotationEnd = rotationEnd;
      }
}
