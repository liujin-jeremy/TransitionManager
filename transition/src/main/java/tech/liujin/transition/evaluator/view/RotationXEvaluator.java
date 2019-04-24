package tech.liujin.transition.evaluator.view;

import android.view.View;

/**
 * 根据进度旋转view角度
 *
 * @author wuxio 2018-06-24:9:24
 */
public class RotationXEvaluator extends ViewEvaluator {

      /**
       * 起始角度
       */
      private float mRotationBegin;
      /**
       * 结束角度
       */
      private float mRotationEnd;

      public RotationXEvaluator ( View view, float rotationEnd ) {

            super( view );

            this.mRotationBegin = view.getRotation();
            this.mRotationEnd = rotationEnd;

            mView = view;
      }

      @Override
      public void evaluate ( float process ) {

            super.evaluate( process );

            if( isReversed ) {
                  float current = mRotationEnd + ( mRotationBegin - mRotationEnd ) * process;
                  mView.setRotationX( current );
            } else {

                  float current = mRotationBegin + ( mRotationEnd - mRotationBegin ) * process;
                  mView.setRotationX( current );
            }
      }

      public void setRotationBegin ( float rotationBegin ) {

            mRotationBegin = rotationBegin;
      }

      public void setRotationEnd ( float rotationEnd ) {

            mRotationEnd = rotationEnd;
      }
}
