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

      /**
       * a scene as Begin Scene , {@link SceneManager} will change this to end scene
       */
      private ViewGroup mSceneToChange;

      /**
       * begin scene vision State
       */
      private ViewVisionState mBeginSceneVision;

      /**
       * end scene vision state
       */
      private ViewVisionState mEndSceneVision;

      /**
       * true : current scene is to end
       */
      private boolean isCurrentSceneEnd;

      /**
       * use this to change {@link #mSceneToChange} to end scene
       */
      private Animator mSceneAnimator;

      /**
       * use this to change child in {@link #mSceneToChange} to end scene state
       */
      private OnSceneUpdateListener mUpdateListener;

      /**
       * this list contains all {@link Evaluator} of child in both scene,use {@link Evaluator} to
       * changeView VisionState
       * <p>
       * use list because the order must not changed when animate
       */
      private ArrayList<Evaluator> mEvaluatorsOfViewInBoth;

      /**
       * @param sceneToChange this is beginScene, it's child could change visionState to visionState
       * defined by {@code layoutEndSceneID}
       * @param layoutEndSceneID end scene will inflate from this layout, end scene decide child in
       * begin scene's end vision state
       * <p>
       * note : {@code sceneToChange} must layout finished
       * <p>
       * note : the two scene must has same children, only children's visionState different and
       * scene size different
       */
      public SceneManager (ViewGroup sceneToChange, @LayoutRes int layoutEndSceneID) {

            mSceneToChange = sceneToChange;

            int left = sceneToChange.getLeft();
            int top = sceneToChange.getTop();
            int right = sceneToChange.getRight();
            int bottom = sceneToChange.getBottom();

            LayoutInflater inflater = LayoutInflater.from(mSceneToChange.getContext());
            ViewGroup sceneEnd = (ViewGroup) inflater.inflate(layoutEndSceneID, null);

            measureAndLayoutSceneFromInflate(sceneEnd, right - left, bottom - top);
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * @param sceneToChange this is beginScene, it's child could change visionState to visionState
       * defined by {@code layoutEndSceneID}
       * @param sceneEnd this is end scene , end scene decide child in begin scene's end vision
       * state
       * <p>
       * note : {@code sceneToChange} must layout finished
       * <p>
       * note : the two scene must has same children, only children's visionState different and
       * scene size different
       */
      public SceneManager (ViewGroup sceneToChange, ViewGroup sceneEnd) {

            mSceneToChange = sceneToChange;

            int left = sceneToChange.getLeft();
            int top = sceneToChange.getTop();
            int right = sceneToChange.getRight();
            int bottom = sceneToChange.getBottom();

            measureAndLayoutSceneFromInflate(sceneEnd, right - left, bottom - top);
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * @param sceneToChange this is beginScene, it's child could change visionState to visionState
       * defined by {@code layoutEndSceneID}
       * @param sceneEndLeft this decide beginScene's left when at endState
       * @param sceneEndTop this decide beginScene's top when at endState
       * @param sceneEndRight this decide beginScene's right when at endState
       * @param sceneEndBottom this decide beginScene's bottom when at endState
       * @param layoutEndSceneID end scene will inflate from this layout, end scene decide child in
       * begin scene's end vision state
       * <p>
       * note : {@code sceneToChange} must layout finished
       * <p>
       * note : the two scene must has same children, only children's visionState different and
       * scene size different
       */
      public SceneManager (
          final ViewGroup sceneToChange,
          int sceneEndLeft,
          int sceneEndTop,
          int sceneEndRight,
          int sceneEndBottom,
          @LayoutRes int layoutEndSceneID) {

            mSceneToChange = sceneToChange;

            LayoutInflater inflater = LayoutInflater.from(mSceneToChange.getContext());
            ViewGroup sceneEnd = (ViewGroup) inflater.inflate(layoutEndSceneID, null);

            measureAndLayoutSceneFromInflate(
                sceneEnd,
                sceneEndRight - sceneEndLeft,
                sceneEndBottom - sceneEndTop
            );
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * @param sceneToChange this is beginScene, it's child could change visionState to visionState
       * defined by {@code layoutEndSceneID}
       * @param sceneEndLeft this decide beginScene's left when at endState
       * @param sceneEndTop this decide beginScene's top when at endState
       * @param sceneEndRight this decide beginScene's right when at endState
       * @param sceneEndBottom this decide beginScene's bottom when at endState
       * @param sceneEnd end scene ； end scene decide child in begin scene's end vision state
       * <p>
       * note : {@code sceneToChange} must layout finished
       * <p>
       * note : the two scene must has same children, only children's visionState different and
       * scene size different
       */
      public SceneManager (
          final ViewGroup sceneToChange,
          int sceneEndLeft,
          int sceneEndTop,
          int sceneEndRight,
          int sceneEndBottom,
          ViewGroup sceneEnd) {

            mSceneToChange = sceneToChange;

            measureAndLayoutSceneFromInflate(
                sceneEnd,
                sceneEndRight - sceneEndLeft,
                sceneEndBottom - sceneEndTop
            );
            createChildrenEvaluator(sceneToChange, sceneEnd);

            mBeginSceneVision = new ViewVisionState(sceneToChange);
            mEndSceneVision = new ViewVisionState(sceneEnd);
      }

      /**
       * if end scene is inflate from layout, must measure and layout it, then could compare with
       * begin scene
       *
       * @param scene need measure and layout
       * @param width use this to measure scene
       * @param height use this to measure scene
       */
      private void measureAndLayoutSceneFromInflate (ViewGroup scene, int width, int height) {

            int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            scene.measure(widthSpec, heightSpec);

            scene.layout(0, 0, scene.getMeasuredWidth(), scene.getMeasuredHeight());
      }

      /**
       * compare children in two scene , then make Evaluator to run when animator running
       *
       * @param from from this scene to {@code to} scene
       * @param to decide view at {@code from} end visionState
       */
      @SuppressWarnings("UnnecessaryLocalVariable")
      private void createChildrenEvaluator (
          ViewGroup from,
          ViewGroup to) {

            int count = from.getChildCount();

            for(int i = 0; i < count; i++) {

                  View childAtBeginScene = from.getChildAt(i);
                  int childId = childAtBeginScene.getId();
                  View childAtEndScene = to.findViewById(childId);

                  if(childAtEndScene != null) {

                        TransitionEvaluator transitionEvaluator = new TransitionEvaluator(
                            childAtBeginScene,
                            childAtEndScene.getLeft(),
                            childAtEndScene.getTop(),
                            childAtEndScene.getRight(),
                            childAtEndScene.getBottom()
                        );

                        addEvaluatorOfChildToList(transitionEvaluator);

                        /* if childAtBeginScene is viewGroup compare it's children with child find from scene provideVisionState */

                        if(childAtEndScene instanceof ViewGroup
                            && childAtBeginScene instanceof ViewGroup) {

                              createChildrenEvaluator(
                                  (ViewGroup) childAtBeginScene,
                                  (ViewGroup) childAtEndScene
                              );
                        }

                        remeasure0SizeViewInBeginScene(childAtBeginScene, childAtEndScene);
                        /* remove the compared view to short find view time */
                        to.removeView(childAtEndScene);
                  }
            }
      }

      /**
       * if view in begin Scene is 0 size(width && height is 0),measure it with size defined at end
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
       * add the views evaluator to list
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
       * change scene with animator
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
       * change current scene to end with animator
       */
      public void changeSceneToEnd () {

            if(!isCurrentSceneEnd) {
                  createSceneEndAnimator().start();
            }
      }

      /**
       * change current scene to begin with animator
       */
      public void changeSceneToBegin () {

            if(isCurrentSceneEnd) {
                  createSceneBeginAnimator().start();
            }
      }

      /**
       * change current scene to end immediately, with out animator
       */
      public void setSceneToEnd () {

            if(!isCurrentSceneEnd) {
                  isCurrentSceneEnd = true;

                  /* change scene self */

                  TransitionEvaluator transitionEvaluator =
                      new TransitionEvaluator(mSceneToChange, mEndSceneVision);

                  transitionEvaluator.setFraction(1);

                  /* change children in beginScene */

                  notifyAllEvaluatorFractionUpdate(1);
            }
      }

      /**
       * change current scene to begin immediately, with out animator
       */
      public void setSceneToBegin () {

            if(isCurrentSceneEnd) {
                  isCurrentSceneEnd = false;

                  /* change scene self */

                  TransitionEvaluator transitionEvaluator =
                      new TransitionEvaluator(mSceneToChange, mBeginSceneVision);
                  transitionEvaluator.setFraction(1);

                  /* change children in beginScene */

                  notifyAllEvaluatorFractionUpdate(1);
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
       * use this to set children evaluator fraction,when Scene animator is running
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
