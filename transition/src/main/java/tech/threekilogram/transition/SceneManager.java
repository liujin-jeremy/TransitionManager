package tech.threekilogram.transition;

import android.animation.Animator;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * create scene transition
 *
 * @author wuxio 2018-06-24:16:02
 */
public class SceneManager {

    private static final String TAG = "SceneManager";

    private ViewGroup mSceneToChange;
    private ViewGroup mSceneEnd;

    /**
     * begin scene layout rect
     */
    private int mSceneBeginLeft;
    private int mSceneBeginTop;
    private int mSceneBeginRight;
    private int mSceneBeginBottom;

    /**
     * end scene layout rect
     */
    private int mSceneEndLeft;
    private int mSceneEndTop;
    private int mSceneEndRight;
    private int mSceneEndBottom;

    private boolean isCurrentSceneEnd;

    private Animator              mSceneAnimator;
    private OnSceneUpdateListener mUpdateListener;

    private ArrayList< Evaluator > mEvaluatorsOfViewInBoth;


    /**
     * create a scene with a begin scene , you could
     * use {@link #setSceneEndRect(int, int, int, int)} set end scene layout rect,
     *
     * note that the {@code sceneToChange} must layouted
     */
    public SceneManager(ViewGroup sceneToChange) {

        mSceneToChange = sceneToChange;

        int left = sceneToChange.getLeft();
        int top = sceneToChange.getTop();
        int right = sceneToChange.getRight();
        int bottom = sceneToChange.getBottom();

        setSceneBeginRect(
                left,
                top,
                right,
                bottom
        );

        setSceneEndRect(
                left,
                top,
                right,
                bottom
        );
    }


    /**
     * create a scene with a begin scene,and set the end scene layout rect;
     *
     * note that the {@code sceneToChange} must layouted
     */
    public SceneManager(final ViewGroup sceneToChange,
                        int sceneEndLeft,
                        int sceneEndTop,
                        int sceneEndRight,
                        int sceneEndBottom) {

        mSceneToChange = sceneToChange;

        setSceneBeginRect(
                sceneToChange.getLeft(),
                sceneToChange.getTop(),
                sceneToChange.getRight(),
                sceneToChange.getBottom()
        );

        setSceneEndRect(
                sceneEndLeft,
                sceneEndTop,
                sceneEndRight,
                sceneEndBottom
        );
    }


    private void setSceneBeginRect(int left, int top, int right, int bottom) {

        mSceneBeginLeft = left;
        mSceneBeginTop = top;
        mSceneBeginRight = right;
        mSceneBeginBottom = bottom;
    }


    /**
     * set the end scene layout rect
     */
    private void setSceneEndRect(int left, int top, int right, int bottom) {

        mSceneEndLeft = left;
        mSceneEndTop = top;
        mSceneEndRight = right;
        mSceneEndBottom = bottom;
    }


    private void addEvaluatorOfViewInBoth(Evaluator evaluator) {

        if (mEvaluatorsOfViewInBoth == null) {

            mEvaluatorsOfViewInBoth = new ArrayList<>();
        }

        mEvaluatorsOfViewInBoth.add(evaluator);
    }


