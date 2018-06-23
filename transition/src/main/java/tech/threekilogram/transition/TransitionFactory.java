package tech.threekilogram.transition;


import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
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
     * @param view    需要变形的view
     * @param rectEnd 最终的显示区域
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            Rect rectEnd) {

        return makeChangeBoundsAnimator(
                view,
                rectEnd.left,
                rectEnd.top,
                rectEnd.right,
                rectEnd.bottom,
                null
        );
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view     需要变形的view
     * @param rectEnd  最终的显示区域
     * @param listener 变化过程监听
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            Rect rectEnd,
            OnTransitionChangeListener listener) {

        return makeChangeBoundsAnimator(
                view,
                rectEnd.left,
                rectEnd.top,
                rectEnd.right,
                rectEnd.bottom,
                listener
        );
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view          需要变形的view
     * @param rectEnd       最终的显示区域
     * @param remeasureView 是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            Rect rectEnd,
            boolean remeasureView) {

        return makeChangeBoundsAnimator(
                view,
                rectEnd.left,
                rectEnd.top,
                rectEnd.right,
                rectEnd.bottom,
                null,
                remeasureView
        );
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view          需要变形的view
     * @param rectEnd       最终的显示区域
     * @param listener      变化过程监听
     * @param remeasureView 是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            Rect rectEnd,
            OnTransitionChangeListener listener,
            boolean remeasureView) {

        return makeChangeBoundsAnimator(
                view,
                rectEnd.left,
                rectEnd.top,
                rectEnd.right,
                rectEnd.bottom,
                listener,
                remeasureView
        );
    }


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
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd) {

        return makeChangeBoundsAnimator(
                view,
                leftEnd,
                topEnd,
                rightEnd,
                bottomEnd,
                null
        );
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view          需要变形的view
     * @param leftEnd       最终的left
     * @param topEnd        最终的top
     * @param rightEnd      最终的right
     * @param bottomEnd     最终的bottom
     * @param remeasureView 是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd,
            boolean remeasureView) {

        return makeChangeBoundsAnimator(
                view,
                leftEnd,
                topEnd,
                rightEnd,
                bottomEnd,
                null,
                remeasureView
        );
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view      需要变形的view
     * @param leftEnd   最终的left
     * @param topEnd    最终的top
     * @param rightEnd  最终的right
     * @param bottomEnd 最终的bottom
     * @param listener  使用该action在变化过程中额外进行处理
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd,
            OnTransitionChangeListener listener) {

        ViewVisionState locationBegin = new ViewVisionState(view);

        ViewVisionState locationEnd = new ViewVisionState(
                leftEnd,
                topEnd,
                rightEnd,
                bottomEnd,
                view.getRotation(),
                view.getAlpha()
        );

        return makeAnimator(view, locationBegin, locationEnd, listener);
    }


    /**
     * 制作一个动画将view变形到到指定区域
     *
     * @param view          需要变形的view
     * @param leftEnd       最终的left
     * @param topEnd        最终的top
     * @param rightEnd      最终的right
     * @param bottomEnd     最终的bottom
     * @param listener      使用该action在变化过程中额外进行处理
     * @param remeasureView 是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeBoundsAnimator(
            @NonNull View view,
            int leftEnd,
            int topEnd,
            int rightEnd,
            int bottomEnd,
            OnTransitionChangeListener listener,
            boolean remeasureView) {

        ViewVisionState locationBegin = new ViewVisionState(view);

        ViewVisionState locationEnd = new ViewVisionState(
                leftEnd,
                topEnd,
                rightEnd,
                bottomEnd,
                view.getRotation(),
                view.getAlpha()
        );

        return makeAnimator(view, locationBegin, locationEnd, listener, remeasureView);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view           需要变化的view
     * @param visionStateEnd view最终显示状态
     * @return 动画
     */
    public static Animator makeChangeAnimator(
            @NonNull View view,
            ViewVisionState visionStateEnd) {

        return makeChangeAnimator(view, visionStateEnd, null);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view           需要变化的view
     * @param visionStateEnd view最终显示状态
     * @param remeasureView  是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeAnimator(
            @NonNull View view,
            ViewVisionState visionStateEnd,
            boolean remeasureView) {

        return makeChangeAnimator(view, visionStateEnd, null, remeasureView);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view           需要变化的view
     * @param visionStateEnd view最终显示状态
     * @param listener       使用该action在变化过程中额外进行处理
     * @return 动画
     */
    public static Animator makeChangeAnimator(
            @NonNull View view,
            ViewVisionState visionStateEnd,
            OnTransitionChangeListener listener) {

        ViewVisionState locationBegin = new ViewVisionState(view);

        return makeAnimator(view, locationBegin, visionStateEnd, listener);
    }


    /**
     * 制作一个将 view 变化为指定状态的动画
     *
     * @param view           需要变化的view
     * @param visionStateEnd view最终显示状态
     * @param listener       使用该action在变化过程中额外进行处理
     * @param remeasureView  是否在变化过程中重新测量
     * @return 动画
     */
    public static Animator makeChangeAnimator(
            @NonNull View view,
            ViewVisionState visionStateEnd,
            OnTransitionChangeListener listener,
            boolean remeasureView) {

        ViewVisionState locationBegin = new ViewVisionState(view);

        return makeAnimator(view, locationBegin, visionStateEnd, listener, remeasureView);
    }


    /**
     * 创建animator
     *
     * @param view             需要变化的view
     * @param visionStateBegin 起始view位置
     * @param visionStateEnd   结束view位置
     * @return animator 动画
     */
    public static Animator makeAnimator(
            @NonNull View view,
            ViewVisionState visionStateBegin,
            ViewVisionState visionStateEnd) {

        return makeAnimator(view, visionStateBegin, visionStateEnd, null);
    }


    /**
     * 创建animator
     *
     * @param view             需要变化的view
     * @param visionStateBegin 起始view位置
     * @param visionStateEnd   结束view位置
     * @param remeasureView    是否在变化过程中重新测量
     * @return animator 动画
     */
    public static Animator makeAnimator(
            @NonNull View view,
            ViewVisionState visionStateBegin,
            ViewVisionState visionStateEnd,
            boolean remeasureView) {

        return makeAnimator(view, visionStateBegin, visionStateEnd, null, remeasureView);
    }


    /**
     * 创建animator
     *
     * @param view             需要变化的view
     * @param visionStateBegin 起始view位置
     * @param visionStateEnd   结束view位置
     * @param listener         在变化过程中额外进行处理
     * @return animator 动画
     */
    public static Animator makeAnimator(
            @NonNull View view,
            ViewVisionState visionStateBegin,
            ViewVisionState visionStateEnd,
            OnTransitionChangeListener listener) {

        return makeAnimator(
                view,
                visionStateBegin,
                visionStateEnd,
                listener,
                false
        );
    }


    /**
     * 创建animator
     *
     * @param view             需要变化的view
     * @param visionStateBegin 起始view位置
     * @param visionStateEnd   结束view位置
     * @param listener         在变化过程中额外进行处理
     * @param remeasureView    在变化过程中是否重新测量,true 重新测量
     * @return animator 动画
     */
    public static Animator makeAnimator(
            @NonNull View view,
            ViewVisionState visionStateBegin,
            ViewVisionState visionStateEnd,
            OnTransitionChangeListener listener,
            boolean remeasureView) {

        ValueAnimator animator = ValueAnimator.ofObject(
                new ViewLocationEvaluator(view, visionStateBegin, listener),
                visionStateBegin,
                visionStateEnd,
                remeasureView
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
    private static class ViewLocationEvaluator implements TypeEvaluator< ViewVisionState > {

        /**
         * 用于重新布局view
         */
        private ViewRelayout mViewRelayout = new ViewRelayout();

        /**
         * 保存上一个进度的计算结果
         */
        private ViewVisionStateResult mTemp = new ViewVisionStateResult(mViewRelayout);


        private ViewLocationEvaluator(
                @NonNull View view,
                ViewVisionState start,
                OnTransitionChangeListener listener) {

            this(view, start, listener, false);
        }


        private ViewLocationEvaluator(
                @NonNull View view,
                ViewVisionState start,
                OnTransitionChangeListener listener,
                boolean remeasureView) {

            mTemp.left = start.left;
            mTemp.top = start.top;
            mTemp.right = start.right;
            mTemp.bottom = start.bottom;
            mTemp.rotation = start.rotation;
            mTemp.alpha = start.alpha;

            mViewRelayout.view = view;
            mViewRelayout.onTransitionChangeListener = listener;

            mViewRelayout.setRemeasureWhenLayoutView(remeasureView);
        }


        @Override
        public ViewVisionState evaluate(
                float fraction,
                ViewVisionState startValue,
                ViewVisionState endValue) {

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

            return mTemp;
        }
    }

    /**
     * 动画执行器
     */
    private static class ViewLocationAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            ViewVisionStateResult value = (ViewVisionStateResult) animation.getAnimatedValue();
            float fraction = animation.getAnimatedFraction();
            value.viewRelayout.layoutView(fraction);
        }
    }

    /**
     * 使用该类制作静态变化效果,不能根据时间变化,只能根据进度变化
     */
    public static class TransitionEvaluator {

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
}
