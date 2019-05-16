package tech.liujin.transition.evaluator.view;

import android.view.View;
import tech.liujin.transition.Measure;
import tech.liujin.transition.ViewVisionState;

/**
 * @author Liujin 2019/4/20:18:50:18
 */
public class VisionStateEvaluator extends ViewEvaluator {

      private ViewVisionState mStateStart;
      private ViewVisionState mStateEnd;
      private ViewVisionState mTemp = new ViewVisionState();

      /**
       * 当{@link #evaluate(float)}时会重新布局view,如果此值为true,那么布局时就会重新测量
       */
      private boolean isRemeasureWhenChanged = true;

      public static VisionStateEvaluator changeLeft ( final View target, int newLeft ) {

            ViewVisionState end = new ViewVisionState( target );
            end.setLeft( newLeft );
            return new VisionStateEvaluator( target, end );
      }

      public static VisionStateEvaluator changeRight ( final View target, int newRight ) {

            ViewVisionState end = new ViewVisionState( target );
            end.setRight( newRight );
            return new VisionStateEvaluator( target, end );
      }

      public static VisionStateEvaluator changeTop ( final View target, int newTop ) {

            ViewVisionState end = new ViewVisionState( target );
            end.setTop( newTop );
            return new VisionStateEvaluator( target, end );
      }

      public static VisionStateEvaluator changeBottom ( final View target, int newBottom ) {

            ViewVisionState end = new ViewVisionState( target );
            end.setBottom( newBottom );
            return new VisionStateEvaluator( target, end );
      }

      public VisionStateEvaluator ( final View view, final ViewVisionState end ) {

            super( view );
            view.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        mStateStart = new ViewVisionState( view );
                        mStateEnd = end;
                  }
            } );
      }

      public VisionStateEvaluator ( View view, ViewVisionState start, ViewVisionState end ) {

            super( view );
            mStateStart = start;
            mStateEnd = end;
      }

      public void setStateEnd ( ViewVisionState stateEnd ) {

            mStateEnd = stateEnd;
      }

      public ViewVisionState getStateEnd ( ) {

            return mStateEnd;
      }

      public void setStateStart ( ViewVisionState stateStart ) {

            mStateStart = stateStart;
      }

      public ViewVisionState getStateStart ( ) {

            return mStateStart;
      }

      @Override
      public void evaluate ( float process ) {

            super.evaluate( process );

            if( isReversed ) {
                  ViewVisionState.calculateDiff( mStateEnd, mStateStart, process, mTemp );
            } else {
                  ViewVisionState.calculateDiff( mStateStart, mStateEnd, process, mTemp );
            }

            if( isRemeasureWhenChanged ) {
                  Measure.remeasureViewWithExactSpec(
                      mView,
                      Math.abs( mTemp.getRight() - mTemp.getLeft() ),
                      Math.abs( mTemp.getBottom() - mTemp.getTop() )
                  );
            }

            mTemp.applyTo( mView );
      }

      /**
       * 当{@link #evaluate(float)}时会重新布局view,如果设置为true,那么布局时就会重新测量
       *
       * @param remeasureWhenChanged true:布局时重新测量
       */
      public void setRemeasureWhenChanged ( boolean remeasureWhenChanged ) {

            isRemeasureWhenChanged = remeasureWhenChanged;
      }

      @Override
      public String toString ( ) {

            return "process:" + mProcess + " " + mTemp.toString();
      }
}
