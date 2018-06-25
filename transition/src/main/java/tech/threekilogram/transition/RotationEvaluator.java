package tech.threekilogram.transition;

import android.view.View;

/**
 * @author wuxio 2018-06-24:9:24
 */
public class RotationEvaluator implements Evaluator {

    private float mRotationBegin;
    private float mRotationEnd;

    private View mView;


    public RotationEvaluator(View view, float rotationEnd) {

        this.mRotationBegin = view.getRotation();
        this.mRotationEnd = rotationEnd;

        mView = view;
    }


    @Override
    public void setFraction(float fraction) {

        float current = mRotationBegin + (mRotationEnd - mRotationBegin) * fraction;
        mView.setRotation(current);
    }


    @Override
    public View getTarget() {

        return mView;
    }
}
