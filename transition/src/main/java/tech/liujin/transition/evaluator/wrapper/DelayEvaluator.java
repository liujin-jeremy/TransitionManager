package tech.liujin.transition.evaluator.wrapper;

import android.os.Message;
import android.support.annotation.NonNull;
import tech.liujin.transition.evaluator.Evaluator;

/**
 * wrapper a {@link Evaluator} make him could set fraction with a delayed time
 *
 * @author wuxio 2018-06-25:11:14
 */
public class DelayEvaluator extends WrapperEvaluator {

      /**
       * 发送延时消息
       */
      private static HelperHandler sHandler = new HelperHandler();

      /**
       * 延时时间
       */
      private int mDelayed;

      /**
       * 使用该 what 取消延时任务
       */
      private int what;

      public DelayEvaluator ( @NonNull Evaluator evaluatorActual, int delayed ) {

            super( evaluatorActual );
            setDelayed( delayed );
            what = hashCode();
      }

      public void setDelayed ( int delayed ) {

            if( delayed < 0 ) {
                  delayed = 0;
            }
            mDelayed = delayed;
      }

      /**
       * 取消延时设置进度
       */
      public void cancel ( ) {

            sHandler.removeMessages( what );
      }

      @Override
      public void evaluate ( float process ) {

            if( mDelayed <= 0 ) {

                  setFractionWhenReceiveMessage( process );
            } else {

                  sHandler.sendDelayMessage( this, mDelayed, process );
            }
      }

      /**
       * handler use this to set fraction when time up
       *
       * @param fraction fraction
       */
      private void setFractionWhenReceiveMessage ( float fraction ) {

            mEvaluatorActual.evaluate( fraction );
      }

      @Override
      public Evaluator getActual ( ) {

            return mEvaluatorActual;
      }

      /**
       * send delayed message
       */
      private static class HelperHandler extends android.os.Handler {

            @Override
            public void handleMessage ( Message msg ) {

                  ( (DelayEvaluator) msg.obj ).setFractionWhenReceiveMessage( msg.arg1 * 1f / 1000 );
            }

            private void sendDelayMessage ( DelayEvaluator target, int delayed, float fraction ) {

                  Message message = Message.obtain();

                  message.what = target.what;
                  message.obj = target;
                  message.arg1 = (int) ( fraction * 1000 );

                  sendMessageDelayed( message, delayed );
            }
      }
}
