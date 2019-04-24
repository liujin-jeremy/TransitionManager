package tech.liujin.transition.evaluator.view;

import android.view.View;

/**
 * 根据进度移动view位置
 *
 * @author wuxio 2018-06-23:12:17
 */
public class TranslateEvaluator extends ViewEvaluator {

      private float mStartX;
      private float mStartY;
      private float mEndX;
      private float mEndY;

      /**
       * {@link View#getX()} 和 {@link View#getY()}
       *
       * @param view 需要移动的view
       * @param endX 结束时的x
       * @param endY 结束时的y
       */
      public TranslateEvaluator ( final View view, final float endX, final float endY ) {

            super( view );
            view.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        mStartX = view.getX();
                        mStartY = view.getY();
                        mEndX = endX;
                        mEndY = endY;
                  }
            } );
      }

      public void setStartX ( float startX ) {

            mStartX = startX;
      }

      public void setStartY ( float startY ) {

            mStartY = startY;
      }

      public void setEndX ( float endX ) {

            mEndX = endX;
      }

      public void setEndY ( float endY ) {

            mEndY = endY;
      }

      @Override
      public void evaluate ( float process ) {

            super.evaluate( process );

            if( isReversed ) {

                  float currentX = ( mEndX + ( mStartX - mEndX ) * process );
                  float currentY = ( mEndY + ( mStartY - mEndY ) * process );

                  mView.setX( currentX );
                  mView.setY( currentY );
            } else {
                  float currentX = ( mStartX + ( mEndX - mStartX ) * process );
                  float currentY = ( mStartY + ( mEndY - mStartY ) * process );

                  mView.setX( currentX );
                  mView.setY( currentY );
            }
      }
}
