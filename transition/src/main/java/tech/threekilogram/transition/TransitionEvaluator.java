package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-23:12:16
 */

/**
 * 使用该类制作静态变化效果,不能根据时间变化,只能根据进度变化
 */
public class TransitionEvaluator implements Evaluator {

    /**
     * 起始状态
     */
    private ViewVisionState begin;
    /**
     * 结束状态
     */
    private ViewVisionState end;
    /**
     * 用于重新布局view
     */
    private ViewRelayout mViewRelayout = new ViewRelayout();

    /**
     * 保存上一个进度的计算结果
     */
    private ViewVisionState mTemp = new ViewVisionState();


    TransitionEvaluator(ViewVisionState begin, ViewVisionState end) {

        this.begin = begin;
        this.end = end;
    }


    void evaluate(float fraction, ViewVisionState startValue, ViewVisionState endValue) {

        /* 计算出当前的进度的值 */

        int left = (int) (startValue.left + (endValue.left - startValue.left) * fraction);
        int top = (int) (startValue.top + (endValue.top - startValue.top) * fraction);
        int right = (int) (startValue.right + (endValue.right - startValue.right) * fraction);
        int bottom = (int) (startValue.bottom + (endValue.bottom - startValue.bottom) * fraction);
        float rotation = startValue.rotation + (endValue.rotation - startValue.rotation) * fraction;
        float alpha = startValue.alpha + (endValue.alpha - startValue.alpha) * fraction;

        /* 计算当前进度和上一个进度的值之间的差值 */
        /* 使用差值移动:因为父布局移动时子布局同样会移动,使用差值,可以在父布局移动时修改子布局位置达到各自移动的效果 */

        mViewRelayout.leftChanged = left - mTemp.left;
        mViewRelayout.topChanged = top - mTemp.top;
        mViewRelayout.rightChanged = right - mTemp.right;
        mViewRelayout.bottomChanged = bottom - mTemp.bottom;
        mViewRelayout.rotationChanged = rotation - mTemp.rotation;
        mViewRelayout.alphaChanged = alpha - mTemp.alpha;

        /* 记录当前的进度值作为下一次的参照 */

        mTemp.left = left;
        mTemp.top = top;
        mTemp.right = right;
        mTemp.bottom = bottom;
        mTemp.rotation = rotation;
        mTemp.alpha = alpha;
    }


    @Override
    public void setFraction(float fraction) {

        evaluate(fraction, begin, end);
        mViewRelayout.layoutView(fraction);
    }


    /**
     * @param remeasure true : 变化过程中将会重新测量
     */
    public void setRemeasureWhenLayout(boolean remeasure) {

        mViewRelayout.setRemeasureWhenLayoutView(remeasure);
    }
}
