package tech.liujin.transition.scene;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import java.util.ArrayList;
import tech.liujin.transition.ViewVisionState;
import tech.liujin.transition.evaluator.Evaluator;
import tech.liujin.transition.evaluator.view.ViewEvaluator;
import tech.liujin.transition.evaluator.view.VisionStateEvaluator;
import tech.liujin.transition.evaluator.wrapper.WrapperEvaluator;

/**
 * 根据view在不同布局中的显示状态（位置，角度，mAlpha）创建场景动画
 * <p>
 * create scene transition
 *
 * @author wuxio 2018-06-24:16:02
 */
public class SceneManager {

      /**
       * this list contains all {@link Evaluator} of child in both scene,use {@link Evaluator} to
       * changeView VisionState
       * <p>
       * use list because the order must not changed when animate
       */
      private ArrayList<Evaluator> mEvaluators = new ArrayList<>();

      /**
       * @param targetGroup this is beginScene, it's child could change visionState to visionState
       *     defined by {@code layoutEndSceneID}
       * @param layoutEndSceneID end scene will inflate from this layout, end scene decide child in
       *     begin scene's end vision state
       *     <p>
       *     note : {@code targetGroup} must layout finished
       *     <p>
       *     note : the two scene must has same children, only children's visionState different and
       *     scene size different
       */
      public SceneManager ( final ViewGroup targetGroup, @LayoutRes final int layoutEndSceneID ) {

            LayoutInflater inflater = LayoutInflater.from( targetGroup.getContext() );
            ViewGroup sceneEnd = (ViewGroup) inflater.inflate( layoutEndSceneID, null );
            init( targetGroup, sceneEnd );
      }

      /**
       * @param targetGroup this is beginScene, it's child could change visionState to visionState
       *     defined by {@code layoutEndSceneID}
       * @param sceneEnd this is end scene , end scene decide child in begin scene's end vision
       *     state
       *     <p>
       *     note : {@code targetGroup} must layout finished
       *     <p>
       *     note : the two scene must has same children, only children's visionState different and
       *     scene size different
       */
      public SceneManager ( final ViewGroup targetGroup, final ViewGroup sceneEnd ) {

            init( targetGroup, sceneEnd );
      }

      private void init ( final ViewGroup targetGroup, final ViewGroup sceneEnd ) {

            targetGroup.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        measureLayoutScene( sceneEnd, targetGroup.getWidth(), targetGroup.getHeight() );
                        createChildrenEvaluator( targetGroup, sceneEnd );
                  }
            } );
      }

      /**
       * if end scene is inflate from layout, must measure and layout it, then could compare with
       * begin scene
       *
       * @param scene need measure and layout
       * @param width use this to measure scene
       * @param height use this to measure scene
       */
      private void measureLayoutScene ( ViewGroup scene, int width, int height ) {

            int widthSpec = MeasureSpec.makeMeasureSpec( width, MeasureSpec.EXACTLY );
            int heightSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.EXACTLY );

            scene.measure( widthSpec, heightSpec );
            scene.layout( 0, 0, scene.getMeasuredWidth(), scene.getMeasuredHeight() );
      }

      /**
       * compare children in two scene , then make Evaluator end run when animator running
       *
       * @param start start this scene end {@code end} scene
       * @param end decide view at {@code start} end visionState
       */
      @SuppressWarnings("UnnecessaryLocalVariable")
      private void createChildrenEvaluator (
          ViewGroup start,
          ViewGroup end ) {

            int count = start.getChildCount();
            for( int i = 0; i < count; i++ ) {

                  View childAtStart = start.getChildAt( i );
                  int childId = childAtStart.getId();
                  View childAtEnd = end.findViewById( childId );

                  if( childAtEnd != null ) {

                        ViewVisionState childStartState = new ViewVisionState( childAtStart );
                        ViewVisionState childEndState = new ViewVisionState( childAtEnd );
                        VisionStateEvaluator evaluator = new VisionStateEvaluator( childAtStart, childStartState, childEndState );
                        mEvaluators.add( evaluator );

                        /* if childAtStart is viewGroup compare it's children with child find start scene provideVisionState */
                        if( childAtEnd instanceof ViewGroup && childAtStart instanceof ViewGroup ) {
                              createChildrenEvaluator( (ViewGroup) childAtStart, (ViewGroup) childAtEnd );
                        }

                        /* remove the compared view , short time */
                        end.removeView( childAtEnd );
                  }
            }
      }

      /**
       * call this will get the child evaluator
       *
       * @param childId child Id
       *
       * @return child evaluator
       */
      public Evaluator getChildEvaluator ( @IdRes int childId ) {

            for( Evaluator evaluator : mEvaluators ) {
                  if( evaluator.getTarget().getId() == childId ) {
                        return evaluator;
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
      public void updateChildEvaluator ( @IdRes int childId, Evaluator evaluator ) {

            ArrayList<Evaluator> evaluators = mEvaluators;
            if( evaluators != null ) {
                  int size = evaluators.size();
                  for( int i = 0; i < size; i++ ) {
                        Evaluator temp = evaluators.get( i );
                        if( temp.getTarget().getId() == childId ) {
                              evaluators.set( i, evaluator );
                        }
                  }
            }
      }

      /**
       * notify all children the animate process changed, evaluator need update
       *
       * @param process new process
       */
      public void evaluate ( float process ) {

            for( Evaluator evaluator : mEvaluators ) {
                  evaluator.evaluate( process );
            }
      }

      public void setReversed ( boolean reversed ) {

            for( Evaluator evaluator : mEvaluators ) {
                  if( evaluator instanceof ViewEvaluator ) {
                        ( (ViewEvaluator) evaluator ).setReversed( reversed );
                        continue;
                  }

                  if( evaluator instanceof WrapperEvaluator ) {
                        ViewEvaluator viewEvaluator = ( (WrapperEvaluator) evaluator ).tryGetViewEvaluator();
                        if( viewEvaluator != null ) {
                              viewEvaluator.setReversed( reversed );
                        }
                  }
            }
      }
}
