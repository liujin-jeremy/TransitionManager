package tech.threekilogram.transition;

import android.view.View;

/**
 * @author wuxio 2018-06-24:9:24
 */
public class AlphaEvaluator implements Evaluator {

    private float mAlphaBegin;
    private float mAlphaEnd;

    private View mView;


    public AlphaEvaluator(View view, float alphaEnd) {

        this.mAlphaBegin = view.getAlpha();
        this.mAlphaEnd = alphaEnd;

        mView = view;
    }


    @Override
    public void setFraction(float fraction) {

        float currentAlpha = mAlphaBegin + (mAlphaEnd - mAlphaBegin) * fraction;
        mView.setAlpha(currentAlpha);
    }
}
