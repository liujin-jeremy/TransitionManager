package tech.liujin.transition.evaluator.view;

/**
 * @author wuxio 2018-06-23:12:16
 */

import android.view.View;
import tech.liujin.transition.Measure;
import tech.liujin.transition.ViewVisionState;

/**
 * 使用该类制作静态变化效果,根据进度变化
 *
 * @author wuxio
 */
public class TransitionEvaluator extends ViewEvaluator {

      /**
       * 作用于该view
       */
      private View            mView;
      /**
       * 起始状态
       */
      private ViewVisionState mBegin;
      /**
       * 结束状态
       */
      private ViewVisionState mEnd;

      /**
       * 当{@link #evaluate(float)}时会重新布局view,如果此值为true,那么布局时就会重新测量
       */
      private boolean isRemeasureWhenChanged = true;

      public TransitionEvaluator ( final View view, int endLeft, int endTop, int endRight, int endBottom ) {

            this( view, new ViewVisionState( view, endLeft, endTop, endRight, endBottom ) );
      }

      public TransitionEvaluator ( final View view, final ViewVisionState end ) {

            super( view );
            view.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        setField( view, new ViewVisionState( view ), end );
                  }
            } );
      }

      public TransitionEvaluator ( View view, ViewVisionState begin, ViewVisionState end ) {

            super( view );
            setField( view, begin, end );
      }

      private void setField ( View view, ViewVisionState begin, ViewVisionState end ) {

            mView = view;
            mBegin = begin;
            mEnd = end;
      }

      private void evaluate ( float fraction, ViewVisionState startValue, ViewVisionState endValue, boolean reversed ) {

            if( reversed ) {
                  ViewVisionState temp = startValue;
                  startValue = endValue;
                  endValue = temp;
            }

            /* 计算出当前的进度的值 */

            int left =
                (int) ( startValue.getLeft()
                    + ( endValue.getLeft() - startValue.getLeft() ) * fraction );

            int top =
                (int) ( startValue.getTop() + ( endValue.getTop() - startValue.getTop() ) * fraction );

            int right = (int) ( startValue.getRight()
                + ( endValue.getRight() - startValue.getRight() ) * fraction );

            int bottom =
                (int) ( startValue.getBottom()
                    + ( endValue.getBottom() - startValue.getBottom() ) * fraction );

            float rotation =
                startValue.getRotation()
                    + ( endValue.getRotation() - startValue.getRotation() ) * fraction;

            float rotationX =
                startValue.getRotationX()
                    + ( endValue.getRotationX() - startValue.getRotationX() ) * fraction;

            float rotationY =
                startValue.getRotationY()
                    + ( endValue.getRotationY() - startValue.getRotationY() ) * fraction;

            float alpha =
                startValue.getAlpha() + ( endValue.getAlpha() - startValue.getAlpha() ) * fraction;

            if( isRemeasureWhenChanged ) {

                  Measure.remeasureViewWithExactSpec(
                      mView,
                      Math.abs( right - left ),
                      Math.abs( bottom - top )
                  );
            }

            mView.layout( left, top, right, bottom );
            mView.setRotation( rotation );
            mView.setRotationX( rotationX );
            mView.setRotationY( rotationY );
            mView.setAlpha( alpha );
      }

      @Override
      public void evaluate ( float process ) {

            super.evaluate( process );
            evaluate( process, mBegin, mEnd, isReversed );
      }

      @Override
      public View getTarget ( ) {

            return mView;
      }

      /**
       * 当{@link #evaluate(float)}时会重新布局view,如果设置为true,那么布局时就会重新测量
       *
       * @param remeasureWhenChanged true:布局时重新测量
       */
      public void setRemeasureWhenChanged ( boolean remeasureWhenChanged ) {

            isRemeasureWhenChanged = remeasureWhenChanged;
      }
}
