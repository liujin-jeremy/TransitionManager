package tech.liujin.transition.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author Liujin 2019/5/16:17:11:17
 */
public class CollapsingLayout extends FrameLayout {

      private static final String TAG = CollapsingLayout.class.getSimpleName();

      private float mCurrentProcess;
      private int   mNormalHeight;
      private int   mCollapsingHeight;

      public CollapsingLayout ( Context context ) {

            super( context );
      }

      public CollapsingLayout (
          Context context, AttributeSet attrs ) {

            super( context, attrs );
      }

      public CollapsingLayout (
          Context context, AttributeSet attrs, int defStyleAttr ) {

            super( context, attrs, defStyleAttr );
      }

      @Override
      public void setLayoutParams ( ViewGroup.LayoutParams params ) {

            // 高度永远是包裹内容
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            super.setLayoutParams( params );
      }

      public void setHeight ( int normalHeight, int collapsingHeight ) {

            mNormalHeight = normalHeight;
            mCollapsingHeight = collapsingHeight;
            collapsing( mCurrentProcess );
      }

      public void setNormalHeight ( int normalHeight ) {

            mNormalHeight = normalHeight;
            collapsing( mCurrentProcess );
      }

      public void setCollapsingHeight ( int collapsingHeight ) {

            mCollapsingHeight = collapsingHeight;
            collapsing( mCurrentProcess );
      }

      public void collapsing ( float process ) {

            mCurrentProcess = process;
            getLayoutParams().height = (int) ( process * ( mCollapsingHeight - mNormalHeight ) + mNormalHeight );
            requestLayout();
      }
}
