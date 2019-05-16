package tech.liujin.transition;

import android.view.View;
import android.view.View.MeasureSpec;

/**
 * @author Liujin 2019/4/19:20:10:05
 */
public class MockMeasure {

      /**
       * 使用参数中的尺寸重新测量view
       *
       * @param view 需要测量的view
       * @param width 新的宽度
       * @param height 新的高度
       */
      public static void measureExactly ( View view, int width, int height ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( width, MeasureSpec.EXACTLY );
            int heightSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.EXACTLY );

            view.measure( widthSpec, heightSpec );
      }

      /**
       * 使用参数中的尺寸重新测量view
       *
       * @param view 需要测量的view
       * @param width 新的宽度
       * @param height 新的高度
       */
      public static void measureAtMost ( View view, int width, int height ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( width, MeasureSpec.AT_MOST );
            int heightSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.AT_MOST );
            view.measure( widthSpec, heightSpec );
      }

      public static int measureAtMostHeight ( View view ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( view.getMeasuredWidth(), MeasureSpec.EXACTLY );
            int heightSpec = MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE >> 1, MeasureSpec.AT_MOST );
            view.measure( widthSpec, heightSpec );
            return view.getMeasuredHeight();
      }

      public static int measureAtMostWidth ( View view ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE >> 1, MeasureSpec.AT_MOST );
            int heightSpec = MeasureSpec.makeMeasureSpec( view.getMeasuredHeight(), MeasureSpec.EXACTLY );
            view.measure( widthSpec, heightSpec );
            return view.getMeasuredWidth();
      }
}