    /**
     * set the layoutID as the end Scene
     *
     * @param layoutEndSceneID end scene layoutID
     */
    public void setEndScene(@LayoutRes int layoutEndSceneID) {

        LayoutInflater inflater = LayoutInflater.from(mSceneToChange.getContext());
        mSceneEnd = (ViewGroup) inflater.inflate(layoutEndSceneID, null);

        int widthSpec = MeasureSpec.makeMeasureSpec(mSceneEndRight - mSceneEndLeft, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(mSceneEndBottom - mSceneEndTop, MeasureSpec.EXACTLY);
        mSceneEnd.measure(widthSpec, heightSpec);
        mSceneEnd.layout(0, 0, mSceneEnd.getMeasuredWidth(), mSceneEnd.getMeasuredHeight());

        createChildrenEvaluator(mSceneToChange, mSceneEnd);
    }


    /**
     * compare children in two scene ,make Evaluator to run when animator start
     *
     * @param begin start scene
     * @param end   end scene
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private void createChildrenEvaluator(
            ViewGroup begin,
            ViewGroup end) {

        int count = begin.getChildCount();

        for (int i = 0; i < count; i++) {

            View beginChild = begin.getChildAt(i);
            int childId = beginChild.getId();
            View childById = end.findViewById(childId);

            if (childById != null) {

                TransitionEvaluator transitionEvaluator = new TransitionEvaluator(
                        beginChild,
                        childById.getLeft(),
                        childById.getTop(),
                        childById.getRight(),
                        childById.getBottom()
                );

                addEvaluatorOfViewInBoth(transitionEvaluator);

                /* what if beginChild is viewGroup */

                if (childById instanceof ViewGroup && beginChild instanceof ViewGroup) {

                    createChildrenEvaluator((ViewGroup) beginChild, (ViewGroup) childById);
                }

                end.removeView(childById);

            } else {

                /* what is sceneEnd without this id view */

            }
        }

    }


    @Deprecated
    private void addExtraViews(ViewGroup begin, ViewGroup end) {

        while (end.getChildCount() > 0) {

            /* get the views those in end scene but not in begin scene */
            View child = end.getChildAt(0);

            /* create a evaluator to use when change scene */
            addEvaluatorOfViewInBoth(generateDefaultEndSceneExtraViewEvaluator(child));

            /* remove child then add to scene begin */
            end.removeViewAt(0);
            /* use the size child measured to avoid remeasure when add to scene begin */
            begin.addView(child, child.getMeasuredWidth(), child.getMeasuredHeight());

            /* layout as a point ,make him not visible */
            child.layout(0, 0, 0, 0);
        }
    }


    /**
     * when end scene has some views that begin scene did not have, manager will remove those extra views
     * and add them to scene Begin,
     * and set them a default transition : from 0 size to size at scene end
     */
    @Deprecated
    @SuppressWarnings("UnnecessaryLocalVariable")
    private Evaluator generateDefaultEndSceneExtraViewEvaluator(View extraViewInEndScene) {

        /* end vision state defined by scene end */

        int left = extraViewInEndScene.getLeft();
        int top = extraViewInEndScene.getTop();
        int right = extraViewInEndScene.getRight();
        int bottom = extraViewInEndScene.getBottom();

        ViewVisionState stateEnd = new ViewVisionState(
                extraViewInEndScene,
                left,
                top,
                right,
                bottom
        );

        /* begin vision state : from center of view change to size */

        int beginLeft = left + (right - left) / 2;
        int beginTop = top + (bottom - top) / 2;
        int beginRight = beginLeft;
        int beginBottom = beginTop;

        ViewVisionState stateBegin = new ViewVisionState(
                beginLeft,
                beginTop,
                beginRight,
                beginBottom,
                extraViewInEndScene.getRotation(),
                0
        );

        return new TransitionEvaluator(extraViewInEndScene, stateBegin, stateEnd);
    }


    /**
     * notify all children the animate fraction changed, evaluator need update
     *
     * @param fraction new fraction
     */
    private void notifyAllEvaluatorFractionUpdate(float fraction) {

        ArrayList< Evaluator > temp = mEvaluatorsOfViewInBoth;

        if (temp != null) {

            /* update all evaluator's fraction */

            int size = temp.size();
            for (int i = 0; i < size; i++) {

                Evaluator evaluator = temp.get(i);

                if (isCurrentSceneEnd) {

                    evaluator.setFraction(fraction);
                } else {

                    evaluator.setFraction(1 - fraction);
                }
            }
        }
    }


    /**
     * @return true : current is in sceneEnd, false : current is in scene begin
     */
    public boolean isCurrentSceneEnd() {

        return isCurrentSceneEnd;
    }


    /**
     * get the animator that could change scene to end
     *
     * @return animator
     */
    public Animator createSceneEndAnimator() {

        isCurrentSceneEnd = true;

        if (mSceneAnimator != null && mSceneAnimator.isRunning()) {

            mSceneAnimator.cancel();
        }

        if (mUpdateListener == null) {
            mUpdateListener = new OnSceneUpdateListener();
        }

        mSceneAnimator = TransitionFactory.makeChangeBoundsAnimator(
                mSceneToChange,
                mSceneEndLeft,
                mSceneEndTop,
                mSceneEndRight,
                mSceneEndBottom,
                mUpdateListener
        );

        return mSceneAnimator;
    }


    /**
     * get the animator that could change scene to end
     *
     * @return animator
     */
    public Animator createSceneBeginAnimator() {

        isCurrentSceneEnd = false;

        if (mSceneAnimator != null && mSceneAnimator.isRunning()) {

            mSceneAnimator.cancel();
        }

        if (mUpdateListener == null) {
            mUpdateListener = new OnSceneUpdateListener();
        }

        mSceneAnimator = TransitionFactory.makeChangeBoundsAnimator(
                mSceneToChange,
                mSceneBeginLeft,
                mSceneBeginTop,
                mSceneBeginRight,
                mSceneBeginBottom,
                mUpdateListener
        );

        return mSceneAnimator;
    }


    /**
     * use this to set children evaluator fraction
     */
    private class OnSceneUpdateListener implements TransitionFactory.OnTransitionChangeListener {

        @Override
        public void onChange(
                View view,
                float fraction,
                int left,
                int top,
                int right,
                int bottom,
                float rotation,
                float alpha) {

            notifyAllEvaluatorFractionUpdate(fraction);
        }
    }
}
