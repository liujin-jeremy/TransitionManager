package tech.liujin.transition.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tech.liujin.transition.evaluator.Evaluator;
import tech.liujin.transition.evaluator.view.ViewEvaluator;
import tech.liujin.transition.evaluator.wrapper.WrapperEvaluator;

/**
 * @author Liujin 2019/5/16:8:55:23
 */
public class AnimatorFactory {

      public static Animator makeAnimator ( final Evaluator... evaluators ) {

            ArrayList<Evaluator> list = new ArrayList<>( evaluators.length );
            Collections.addAll( list, evaluators );
            return makeAnimator( list );
      }

      public static Animator makeAnimator ( final List<Evaluator> evaluators ) {

            ValueAnimator animator = ValueAnimator.ofFloat( 0, 1f );
            animator.addUpdateListener( new AnimatorUpdateListener() {

                  List<Evaluator> mEvaluators = evaluators;

                  @Override
                  public void onAnimationUpdate ( ValueAnimator animation ) {

                        float process = animation.getAnimatedFraction();
                        for( Evaluator evaluator : mEvaluators ) {
                              evaluator.evaluate( process );
                        }

                        if( process == 1 ) {
                              for( Evaluator evaluator : mEvaluators ) {
                                    if( evaluator instanceof ViewEvaluator ) {
                                          ViewEvaluator viewEvaluator = (ViewEvaluator) evaluator;
                                          viewEvaluator.justReversed( !viewEvaluator.isReversed() );
                                          continue;
                                    }
                                    if( evaluator instanceof WrapperEvaluator ) {
                                          ViewEvaluator viewEvaluator = ( (WrapperEvaluator) evaluator ).tryGetViewEvaluator();
                                          if( viewEvaluator != null ) {
                                                viewEvaluator.justReversed( !viewEvaluator.isReversed() );
                                          }
                                    }
                              }
                        }
                  }
            } );
            return animator;
      }

      public static Animator makeAnimator ( final Evaluator[] evaluators, final AnimatorUpdateListener listener ) {

            ArrayList<Evaluator> list = new ArrayList<>( evaluators.length );
            Collections.addAll( list, evaluators );
            return makeAnimator( list, listener );
      }

      public static Animator makeAnimator ( final List<Evaluator> evaluators, final AnimatorUpdateListener listener ) {

            ValueAnimator animator = ValueAnimator.ofFloat( 0, 1f );
            animator.addUpdateListener( new AnimatorUpdateListener() {

                  List<Evaluator> mEvaluators = evaluators;
                  private AnimatorUpdateListener mListener = listener;

                  @Override
                  public void onAnimationUpdate ( ValueAnimator animation ) {

                        if( mListener != null ) {
                              mListener.onAnimationUpdate( animation );
                        }

                        float process = animation.getAnimatedFraction();
                        for( Evaluator evaluator : mEvaluators ) {
                              evaluator.evaluate( process );
                        }

                        if( process == 1 ) {
                              for( Evaluator evaluator : mEvaluators ) {
                                    if( evaluator instanceof ViewEvaluator ) {
                                          ViewEvaluator viewEvaluator = (ViewEvaluator) evaluator;
                                          viewEvaluator.justReversed( !viewEvaluator.isReversed() );
                                          continue;
                                    }
                                    if( evaluator instanceof WrapperEvaluator ) {
                                          ViewEvaluator viewEvaluator = ( (WrapperEvaluator) evaluator ).tryGetViewEvaluator();
                                          if( viewEvaluator != null ) {
                                                viewEvaluator.justReversed( !viewEvaluator.isReversed() );
                                          }
                                    }
                              }
                        }
                  }
            } );
            return animator;
      }
}
