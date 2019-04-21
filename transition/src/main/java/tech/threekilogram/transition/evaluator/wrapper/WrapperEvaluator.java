package tech.threekilogram.transition.evaluator.wrapper;

import android.view.View;
import tech.threekilogram.transition.evaluator.Evaluator;

/**
 * @author Liujin 2019/4/20:9:59:38
 */
public abstract class WrapperEvaluator implements Evaluator {

      /**
       * 实际起作用的evaluator
       */
      protected Evaluator mEvaluatorActual;

      protected WrapperEvaluator ( Evaluator evaluator ) {

            mEvaluatorActual = evaluator;
      }

      protected Evaluator getActual ( ) {

            return mEvaluatorActual;
      }

      @Override
      public View getTarget ( ) {

            return mEvaluatorActual.getTarget();
      }
}
