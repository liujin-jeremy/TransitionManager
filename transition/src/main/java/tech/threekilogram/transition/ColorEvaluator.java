package tech.threekilogram.transition;

import android.support.annotation.ColorInt;
import android.support.graphics.drawable.ArgbEvaluator;
import android.view.View;

/**
 * @author wuxio 2018-06-24:17:21
 */
public class ColorEvaluator implements Evaluator {

    @ColorInt
    private int mStartColor;
    @ColorInt
    private int mEndColor;

    private ColorEvaluatorConstructor mConstructor;

    private ArgbEvaluator mArgbEvaluator;

    private View mView;


    public ColorEvaluator(View view, ColorEvaluatorConstructor constructor, @ColorInt int endColor) {

        mView = view;
        mConstructor = constructor;
        mStartColor = constructor.getStartColor(view);
        mEndColor = endColor;

        mArgbEvaluator = new ArgbEvaluator();
    }


    @Override
    public void setFraction(float fraction) {

        Integer currentColor = (Integer) mArgbEvaluator.evaluate(fraction, mStartColor, mEndColor);
        mConstructor.onNewColorEvaluated(mView, fraction, currentColor);
    }


    @Override
    public View getTarget() {

        return mView;
    }


    public interface ColorEvaluatorConstructor {

        /**
         * 获取开始颜色
         *
         * @param view target
         * @return start color
         */
        @ColorInt
        int getStartColor(View view);

        /**
         * when new color evaluate this will call
         *
         * @param view     view target
         * @param process  current process
         * @param colorNew new color at this process
         */
        void onNewColorEvaluated(View view, float process, int colorNew);
    }
}
