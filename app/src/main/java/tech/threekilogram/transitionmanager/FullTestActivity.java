package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import tech.threekilogram.transition.evaluator.view.AlphaEvaluator;
import tech.threekilogram.transition.evaluator.view.ColorEvaluator;
import tech.threekilogram.transition.evaluator.view.ColorEvaluator.ColorApply;
import tech.threekilogram.transition.evaluator.view.RotationEvaluator;
import tech.threekilogram.transition.evaluator.view.TransitionEvaluator;
import tech.threekilogram.transition.evaluator.view.TranslateEvaluator;

/**
 * @author wuxio
 */
public class FullTestActivity extends AppCompatActivity {

      private static final String TAG = "FullTestActivity";

      private TextView    mTestTransitionText;
      private TextView    mTestTranslateText;
      private TextView    mTestAlphaText;
      private TextView    mTestRotationText;
      private TextView    mTestColorText;
      private FrameLayout mRoot;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, FullTestActivity.class );
            context.startActivity( starter );
      }

      protected FrameLayout mTestFrame;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            super.setContentView( R.layout.activity_full_test );
            initView();

            EvaluatorFactory factory = new EvaluatorFactory();
      }

      private void initView ( ) {

            mRoot = (FrameLayout) findViewById( R.id.root );
            mTestFrame = findViewById( R.id.testFrame );
            mTestTransitionText = (TextView) findViewById( R.id.testTransitionText );
            mTestTranslateText = (TextView) findViewById( R.id.testTranslateText );
            mTestAlphaText = (TextView) findViewById( R.id.testAlphaText );
            mTestRotationText = (TextView) findViewById( R.id.testRotationText );
            mTestColorText = (TextView) findViewById( R.id.testColorText );
      }

      private class EvaluatorFactory {

            /**
             * 用于设置进度
             */
            private Animator mAnimator;
            private int      mWidth;
            private int      mHeight;

            private TransitionEvaluator mContainerEva;
            private TransitionEvaluator mTransitionEvaluator;
            private AlphaEvaluator      mAlphaEvaluator;
            private RotationEvaluator   mRotationEvaluator;
            private ColorEvaluator      mColorEvaluator;
            private TranslateEvaluator  mTranslateEvaluator;

            private EvaluatorFactory ( ) {

                  mTestFrame.post( new Runnable() {

                        @Override
                        public void run ( ) {

                              mWidth = mRoot.getWidth();
                              mHeight = mRoot.getHeight();

                              buildContainer();
                              buildTransition();
                              buildAlpha();
                              buildTranslate();
                              buildRotation();
                              buildColor();
                        }
                  } );

                  initAnimator();
                  setOnClick();
            }

            private void buildColor ( ) {

                  mColorEvaluator = new ColorEvaluator(
                      mTestColorText,
                      getResources().getColor( R.color.gold ),
                      Color.RED,
                      new ColorApply() {

                            @Override
                            public void onNewColorEvaluated ( View view, float process, int colorNew ) {

                                  view.setBackgroundColor( colorNew );
                            }
                      }
                  );
            }

            private void buildRotation ( ) {

                  mRotationEvaluator = new RotationEvaluator( mTestRotationText, 180 );
            }

            private void buildTranslate ( ) {

                  int width = mTestTranslateText.getWidth();
                  mTranslateEvaluator = new TranslateEvaluator( mTestTranslateText, mWidth - width, 0 );
            }

            private void buildAlpha ( ) {

                  mAlphaEvaluator = new AlphaEvaluator( mTestAlphaText, 0.1f );
            }

            private void buildTransition ( ) {

                  int targetWidth = (int) ( mTestTransitionText.getWidth() * 1.5f );
                  int targetHeight = (int) ( mTestTransitionText.getHeight() * 1.5f );
                  mTransitionEvaluator = new TransitionEvaluator(
                      mTestTransitionText,
                      mWidth - targetWidth,
                      100,
                      mWidth,
                      100 + targetHeight
                  );
            }

            private void buildContainer ( ) {

                  mContainerEva = new TransitionEvaluator(
                      mTestFrame,
                      0,
                      0,
                      mWidth,
                      mHeight
                  );
            }

            private void initAnimator ( ) {

                  mAnimator = ValueAnimator.ofFloat( 0, 1 );
                  ( (ValueAnimator) mAnimator ).addUpdateListener( new UpdateListener() );
                  mAnimator.setDuration( 1000 );
            }

            private void setOnClick ( ) {

                  mTestFrame.setOnClickListener( new OnClickListener() {

                        @Override
                        public void onClick ( View v ) {

                              if( mAnimator.isRunning() ) {
                                    return;
                              }
                              mAnimator.start();
                        }
                  } );
            }

            private class UpdateListener implements AnimatorUpdateListener {

                  @Override
                  public void onAnimationUpdate ( ValueAnimator animation ) {

                        float animatedFraction = animation.getAnimatedFraction();

                        mContainerEva.evaluate( animatedFraction );
                        mTransitionEvaluator.evaluate( animatedFraction );
                        mAlphaEvaluator.evaluate( animatedFraction );
                        mTranslateEvaluator.evaluate( animatedFraction );
                        mRotationEvaluator.evaluate( animatedFraction );
                        mColorEvaluator.evaluate( animatedFraction );

                        if( mContainerEva.getProcess() == 1 ) {
                              mContainerEva.justReversed( !mContainerEva.isReversed() );
                              mTransitionEvaluator.justReversed( !mTransitionEvaluator.isReversed() );
                              mTranslateEvaluator.justReversed( !mTranslateEvaluator.isReversed() );
                              mAlphaEvaluator.justReversed( !mAlphaEvaluator.isReversed() );
                              mRotationEvaluator.justReversed( !mRotationEvaluator.isReversed() );
                              mColorEvaluator.justReversed( !mColorEvaluator.isReversed() );
                        }
                  }
            }
      }
}
