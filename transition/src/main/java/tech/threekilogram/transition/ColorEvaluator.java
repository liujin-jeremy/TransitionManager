package tech.threekilogram.transition;

import android.support.annotation.ColorInt;
import android.support.graphics.drawable.ArgbEvaluator;

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


    public ColorEvaluator(ColorEvaluatorConstructor constructor, @ColorInt int endColor) {

        mConstructor = constructor;
        mStartColor = constructor.getStartColor();
        mEndColor = endColor;

        mArgbEvaluator = new ArgbEvaluator();
    }


    @Override
    public void setFraction(float fraction) {

        Integer currentColor = (Integer) mArgbEvaluator.evaluate(fraction, mStartColor, mEndColor);
        mConstructor.onNewColorEvaluated(fraction, currentColor);
    }


    public interface ColorEvaluatorConstructor {

        /**
         * 获取开始颜色
         *
         * @return start color
         */
        @ColorInt
        int getStartColor();

        /**
         * when new color evaluate this will call
         *
         * @param process  current process
         * @param colorNew new color at this process
         */
        void onNewColorEvaluated(float process, int colorNew);
    }
}
