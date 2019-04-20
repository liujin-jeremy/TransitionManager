package tech.threekilogram.transition.impl;

import android.support.annotation.FloatRange;
import tech.threekilogram.transition.Evaluator;
import tech.threekilogram.transition.WrapperEvaluator;

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
      public void setFraction ( float fraction ) {

            if( fraction < mStartFraction ) {

                  mEvaluatorActual.setFraction( 0 );
            } else if( fraction >= mStartFraction && fraction <= mEndFraction ) {

                  fraction = ( fraction - mStartFraction ) / ( mEndFraction - mStartFraction );
                  mEvaluatorActual.setFraction( fraction );
            } else {

                  mEvaluatorActual.setFraction( 1 );
            }
      }

      public void setEndFraction ( float endFraction ) {

            mEndFraction = endFraction;
      }

      public void setStartFraction ( float startFraction ) {

            mStartFraction = startFraction;
      }
}
