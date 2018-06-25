package tech.threekilogram.transition;

import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author wuxio 2018-06-25:11:14
 */
public class DelayEvaluator implements Evaluator {

    private static final String        TAG      = "DelayEvaluator";
    private static       HelperHandler sHandler = new HelperHandler();

    private Evaluator mEvaluatorActual;
    private int       mDelayed;
    private int       mLeft;
    private int       mTop;
    private int       mRight;
    private int       mBottom;


    public DelayEvaluator(@NonNull Evaluator evaluatorActual, @IntRange(from = 0) int delayed) {

        mEvaluatorActual = evaluatorActual;
        mDelayed = delayed;

        View target = mEvaluatorActual.getTarget();
        mLeft = target.getLeft();
        mTop = target.getTop();
        mRight = target.getRight();
        mBottom = target.getBottom();
    }


    @Override
    public void setFraction(float fraction) {

        sHandler.sendDelayMessage(this, mDelayed, fraction);

        View target = mEvaluatorActual.getTarget();
        target.layout(mLeft,mTop,mRight,mBottom);
    }


    private void setFractionWhenReceiveMessage(float fraction) {

        mEvaluatorActual.setFraction(fraction);

        View target = mEvaluatorActual.getTarget();
        mLeft = target.getLeft();
        mTop = target.getTop();
        mRight = target.getRight();
        mBottom = target.getBottom();
    }


    @Override
    public View getTarget() {

        return mEvaluatorActual.getTarget();
    }


    /**
     * send delayed message
     */
    private static class HelperHandler extends android.os.Handler {

        private final static int WHAT_DELAYED = 0b1001;


        @Override
        public void handleMessage(Message msg) {

            if (msg.what == WHAT_DELAYED) {

                ((DelayEvaluator) msg.obj).setFractionWhenReceiveMessage(msg.arg1 * 1f / 1000);
            }
        }


        public void sendDelayMessage(DelayEvaluator target, int delayed, float fraction) {

            Message message = Message.obtain();

            message.what = WHAT_DELAYED;
            message.obj = target;
            message.arg1 = (int) (fraction * 1000);

            sendMessageDelayed(message, delayed);
        }
    }
}
