package tech.liujin.transition.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author Liujin 2019/5/16:17:11:17
 */
public class CollapsingLayout extends FrameLayout {

      private float mCurrentProcess;
      private int   mNormalSize;
      private int   mCollapsingSize;

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

      public void setSize ( int normalSize, int collapsingSize ) {

            mNormalSize = normalSize;
            mCollapsingSize = collapsingSize;
            collapsing( mCurrentProcess );
      }

      public void setNormalSize ( int normalSize ) {

            mNormalSize = normalSize;
            collapsing( mCurrentProcess );
      }

      public void setCollapsingSize ( int collapsingSize ) {

            mCollapsingSize = collapsingSize;
            collapsing( mCurrentProcess );
      }

      public void collapsing ( float process ) {

            mCurrentProcess = process;
            getLayoutParams().height = (int) ( process * ( mCollapsingSize - mNormalSize ) + mNormalSize );
            requestLayout();
      }
}
