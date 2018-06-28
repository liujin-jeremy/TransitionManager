package tech.threekilogram.transition.scene;

import android.view.View;
import tech.threekilogram.transition.Evaluator;

/**
 * 用于根据进度变换alpha
 *
 * @author wuxio 2018-06-24:9:24
 */
public class AlphaEvaluator implements Evaluator {

      /**
       * 起始alpha
       */
      private float mAlphaBegin;

      /**
       * 结束alpha
       */
      private float mAlphaEnd;

      /**
       * 该类作用于哪个view
       */
      private View mView;

      /**
       * 创建一个evaluator，用于根据进度变换 view 的 alpha
       *
       * @param view 需要作用的alpha
       * @param alphaEnd 结束alpha
       */
      public AlphaEvaluator (View view, float alphaEnd) {

            this.mAlphaBegin = view.getAlpha();
            this.mAlphaEnd = alphaEnd;

            mView = view;
      }

      @Override
      public void setFraction (float fraction) {

            float currentAlpha = mAlphaBegin + (mAlphaEnd - mAlphaBegin) * fraction;
            mView.setAlpha(currentAlpha);
      }

      @Override
      public View getTarget () {

            return mView;
      }

      public void setAlphaBegin (float alphaBegin) {

            mAlphaBegin = alphaBegin;
      }

      public void setAlphaEnd (float alphaEnd) {

            mAlphaEnd = alphaEnd;
      }
}
