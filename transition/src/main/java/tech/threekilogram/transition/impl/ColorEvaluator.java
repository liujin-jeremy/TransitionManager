package tech.threekilogram.transition.impl;

import android.support.annotation.ColorInt;
import android.view.View;
import tech.threekilogram.transition.Evaluator;

/**
 * 根据进度值计算出一个颜色，并设置给view
 *
 * @author wuxio 2018-06-24:17:21
 */
public class ColorEvaluator implements Evaluator {

      @ColorInt
      private int mStartColor;
      @ColorInt
      private int mEndColor;

      /**
       * 使用该接口获取开始颜色，并且决定如何设置新的颜色
       */
      private ColorApply mConstructor;

      private View mView;

      public ColorEvaluator (
          View view,
          @ColorInt int endColor,
          ColorApply constructor ) {

            mView = view;
            mConstructor = constructor;
            mStartColor = constructor.getStartColor( view );
            mEndColor = endColor;
      }

      @Override
      public void setFraction ( float fraction ) {

            int currentColor = evaluate( fraction, mStartColor, mEndColor );
            mConstructor.onNewColorEvaluated( mView, fraction, currentColor );
      }

      @Override
      public View getTarget ( ) {

            return mView;
      }

      public void setStartColor ( @ColorInt int startColor ) {

            mStartColor = startColor;
      }

      public void setEndColor ( @ColorInt int endColor ) {

            mEndColor = endColor;
      }

      /**
       * 使用该接口构建一个Evaluator
       */
      public interface ColorApply {

            /**
             * 获取view 起始变化颜色
             *
             * @param view target
             *
             * @return start color
             */
            @ColorInt
            int getStartColor ( View view );

            /**
             * when new color evaluate this will call
             *
             * @param view view target
             * @param process current process
             * @param colorNew new color at this process
             */
            void onNewColorEvaluated ( View view, float process, int colorNew );
      }

      /**
       * 根据进度计算颜色值
       *
       * @param fraction 进度
       * @param startValue 开始
       * @param endValue 结束
       *
       * @return 当前颜色值
       */
      private static int evaluate ( float fraction, int startValue, int endValue ) {

            float startA = ( ( startValue >> 24 ) & 0xff ) / 255.0f;
            float startR = ( ( startValue >> 16 ) & 0xff ) / 255.0f;
            float startG = ( ( startValue >> 8 ) & 0xff ) / 255.0f;
            float startB = ( ( startValue ) & 0xff ) / 255.0f;

            float endA = ( ( endValue >> 24 ) & 0xff ) / 255.0f;
            float endR = ( ( endValue >> 16 ) & 0xff ) / 255.0f;
            float endG = ( ( endValue >> 8 ) & 0xff ) / 255.0f;
            float endB = ( ( endValue ) & 0xff ) / 255.0f;

            // convert from sRGB to linear
            startR = (float) Math.pow( startR, 2.2 );
            startG = (float) Math.pow( startG, 2.2 );
            startB = (float) Math.pow( startB, 2.2 );

            endR = (float) Math.pow( endR, 2.2 );
            endG = (float) Math.pow( endG, 2.2 );
            endB = (float) Math.pow( endB, 2.2 );

            // compute the interpolated color in linear space
            float a = startA + fraction * ( endA - startA );
            float r = startR + fraction * ( endR - startR );
            float g = startG + fraction * ( endG - startG );
            float b = startB + fraction * ( endB - startB );

            // convert back to sRGB in the [0..255] range
            a = a * 255.0f;
            r = (float) Math.pow( r, 1.0 / 2.2 ) * 255.0f;
            g = (float) Math.pow( g, 1.0 / 2.2 ) * 255.0f;
            b = (float) Math.pow( b, 1.0 / 2.2 ) * 255.0f;

            return Math.round( a ) << 24 | Math.round( r ) << 16 | Math.round( g ) << 8 | Math.round( b );
      }
}
