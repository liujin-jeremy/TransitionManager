package tech.threekilogram.transition.impl;

import android.view.View;
import tech.threekilogram.transition.ViewEvaluator;

/**
 * 用于根据进度变换alpha
 *
 * @author wuxio 2018-06-24:9:24
 */
public class AlphaEvaluator extends ViewEvaluator {

      /**
       * 起始alpha
       */
      private float mAlphaBegin;
      /**
       * 结束alpha
       */
      private float mAlphaEnd;

      /**
       * 创建一个evaluator，用于根据进度变换 view 的 alpha
       *
       * @param view 需要作用的alpha
       * @param alphaEnd 结束alpha
       */
      public AlphaEvaluator ( View view, float alphaEnd ) {

            this( view, view.getAlpha(), alphaEnd );
      }

      /**
       * 创建一个evaluator，用于根据进度变换 view 的 alpha
       *
       * @param view 需要作用的alpha
       * @param alphaEnd 结束alpha
       */
      public AlphaEvaluator ( View view, float alphaBegin, float alphaEnd ) {

            super( view );

            this.mAlphaBegin = alphaBegin;
            this.mAlphaEnd = alphaEnd;
            view.setAlpha( alphaBegin );
      }

      @Override
      public void setFraction ( float fraction ) {

            if( isReversed ) {
                  float currentAlpha = mAlphaEnd + ( mAlphaBegin - mAlphaEnd ) * fraction;
                  mView.setAlpha( currentAlpha );
            } else {
                  float currentAlpha = mAlphaBegin + ( mAlphaEnd - mAlphaBegin ) * fraction;
                  mView.setAlpha( currentAlpha );
            }
      }

      public void setAlphaBegin ( float alphaBegin ) {

            mAlphaBegin = alphaBegin;
      }

      public void setAlphaEnd ( float alphaEnd ) {

            mAlphaEnd = alphaEnd;
      }
}
