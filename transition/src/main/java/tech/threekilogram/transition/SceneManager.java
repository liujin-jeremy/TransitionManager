package tech.threekilogram.transition;

import android.animation.Animator;
import android.support.annotation.IdRes;
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

      private ViewGroup       mSceneToChange;
      private ViewVisionState mBeginSceneVision;
      private ViewVisionState mEndSceneVision;

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

      /**
       * true current scene is to end
       */
      private boolean isCurrentSceneEnd;

      private Animator              mSceneAnimator;
      private OnSceneUpdateListener mUpdateListener;

      /**
       * use list because the order must not changed when animate
       */
      private ArrayList<Evaluator> mEvaluatorsOfViewInBoth;

      /**
       * create a scene with a begin scene , you could use {@link #setSceneEndRect(int, int, int,
       * int)} set end scene layout rect,
       * <p>
       * note that the {@code sceneToChange} must layouted
       */
      public SceneManager (ViewGroup sceneToChange, @LayoutRes int layoutEndSceneID) {

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

            LayoutInflater inflater = LayoutInflater.from(mSceneToChange.getContext());
            ViewGroup sceneEnd = (ViewGroup) inflater.inflate(layoutEndSceneID, null);

            measureAndLayoutSceneFromInflate(sceneEnd, right - left, bottom - top);
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * create a scene with a begin scene , you could use {@link #setSceneEndRect(int, int, int,
       * int)} set end scene layout rect,
       * <p>
       * note that the {@code sceneToChange} must layouted
       */
      public SceneManager (ViewGroup sceneToChange, ViewGroup sceneEnd) {

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

            measureAndLayoutSceneFromInflate(sceneEnd, right - left, bottom - top);
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * create a scene with a begin scene,and set the end scene layout rect;
       * <p>
       * note that the {@code sceneToChange} must layouted
       */
      public SceneManager (
          final ViewGroup sceneToChange,
          int sceneEndLeft,
          int sceneEndTop,
          int sceneEndRight,
          int sceneEndBottom,
          @LayoutRes int layoutEndSceneID) {

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

            LayoutInflater inflater = LayoutInflater.from(mSceneToChange.getContext());
            ViewGroup sceneEnd = (ViewGroup) inflater.inflate(layoutEndSceneID, null);

            measureAndLayoutSceneFromInflate(
                sceneEnd,
                sceneEndRight - sceneEndLeft,
                sceneEndBottom - sceneEndLeft
            );
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * create a scene with a begin scene,and set the end scene layout rect;
       * <p>
       * note that the {@code sceneToChange} must layouted
       */
      public SceneManager (
          final ViewGroup sceneToChange,
          int sceneEndLeft,
          int sceneEndTop,
          int sceneEndRight,
          int sceneEndBottom,
          ViewGroup sceneEnd) {

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

            measureAndLayoutSceneFromInflate(
                sceneEnd,
                sceneEndRight - sceneEndLeft,
                sceneEndBottom - sceneEndLeft
            );
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * set the begin scene layout rect
       */
      private void setSceneBeginRect (int left, int top, int right, int bottom) {

            mSceneBeginLeft = left;
            mSceneBeginTop = top;
            mSceneBeginRight = right;
            mSceneBeginBottom = bottom;
      }

      /**
       * set the end scene layout rect
       */
      private void setSceneEndRect (int left, int top, int right, int bottom) {

            mSceneEndLeft = left;
            mSceneEndTop = top;
            mSceneEndRight = right;
            mSceneEndBottom = bottom;
      }

      private void measureAndLayoutSceneFromInflate (ViewGroup scene, int width, int height) {

            int widthSpec = MeasureSpec
                .makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec
                .makeMeasureSpec(height, MeasureSpec.EXACTLY);
            scene.measure(widthSpec, heightSpec);
            scene.layout(0, 0, scene.getMeasuredWidth(), scene.getMeasuredHeight());
      }

      /**
       * compare children in two scene ,make Evaluator to run when animator start
       *
       * @param from from this scene to {@code to} scene
       * @param to decide view at from end visionState
       */
      @SuppressWarnings("UnnecessaryLocalVariable")
      private void createChildrenEvaluator (
          ViewGroup from,
          ViewGroup to) {

            int count = from.getChildCount();

            for(int i = 0; i < count; i++) {

                  View beginChild = from.getChildAt(i);
                  int childId = beginChild.getId();
                  View childById = to.findViewById(childId);

                  if(childById != null) {

                        TransitionEvaluator transitionEvaluator = new TransitionEvaluator(
                            beginChild,
                            childById.getLeft(),
                            childById.getTop(),
                            childById.getRight(),
                            childById.getBottom()
                        );

                        remeasure0SizeViewInBeginScene(beginChild, childById);

                        addEvaluatorOfChildToList(transitionEvaluator);

                        /* if beginChild is viewGroup compare it's children with child find from scene provideVisionState */

                        if(childById instanceof ViewGroup && beginChild instanceof ViewGroup) {

                              createChildrenEvaluator(
                                  (ViewGroup) beginChild,
                                  (ViewGroup) childById
                              );
                        }

                        /* remove the compared view to short find view time */
                        to.removeView(childById);
                  }
            }
      }

      /**
       * if view in begin Scene is 0 size(width && height is 0),measure it with size defined in end
       * scene
       */
      private void remeasure0SizeViewInBeginScene (View beginChild, View childById) {

            if(beginChild.getMeasuredWidth() == 0 && beginChild.getMeasuredHeight() == 0) {

                  TransitionFactory.remeasureViewWithExactSpec(
                      beginChild,
                      childById.getRight() - childById.getLeft(),
                      childById.getBottom() - childById.getTop()
                  );
            }
      }

      /**
       * add the views evaluator
       */
      private void addEvaluatorOfChildToList (Evaluator evaluator) {

            if(mEvaluatorsOfViewInBoth == null) {

                  mEvaluatorsOfViewInBoth = new ArrayList<>();
            }

            mEvaluatorsOfViewInBoth.add(evaluator);
      }

      /**
       * call this will get the child evaluator
       *
       * @param childId child Id
       *
       * @return child evaluator
       */
      public Evaluator getChildEvaluator (@IdRes int childId) {

            ArrayList<Evaluator> evaluators = mEvaluatorsOfViewInBoth;

            if(evaluators != null) {

                  int size = evaluators.size();
                  for(int i = 0; i < size; i++) {

                        Evaluator evaluator = evaluators.get(i);

                        if(evaluator.getTarget().getId() == childId) {
                              return evaluator;
                        }
                  }
            }

            return null;
      }

      /**
       * call this will update the child evaluator
       *
       * @param childId child Id
       *
       * @return child evaluator
       */
      public void updateChildEvaluator (@IdRes int childId, Evaluator evaluator) {

            ArrayList<Evaluator> evaluators = mEvaluatorsOfViewInBoth;

            if(evaluators != null) {

                  int size = evaluators.size();
                  for(int i = 0; i < size; i++) {

                        Evaluator temp = evaluators.get(i);

                        if(temp.getTarget().getId() == childId) {

                              evaluators.set(i, evaluator);
                        }
                  }
            }
      }

      /**
       * notify all children the animate fraction changed, evaluator need update
       *
       * @param fraction new fraction
       */
      private void notifyAllEvaluatorFractionUpdate (float fraction) {

            ArrayList<Evaluator> temp = mEvaluatorsOfViewInBoth;

            if(temp != null) {

                  /* update all evaluator's fraction */

                  int size = temp.size();
                  for(int i = 0; i < size; i++) {

                        Evaluator evaluator = temp.get(i);

                        if(isCurrentSceneEnd) {

                              evaluator.setFraction(fraction);
                        } else {

                              evaluator.setFraction(1 - fraction);
                        }
                  }
            }
      }

      /**
       * change scene
       *
       * @return true : changed to end scene
       */
      public boolean changeScene () {

            if(isCurrentSceneEnd) {

                  createSceneBeginAnimator().start();
            } else {

                  createSceneEndAnimator().start();
            }

            return isCurrentSceneEnd;
      }

      /**
       * change current scene to end ,is not at end scene
       */
      public void changeSceneToEnd () {

            if(!isCurrentSceneEnd) {
                  createSceneEndAnimator().start();
            }
      }

      /**
       * change current scene to end ,is not at end scene
       */
      public void changeSceneToBegin () {

            if(isCurrentSceneEnd) {
                  createSceneBeginAnimator().start();
            }
      }

      /**
       * change current scene to end ,is not at end scene
       */
      public void setSceneToEnd () {

            if(!isCurrentSceneEnd) {
                  isCurrentSceneEnd = true;

                  TransitionEvaluator transitionEvaluator =
                      new TransitionEvaluator(mSceneToChange, mEndSceneVision);

                  transitionEvaluator.setFraction(1);

                  notifyAllEvaluatorFractionUpdate(1);
            }
      }

      /**
       * change current scene to end ,is not at end scene
       */
      public void setSceneToBegin () {

            if(!isCurrentSceneEnd) {
                  isCurrentSceneEnd = false;

                  TransitionEvaluator transitionEvaluator =
                      new TransitionEvaluator(mSceneToChange, mBeginSceneVision);

                  transitionEvaluator.setFraction(1);

                  notifyAllEvaluatorFractionUpdate(0);
            }
      }

      /**
       * @return true : current is in sceneEnd, false : current is in scene begin
       */
      public boolean isCurrentSceneEnd () {

            return isCurrentSceneEnd;
      }

      /**
       * get the animator that could change scene to end
       *
       * @return animator
       */
      public Animator createSceneEndAnimator () {

            isCurrentSceneEnd = true;

            if(mSceneAnimator != null && mSceneAnimator.isRunning()) {

                  mSceneAnimator.cancel();
            }

            if(mUpdateListener == null) {
                  mUpdateListener = new OnSceneUpdateListener();
            }

            mSceneAnimator = TransitionFactory.makeAnimator(
                mSceneToChange,
                mBeginSceneVision,
                mEndSceneVision,
                mUpdateListener
            );

            return mSceneAnimator;
      }

      /**
       * get the animator that could change scene to end
       *
       * @return animator
       */
      public Animator createSceneBeginAnimator () {

            isCurrentSceneEnd = false;

            if(mSceneAnimator != null && mSceneAnimator.isRunning()) {

                  mSceneAnimator.cancel();
            }

            if(mUpdateListener == null) {
                  mUpdateListener = new OnSceneUpdateListener();
            }

            mSceneAnimator = TransitionFactory.makeAnimator(
                mSceneToChange,
                mEndSceneVision,
                mBeginSceneVision,
                mUpdateListener
            );

            return mSceneAnimator;
      }

      /**
       * use this to set children evaluator fraction
       */
      private class OnSceneUpdateListener implements TransitionFactory.OnTransitionChangeListener {

            @Override
            public void onChange (
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
