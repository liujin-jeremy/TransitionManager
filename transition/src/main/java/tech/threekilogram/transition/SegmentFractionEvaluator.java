package tech.threekilogram.transition;

import android.support.annotation.FloatRange;
import android.view.View;

/**
 * @author wuxio 2018-06-25:12:57
 */
public class SegmentFractionEvaluator implements Evaluator {

    private Evaluator mEvaluatorActual;

    private float mStartFraction;
    private float mEndFraction;


    public SegmentFractionEvaluator(
            Evaluator evaluatorActual,
            @FloatRange(from = 0, to = 1) float startFraction,
            @FloatRange(from = 0, to = 1) float endFraction) {

        mEvaluatorActual = evaluatorActual;
        mStartFraction = startFraction;
        mEndFraction = endFraction;
    }


    @Override
    public void setFraction(float fraction) {

        if (fraction < mStartFraction) {

            mEvaluatorActual.setFraction(0);

        } else if (fraction >= mStartFraction && fraction <= mEndFraction) {

            fraction = (fraction - mStartFraction) / (mEndFraction - mStartFraction);
            mEvaluatorActual.setFraction(fraction);

        } else {

            mEvaluatorActual.setFraction(1);
        }
    }


    @Override
    public View getTarget() {

        return mEvaluatorActual.getTarget();
    }
}
