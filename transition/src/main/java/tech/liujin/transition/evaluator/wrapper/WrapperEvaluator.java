package tech.liujin.transition.evaluator.wrapper;

import android.view.View;
import tech.liujin.transition.evaluator.Evaluator;
import tech.liujin.transition.evaluator.view.ViewEvaluator;

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

      /**
       * @return 遍历被装饰的evaluator, 找到viewEvaluator, 可能为null
       */
      public ViewEvaluator tryGetViewEvaluator ( ) {

            Evaluator result = mEvaluatorActual;
            while( result instanceof WrapperEvaluator ) {
                  result = ( (WrapperEvaluator) result ).getActual();
            }
            if( result instanceof ViewEvaluator ) {
                  return (ViewEvaluator) result;
            } else {
                  return null;
            }
      }

      @Override
      public View getTarget ( ) {

            return mEvaluatorActual.getTarget();
      }
}
