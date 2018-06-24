package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import tech.threekilogram.transition.AlphaEvaluator;
import tech.threekilogram.transition.ColorEvaluator;
import tech.threekilogram.transition.RotationEvaluator;
import tech.threekilogram.transition.TransitionEvaluator;
import tech.threekilogram.transition.TransitionFactory;
import tech.threekilogram.transition.TranslateEvaluator;
import tech.threekilogram.transition.ViewVisionState;

/**
 * @author wuxio
 */
public class ExampleActivity extends AppCompatActivity {

    private static final String TAG = "ExampleActivity";


    public static void start(Context context) {

        Intent starter = new Intent(context, ExampleActivity.class);
        context.startActivity(starter);
    }


    protected FrameLayout mTestFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_example);
        initView();
    }


    private void initView() {

        mTestFrame = findViewById(R.id.testFrame);
        mTestFrame.setOnClickListener(new TestFrameTransition(mTestFrame));
    }


    /**
     * 点击执行动画,点击事件
     */
    private static class TestFrameTransition implements View.OnClickListener {


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

        private TransitionFactory.OnTransitionChangeListener mExpandListener;
        private TransitionFactory.OnTransitionChangeListener mCollapseListener;


        public TestFrameTransition(FrameLayout frameLayout) {

            mFrameLayout = frameLayout;
            mTestTransitionText = mFrameLayout.findViewById(R.id.testTransitionText);
            mTestTranslateText = mFrameLayout.findViewById(R.id.testTranslateText);
            mTestAlphaText = mFrameLayout.findViewById(R.id.testAlphaText);
            mTestRotationText = mFrameLayout.findViewById(R.id.testRotationText);
            mTestColorText = mFrameLayout.findViewById(R.id.testColorText);

            mExpandListener = new ExpandTransitionListener();
            mCollapseListener = new CollapseTransitionListener();
        }


        @Override
        public void onClick(View v) {


            /* 先停止之前的动画,防止冲突 */

            if (mAnimator != null && mAnimator.isRunning()) {

                mAnimator.cancel();
            }

            /* 记录位置信息 */
            initViewRectIfNeed(mViewRect);

            if (isExpand) {

                /* 折叠 */
                collapse();
            } else {

                /* 展开 */
                expand();
            }

            isExpand = !isExpand;
        }


        private void initViewRectIfNeed(ViewRect rect) {

            if (rect.rootTopStart == 0) {
                rect.setRootRect(mFrameLayout);
                rect.setTestTextChangeBoundsRect(mTestTransitionText);
            }
        }


        /**
         * 执行折叠动画
         */
        private void collapse() {

            mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                    mFrameLayout,
                    0,
                    mViewRect.rootTopStart,
                    mFrameLayout.getRight(),
                    mFrameLayout.getBottom(),
                    mCollapseListener
            );

            mAnimator.start();

        }


        /**
         * 执行展开动画
         */
        private void expand() {

            mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                    mFrameLayout,
                    0,
                    0,
                    mFrameLayout.getRight(),
                    mFrameLayout.getBottom(),
                    mExpandListener
            );

            mAnimator.start();
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


            void setRootRect(View root) {

                rootTopStart = root.getTop();

                width = root.getRight();
                height = root.getBottom();
            }


            void setTestTextChangeBoundsRect(View testText) {

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
        private class ExpandTransitionListener implements TransitionFactory.OnTransitionChangeListener {

            private TransitionEvaluator mTestTextEvaluator;
            private TranslateEvaluator  mTestTransitionTranslateEvaluator;
            private AlphaEvaluator      mTestAlphaEvaluator;
            private RotationEvaluator   mTestRotationEvaluator;
            private ColorEvaluator      mTestColorEvaluator;


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

                testChangeBounds(mViewRect, mTestTransitionText, fraction);
                testTranslate(mTestTranslateText, fraction);
                testAlpha(mTestAlphaText, fraction);
                testRotation(mTestRotationText, fraction);
                testColor(mTestColorText, fraction);
            }


            private void testColor(TextView text, float fraction) {

                if (mTestColorEvaluator == null) {

                    final int startColor = mTestColorText
                            .getContext()
                            .getResources()
                            .getColor(R.color.gold);

                    final int endColor = mTestColorText
                            .getContext()
                            .getResources()
                            .getColor(R.color.orangered);

                    mTestColorEvaluator = new ColorEvaluator(new ColorEvaluator.ColorEvaluatorConstructor() {
                        @Override
                        public int getStartColor() {

                            return startColor;
                        }


                        @Override
                        public void onNewColorEvaluated(float process, int colorNew) {

                            mTestColorText.setBackgroundColor(colorNew);
                        }
                    }, endColor);
                }

                mTestColorEvaluator.setFraction(fraction);
            }


            private void testRotation(TextView text, float fraction) {

                if (mTestRotationEvaluator == null) {

                    mTestRotationEvaluator = new RotationEvaluator(text, 180);
                }

                mTestRotationEvaluator.setFraction(fraction);
            }


            private void testAlpha(TextView text, float fraction) {

                if (mTestAlphaEvaluator == null) {

                    mTestAlphaEvaluator = new AlphaEvaluator(text, 0.5f);
                }

                mTestAlphaEvaluator.setFraction(fraction);
            }


            private void testTranslate(TextView text, float fraction) {

                if (mTestTransitionTranslateEvaluator == null) {

                    mTestTransitionTranslateEvaluator = new TranslateEvaluator(
                            text,
                            text.getX() + text.getWidth(),
                            text.getY() + 800);
                }

                mTestTransitionTranslateEvaluator.setFraction(fraction);
            }


            private void testChangeBounds(ViewRect viewRect, View view, float fraction) {

                /* 位置大小变换 */

                if (mTestTextEvaluator == null) {

                    ViewVisionState end = new ViewVisionState(
                            view,
                            viewRect.testTextLeftEnd,
                            viewRect.testTextTopEnd,
                            viewRect.testTextRightEnd,
                            viewRect.testTextBottomEnd
                    );

                    mTestTextEvaluator = new TransitionEvaluator(view, end);
                    mTestTextEvaluator.setRemeasureWhenFractionChanged(true);
                }

                mTestTextEvaluator.setFraction(fraction);
            }
        }

        /**
         * 执行子view折叠动画
         */
        private class CollapseTransitionListener implements TransitionFactory.OnTransitionChangeListener {

            private TransitionEvaluator mTestTextEvaluator;
            private TranslateEvaluator  mTestTransitionTranslateEvaluator;
            private AlphaEvaluator      mTestAlphaEvaluator;
            private RotationEvaluator   mTestRotationEvaluator;
            private ColorEvaluator      mTestColorEvaluator;


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

                testChangeBounds(mViewRect, mTestTransitionText, fraction);
                testTranslate(mTestTranslateText, fraction);
                testAlpha(mTestAlphaText, fraction);
                testRotation(mTestRotationText, fraction);
                testColor(mTestColorText, fraction);
            }


            private void testColor(TextView text, float fraction) {

                if (mTestColorEvaluator == null) {

                    final int endColor = mTestColorText
                            .getContext()
                            .getResources()
                            .getColor(R.color.gold);

                    final int startColor = mTestColorText
                            .getContext()
                            .getResources()
                            .getColor(R.color.orangered);

                    mTestColorEvaluator = new ColorEvaluator(new ColorEvaluator.ColorEvaluatorConstructor() {
                        @Override
                        public int getStartColor() {

                            return startColor;
                        }


                        @Override
                        public void onNewColorEvaluated(float process, int colorNew) {

                            mTestColorText.setBackgroundColor(colorNew);
                        }
                    }, endColor);
                }

                mTestColorEvaluator.setFraction(fraction);
            }


            private void testRotation(TextView text, float fraction) {

                if (mTestRotationEvaluator == null) {

                    mTestRotationEvaluator = new RotationEvaluator(text, 0);
                }

                mTestRotationEvaluator.setFraction(fraction);
            }


            private void testAlpha(TextView text, float fraction) {

                if (mTestAlphaEvaluator == null) {

                    mTestAlphaEvaluator = new AlphaEvaluator(text, 1f);
                }

                mTestAlphaEvaluator.setFraction(fraction);
            }


            private void testTranslate(TextView text, float fraction) {

                if (mTestTransitionTranslateEvaluator == null) {

                    mTestTransitionTranslateEvaluator = new TranslateEvaluator(
                            text,
                            text.getX() - text.getWidth(),
                            text.getY() - 800);
                }

                mTestTransitionTranslateEvaluator.setFraction(fraction);
            }


            private void testChangeBounds(ViewRect viewRect, View view, float fraction) {

                if (mTestTextEvaluator == null) {

                    ViewVisionState end = new ViewVisionState(
                            view,
                            viewRect.testTextLeftStart,
                            viewRect.testTextTopStart,
                            viewRect.testTextRightStart,
                            viewRect.testTextBottomStart
                    );

                    mTestTextEvaluator = new TransitionEvaluator(view, end);
                    mTestTextEvaluator.setRemeasureWhenFractionChanged(true);
                }

                mTestTextEvaluator.setFraction(fraction);
            }
        }
    }
}
