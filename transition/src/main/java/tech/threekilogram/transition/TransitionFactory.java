package tech.threekilogram.transition;


import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * @author wuxio 2018-06-21:10:27
 */
public class TransitionFactory {

    private static final String TAG = "TransitionFactory";

    /**
     * 通用的动画更新器
     */
    private static ViewLocationAnimatorUpdateListener mAnimatorUpdateListener =
            new ViewLocationAnimatorUpdateListener();


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view      需要变形的view
     * @param leftEnd   最终的left
     * @param topEnd    最终的top
     * @param rightEnd  最终的right
     * @param bottomEnd 最终的bottom
     * @return 动画
     */
    public static Animator makeChangeBounds(
            View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd) {

        return makeChangeBounds(view, leftEnd, topEnd, rightEnd, bottomEnd, null);
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view      需要变形的view
     * @param leftEnd   最终的left
     * @param topEnd    最终的top
     * @param rightEnd  最终的right
     * @param bottomEnd 最终的bottom
     * @param action    使用该action在变化过程中额外进行处理
     * @return 动画
     */
    public static Animator makeChangeBounds(
            View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd,
            TransitionAction action) {

        ViewLocation locationBegin = new ViewLocation(view);
        ViewLocation locationEnd = new ViewLocation(
                view,
                leftEnd,
                topEnd,
                rightEnd,
                bottomEnd,
                view.getRotation(),
                view.getAlpha()
        );

        if (action != null) {

            locationBegin.setActionInTransition(action);
        }

        return makeAnimator(locationBegin, locationEnd);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view        需要变化的view
     * @param locationEnd view最终显示状态
     * @return 动画
     */
    public static Animator makeChange(View view, ViewLocation locationEnd) {

        return makeChange(view, locationEnd, locationEnd.actionInTransition);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view        需要变化的view
     * @param locationEnd view最终显示状态
     * @param action      使用该action在变化过程中额外进行处理
     * @return 动画
     */
    public static Animator makeChange(View view, ViewLocation locationEnd, TransitionAction action) {

        ViewLocation locationBegin = new ViewLocation(view);

        if (action != null) {

            locationBegin.actionInTransition = locationEnd.actionInTransition;
        }

        return makeAnimator(locationBegin, locationEnd);
    }


    /**
     * 创建animator
     *
     * @param locationBegin 起始view位置
     * @param locationEnd   结束view位置
     * @return animator
     */
    private static Animator makeAnimator(
            ViewLocation locationBegin, ViewLocation locationEnd) {

        ValueAnimator animator = ValueAnimator.ofObject(
                new ViewLocationEvaluator(locationBegin),
                locationBegin,
                locationEnd
        );

        animator.addUpdateListener(mAnimatorUpdateListener);
        return animator;
    }


    /**
     * 创建animator
     *
     * @param locations 一组view的显示状态变化
     * @return animator 动画
     */
    public static Animator makeAnimator(ViewLocation... locations) {

        ValueAnimator animator = ValueAnimator.ofObject(
                new ViewLocationEvaluator(locations[0]),
                (Object[]) locations
        );

        animator.addUpdateListener(mAnimatorUpdateListener);
        return animator;
    }


    /**
     * 使用参数中的尺寸重新测量view
     *
     * @param view   需要测量的view
     * @param width  新的宽度
     * @param height 新的高度
     */
    public static void remeasureViewWithExactSpec(View view, int width, int height) {

        int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        view.measure(widthSpec, heightSpec);
    }


    /**
     * 使用参数中的尺寸重新测量view
     *
     * @param view   需要测量的view
     * @param width  新的宽度
     * @param height 新的高度
     */
    public static void remeasureViewWithAtMostSpec(View view, int width, int height) {

        int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        view.measure(widthSpec, heightSpec);
    }


    /**
     * 计算进度变化时,当前进度和上一个进度的差值,并返回给Animator,使用差值的原因:当view发生变化的同时可以使用其他的动画,
     * 如果使用中间值直接设置给view那么其他的动画会受到影响
     */
    private static class ViewLocationEvaluator implements TypeEvaluator< ViewLocation > {

        /**
         * 保存当前进度计算的结果
         */
        private ViewLocation result = new ViewLocation();
        /**
         * 保存上一个进度的计算结果
         */
        private ViewLocation temp   = new ViewLocation();


        public ViewLocationEvaluator(ViewLocation start) {

            temp.left = start.left;
            temp.top = start.top;
            temp.right = start.right;
            temp.bottom = start.bottom;

            temp.rotation = start.rotation;
            temp.alpha = start.alpha;

            result.view = start.view;
            result.actionInTransition = start.actionInTransition;
        }


        @Override
        public ViewLocation evaluate(float fraction, ViewLocation startValue, ViewLocation endValue) {

            /* 计算出当前的进度的值 */

            int left = (int) (startValue.left + (endValue.left - startValue.left) * fraction);
            int top = (int) (startValue.top + (endValue.top - startValue.top) * fraction);
            int right = (int) (startValue.right + (endValue.right - startValue.right) * fraction);
            int bottom = (int) (startValue.bottom + (endValue.bottom - startValue.bottom) * fraction);
            float rotation = startValue.rotation + (endValue.rotation - startValue.rotation) * fraction;
            float alpha = startValue.alpha + (endValue.alpha - startValue.alpha) * fraction;

            /* 计算当前进度和上一个进度的值之间的差值 */
            /* 使用差值移动:因为父布局移动时子布局同样会移动,使用差值,可以在父布局移动时修改子布局位置达到各自移动的效果 */

            result.left = left - temp.left;
            result.top = top - temp.top;
            result.right = right - temp.right;
            result.bottom = bottom - temp.bottom;
            result.rotation = rotation - temp.rotation;
            result.alpha = alpha - temp.alpha;

            /* 记录当前的进度值作为下一次的参照 */

            temp.left = left;
            temp.top = top;
            temp.right = right;
            temp.bottom = bottom;
            temp.rotation = rotation;
            temp.alpha = alpha;

            return result;
        }
    }

    /**
     * 动画执行器
     */
    private static class ViewLocationAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            ViewLocation value = (ViewLocation) animation.getAnimatedValue();
            float fraction = animation.getAnimatedFraction();
            value.layoutView(fraction);
        }
    }
}
