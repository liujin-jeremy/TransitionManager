package tech.threekilogram.transition;

import android.view.View;

/**
 * @author wuxio 2018-06-23:12:17
 */
public class TranslateEvaluator implements Evaluator {

    private int mStartX;
    private int mStartY;

    private int mEndX;
    private int mEndY;

    private View mView;

    private int mTempX;
    private int mTempY;


    public TranslateEvaluator(View view, int startX, int startY, int endX, int endY) {

        mView = view;
        this.mStartX = startX;
        this.mStartY = startY;
        this.mEndX = endX;
        this.mEndY = endY;
    }


    @Override
    public void setFraction(float fraction) {

        int currentX = (int) (mStartX + (mEndX - mStartX) * fraction);
        int currentY = (int) (mStartY + (mEndY - mStartY) * fraction);

        int changedX = currentX - mTempX;
        int changedY = currentY - mTempY;

        mView.setX(mView.getX() + changedX);
        mView.setY(mView.getY() + changedY);

        mTempX = currentX;
        mTempY = currentY;
    }
}
