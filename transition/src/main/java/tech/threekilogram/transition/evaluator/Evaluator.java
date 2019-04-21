package tech.threekilogram.transition.evaluator;

import android.view.View;

/**
 * @author Liujin 2019/4/20:11:16:03
 */
public interface Evaluator {

      /**
       * 设置动画进度
       *
       * @param process 进度
       */
      void evaluate ( float process );

      /**
       * 获取被应用动画的对象
       *
       * @return 被应用动画的view
       */
      View getTarget ( );
}
