package tech.threekilogram.transition;

import android.view.View;
import android.view.View.MeasureSpec;

/**
 * @author Liujin 2019/4/19:20:10:05
 */
public class Measure {

      /**
       * 使用参数中的尺寸重新测量view
       *
       * @param view 需要测量的view
       * @param width 新的宽度
       * @param height 新的高度
       */
      public static void remeasureViewWithExactSpec ( View view, int width, int height ) {

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
      public static void remeasureViewWithAtMostSpec ( View view, int width, int height ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( width, MeasureSpec.AT_MOST );
            int heightSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.AT_MOST );
            view.measure( widthSpec, heightSpec );
      }
}
