package tech.liujin.transition.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import tech.liujin.transition.evaluator.Evaluator;
import tech.liujin.transition.evaluator.view.ViewEvaluator;
import tech.liujin.transition.evaluator.wrapper.WrapperEvaluator;

/**
 * @author Liujin 2019/5/16:8:55:23
 */
public class AnimatorFactory {

      public static Animator makeAnimator ( final Evaluator... evaluators ) {

            ValueAnimator animator = ValueAnimator.ofFloat( 0, 1f );
            animator.addUpdateListener( new AnimatorUpdateListener() {

                  Evaluator[] mEvaluators = evaluators;

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

            ValueAnimator animator = ValueAnimator.ofFloat( 0, 1f );
            animator.addUpdateListener( new AnimatorUpdateListener() {

                  Evaluator[] mEvaluators = evaluators;
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
