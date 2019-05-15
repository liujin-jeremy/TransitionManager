package tech.liujin.transition.evaluator.wrapper;

import android.view.View;
import tech.liujin.transition.evaluator.Evaluator;

/**
 * @author Liujin 2019/4/20:9:59:38
 */
public abstract class WrapperEvaluator implements Evaluator {

      /**
       * 被装饰的evaluator
       */
      protected Evaluator mEvaluatorActual;

      protected WrapperEvaluator ( Evaluator evaluator ) {

            mEvaluatorActual = evaluator;
      }

      public Evaluator getActual ( ) {

            return mEvaluatorActual;
      }

      @Override
      public View getTarget ( ) {

            return mEvaluatorActual.getTarget();
      }
}
