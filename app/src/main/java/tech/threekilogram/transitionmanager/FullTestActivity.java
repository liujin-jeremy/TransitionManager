package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import tech.threekilogram.transition.SceneManager.OnTransitionChangeListener;
import tech.threekilogram.transition.ViewVisionState;
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

      private TextView mTestTransitionText;
      private TextView mTestTranslateText;
      private TextView mTestAlphaText;
      private TextView mTestRotationText;
      private TextView mTestColorText;
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

            public EvaluatorFactory ( ) {

                  mTestFrame.post( new Runnable() {

                        @Override
                        public void run ( ) {

                              TransitionEvaluator containerEva = new TransitionEvaluator( mTestFrame, 0, 0, mRoot.getWidth(),
                                                                                          mRoot.getHeight()
                              );
                        }
                  } );
            }
      }

      /**
       * 点击执行动画,点击事件
       */
      private static class TestFrameTransition implements OnClickListener {

            /**
             * 记录位置信息
             */
            private ViewRect mViewRect = new ViewRect();

            /**
             * 是否已经展开
             */
            private boolean     isExpand;
            private Animator    mAnimator;
            private FrameLayout mFrameLayout;
            private TextView    mTestTransitionText;
            private TextView    mTestTranslateText;
            private TextView    mTestAlphaText;
            private TextView    mTestRotationText;
            private TextView    mTestColorText;

            /* 为子view执行变化 */

            private OnTransitionChangeListener mExpandListener;
            private OnTransitionChangeListener mCollapseListener;

            public TestFrameTransition ( FrameLayout frameLayout ) {

                  mFrameLayout = frameLayout;
                  mTestTransitionText = mFrameLayout.findViewById( R.id.testTransitionText );
                  mTestTranslateText = mFrameLayout.findViewById( R.id.testTranslateText );
                  mTestAlphaText = mFrameLayout.findViewById( R.id.testAlphaText );
                  mTestRotationText = mFrameLayout.findViewById( R.id.testRotationText );
                  mTestColorText = mFrameLayout.findViewById( R.id.testColorText );

                  mExpandListener = new ExpandTransitionListener();
                  mCollapseListener = new CollapseTransitionListener();
            }

            @Override
            public void onClick ( View v ) {


                  /* 先停止之前的动画,防止冲突 */

                  if( mAnimator != null && mAnimator.isRunning() ) {

                        mAnimator.cancel();
                  }

                  /* 记录位置信息 */
                  initViewRectIfNeed( mViewRect );

                  if( isExpand ) {

                        /* 折叠 */
                        collapse();
                  } else {

                        /* 展开 */
                        expand();
                  }

                  isExpand = !isExpand;
            }

            private void initViewRectIfNeed ( ViewRect rect ) {

                  if( rect.rootTopStart == 0 ) {
                        rect.setRootRect( mFrameLayout );
                        rect.setTestTextChangeBoundsRect( mTestTransitionText );
                  }
            }

            /**
             * 执行折叠动画
             */
            private void collapse ( ) {

            }

            /**
             * 执行展开动画
             */
            private void expand ( ) {

            }

            /**
             * 记录view位置
             */
            private class ViewRect {

                  /**
                   * 宽高
                   */
                  private int width;
                  private int height;

                  /**
                   * root 折叠top
                   */
                  private int rootTopStart;

                  /**
                   * testTextView 折叠位置
                   */
                  private int testTextLeftStart;
                  private int testTextTopStart;
                  private int testTextRightStart;
                  private int testTextBottomStart;

                  /**
                   * testTextView 展开位置
                   */
                  private int testTextLeftEnd;
                  private int testTextTopEnd;
                  private int testTextRightEnd;
                  private int testTextBottomEnd;

                  void setRootRect ( View root ) {

                        rootTopStart = root.getTop();

                        width = root.getRight();
                        height = root.getBottom();
                  }

                  void setTestTextChangeBoundsRect ( View testText ) {

                        testTextLeftStart = testText.getLeft();
                        testTextTopStart = testText.getTop();
                        testTextRightStart = testText.getRight();
                        testTextBottomStart = testText.getBottom();

                        testTextLeftEnd = width - testText.getWidth() - 200;
                        testTextTopEnd = 200;
                        testTextRightEnd = width;
                        testTextBottomEnd = 200 + testText.getHeight() + 200;
                  }
            }

            /**
             * 执行子view展开动画
             */
            private class ExpandTransitionListener implements OnTransitionChangeListener {

                  private TransitionEvaluator mTestTextEvaluator;
                  private TranslateEvaluator  mTestTransitionTranslateEvaluator;
                  private AlphaEvaluator      mTestAlphaEvaluator;
                  private RotationEvaluator   mTestRotationEvaluator;
                  private ColorEvaluator      mTestColorEvaluator;

                  @Override
                  public void onChange (
                      View view,
                      float fraction,
                      int left,
                      int top,
                      int right,
                      int bottom,
                      float rotation,
                      float alpha ) {

                        testChangeBounds( mViewRect, mTestTransitionText, fraction );
                        testTranslate( mTestTranslateText, fraction );
                        testAlpha( mTestAlphaText, fraction );
                        testRotation( mTestRotationText, fraction );
                        testColor( mTestColorText, fraction );
                  }

                  private void testColor ( TextView text, float fraction ) {

                        if( mTestColorEvaluator == null ) {

                              final int startColor = mTestColorText
                                  .getContext()
                                  .getResources()
                                  .getColor( R.color.gold );

                              final int endColor = mTestColorText
                                  .getContext()
                                  .getResources()
                                  .getColor( R.color.orangered );

                              mTestColorEvaluator = new ColorEvaluator(
                                  mTestColorText,
                                  startColor,
                                  endColor,
                                  new ColorApply() {

                                        @Override
                                        public void onNewColorEvaluated ( View view, float process, int colorNew ) {

                                              mTestColorText.setBackgroundColor( colorNew );
                                        }
                                  }
                              );
                        }

                        mTestColorEvaluator.evaluate( fraction );
                  }

                  private void testRotation ( TextView text, float fraction ) {

                        if( mTestRotationEvaluator == null ) {

                              mTestRotationEvaluator = new RotationEvaluator( text, 180 );
                        }

                        mTestRotationEvaluator.evaluate( fraction );
                  }

                  private void testAlpha ( TextView text, float fraction ) {

                        if( mTestAlphaEvaluator == null ) {

                              mTestAlphaEvaluator = new AlphaEvaluator( text, 0.5f );
                        }

                        mTestAlphaEvaluator.evaluate( fraction );
                  }

                  private void testTranslate ( TextView text, float fraction ) {

                        if( mTestTransitionTranslateEvaluator == null ) {

                              mTestTransitionTranslateEvaluator = new TranslateEvaluator(
                                  text,
                                  text.getX() + text.getWidth(),
                                  text.getY() + 800
                              );
                        }

                        mTestTransitionTranslateEvaluator.evaluate( fraction );
                  }

                  private void testChangeBounds ( ViewRect viewRect, View view, float fraction ) {

                        /* 位置大小变换 */

                        if( mTestTextEvaluator == null ) {

                              ViewVisionState end = new ViewVisionState(
                                  view,
                                  viewRect.testTextLeftEnd,
                                  viewRect.testTextTopEnd,
                                  viewRect.testTextRightEnd,
                                  viewRect.testTextBottomEnd
                              );

                              mTestTextEvaluator = new TransitionEvaluator( view, end );
                              mTestTextEvaluator.setRemeasureWhenFractionChanged( true );
                        }

                        mTestTextEvaluator.evaluate( fraction );
                  }
            }

            /**
             * 执行子view折叠动画
             */
            private class CollapseTransitionListener implements OnTransitionChangeListener {

                  private TransitionEvaluator mTestTextEvaluator;
                  private TranslateEvaluator  mTestTransitionTranslateEvaluator;
                  private AlphaEvaluator      mTestAlphaEvaluator;
                  private RotationEvaluator   mTestRotationEvaluator;
                  private ColorEvaluator      mTestColorEvaluator;

                  @Override
                  public void onChange (
                      View view,
                      float fraction,
                      int left,
                      int top,
                      int right,
                      int bottom,
                      float rotation,
                      float alpha ) {

                        testChangeBounds( mViewRect, mTestTransitionText, fraction );
                        testTranslate( mTestTranslateText, fraction );
                        testAlpha( mTestAlphaText, fraction );
                        testRotation( mTestRotationText, fraction );
                        testColor( mTestColorText, fraction );
                  }

                  private void testColor ( TextView text, float fraction ) {

                        if( mTestColorEvaluator == null ) {

                              final int endColor = mTestColorText
                                  .getContext()
                                  .getResources()
                                  .getColor( R.color.gold );

                              final int startColor = mTestColorText
                                  .getContext()
                                  .getResources()
                                  .getColor( R.color.orangered );

                              mTestColorEvaluator = new ColorEvaluator(
                                  mTestColorText,
                                  startColor,
                                  endColor,
                                  new ColorApply() {

                                        @Override
                                        public void onNewColorEvaluated ( View view, float process, int colorNew ) {

                                              mTestColorText.setBackgroundColor( colorNew );
                                        }
                                  }
                              );
                        }

                        mTestColorEvaluator.evaluate( fraction );
                  }

                  private void testRotation ( TextView text, float fraction ) {

                        if( mTestRotationEvaluator == null ) {

                              mTestRotationEvaluator = new RotationEvaluator( text, 0 );
                        }

                        mTestRotationEvaluator.evaluate( fraction );
                  }

                  private void testAlpha ( TextView text, float fraction ) {

                        if( mTestAlphaEvaluator == null ) {

                              mTestAlphaEvaluator = new AlphaEvaluator( text, 1f );
                        }

                        mTestAlphaEvaluator.evaluate( fraction );
                  }

                  private void testTranslate ( TextView text, float fraction ) {

                        if( mTestTransitionTranslateEvaluator == null ) {

                              mTestTransitionTranslateEvaluator = new TranslateEvaluator(
                                  text,
                                  text.getX() - text.getWidth(),
                                  text.getY() - 800
                              );
                        }

                        mTestTransitionTranslateEvaluator.evaluate( fraction );
                  }

                  private void testChangeBounds ( ViewRect viewRect, View view, float fraction ) {

                        if( mTestTextEvaluator == null ) {

                              ViewVisionState end = new ViewVisionState(
                                  view,
                                  viewRect.testTextLeftStart,
                                  viewRect.testTextTopStart,
                                  viewRect.testTextRightStart,
                                  viewRect.testTextBottomStart
                              );

                              mTestTextEvaluator = new TransitionEvaluator( view, end );
                              mTestTextEvaluator.setRemeasureWhenFractionChanged( true );
                        }

                        mTestTextEvaluator.evaluate( fraction );
                  }
            }
      }
}
