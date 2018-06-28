package tech.threekilogram.transition.scene;

import android.view.View;
import tech.threekilogram.transition.Evaluator;

/**
 * 根据进度移动view位置
 *
 * @author wuxio 2018-06-23:12:17
 */
public class TranslateEvaluator implements Evaluator {

      private float mStartX;
      private float mStartY;
      private float mEndX;
      private float mEndY;
      private View  mView;

      /**
       * {@link View#getX()} 和 {@link View#getY()}
       *
       * @param view 需要移动的view
       * @param endX 结束时的x
       * @param endY 结束时的y
       */
      public TranslateEvaluator (View view, float endX, float endY) {

            mView = view;
            this.mStartX = view.getX();
            this.mStartY = view.getY();
            this.mEndX = endX;
            this.mEndY = endY;
      }

      public void setStartX (float startX) {

            mStartX = startX;
      }

      public void setStartY (float startY) {

            mStartY = startY;
      }

      public void setEndX (float endX) {

            mEndX = endX;
      }

      public void setEndY (float endY) {

            mEndY = endY;
      }

      @Override
      public void setFraction (float fraction) {

            float currentX = (mStartX + (mEndX - mStartX) * fraction);
            float currentY = (mStartY + (mEndY - mStartY) * fraction);

            mView.setX(currentX);
            mView.setY(currentY);
      }

      @Override
      public View getTarget () {

            return mView;
      }
}
