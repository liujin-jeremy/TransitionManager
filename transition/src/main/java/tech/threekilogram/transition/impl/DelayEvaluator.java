package tech.threekilogram.transition.impl;

import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import tech.threekilogram.transition.Evaluator;
import tech.threekilogram.transition.ViewVisionState;

/**
 * wrapper a {@link Evaluator} make him could set fraction with a delayed time
 *
 * @author wuxio 2018-06-25:11:14
 */
public class DelayEvaluator implements Evaluator {

      /**
       * 发送延时消息
       */
      private static HelperHandler sHandler = new HelperHandler();

      /**
       * 实际起作用的evaluator
       */
      private Evaluator       mEvaluatorActual;
      /**
       * 延时时间
       */
      private int             mDelayed;
      /**
       * 记录上一次显示状态
       */
      private ViewVisionState mViewVisionState;

      /**
       * 使用该 what 取消延时任务
       */
      int what = hashCode();

      public DelayEvaluator (@NonNull Evaluator evaluatorActual, int delayed) {

            mEvaluatorActual = evaluatorActual;
            setDelayed(delayed);

            View target = mEvaluatorActual.getTarget();
            mViewVisionState = new ViewVisionState(target);
      }

      public void setDelayed (int delayed) {

            if(delayed < 0) {
                  delayed = 0;
            }
            mDelayed = delayed;
      }

      /**
       * 取消延时设置进度
       */
      public void cancel () {

            sHandler.removeMessages(what);
      }

      @Override
      public void setFraction (float fraction) {

            /* set target location stable */

            View target = mEvaluatorActual.getTarget();
            mViewVisionState.applyTo(target);

            if(mDelayed <= 0) {

                  setFractionWhenReceiveMessage(fraction);
            } else {

                  sHandler.sendDelayMessage(this, mDelayed, fraction);
            }
      }

      /**
       * handler use this to set fraction when time up
       *
       * @param fraction fraction
       */
      private void setFractionWhenReceiveMessage (float fraction) {

            mEvaluatorActual.setFraction(fraction);

            View target = mEvaluatorActual.getTarget();
            mViewVisionState.update(target);
      }

      @Override
      public View getTarget () {

            return mEvaluatorActual.getTarget();
      }

      /**
       * send delayed message
       */
      private static class HelperHandler extends android.os.Handler {

            @Override
            public void handleMessage (Message msg) {

                  ((DelayEvaluator) msg.obj).setFractionWhenReceiveMessage(msg.arg1 * 1f / 1000);
            }

            public void sendDelayMessage (DelayEvaluator target, int delayed, float fraction) {

                  Message message = Message.obtain();

                  message.what = target.what;
                  message.obj = target;
                  message.arg1 = (int) (fraction * 1000);

                  sendMessageDelayed(message, delayed);
            }
      }
}
