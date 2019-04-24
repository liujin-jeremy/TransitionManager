package tech.liujin.transition.evaluator.wrapper;

import android.support.annotation.FloatRange;
import tech.liujin.transition.evaluator.Evaluator;

/**
 * 将一段进度映射成另一段进度
 *
 * @author wuxio 2018-06-25:12:57
 */
public class SegmentFractionEvaluator extends WrapperEvaluator {

      /**
       * 将这两段进度之间的进度映射成 （0~1）
       */
      private float mStartFraction;
      private float mEndFraction;

      public SegmentFractionEvaluator (
          Evaluator evaluatorActual,
          @FloatRange(from = 0, to = 1) float startFraction,
          @FloatRange(from = 0, to = 1) float endFraction ) {

            super( evaluatorActual );
            mStartFraction = startFraction;
            mEndFraction = endFraction;
      }

      @Override
      public void evaluate ( float process ) {

            if( process < mStartFraction ) {

                  mEvaluatorActual.evaluate( 0 );
            } else if( process >= mStartFraction && process <= mEndFraction ) {

                  process = ( process - mStartFraction ) / ( mEndFraction - mStartFraction );
                  mEvaluatorActual.evaluate( process );
            } else {

                  mEvaluatorActual.evaluate( 1 );
            }
      }

      public void setEndFraction ( float endFraction ) {

            mEndFraction = endFraction;
      }

      public void setStartFraction ( float startFraction ) {

            mStartFraction = startFraction;
      }
}
