package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import tech.threekilogram.transition.TransitionEvaluator;
import tech.threekilogram.transition.TransitionFactory;
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
     * 点击执行动画
     */
    private static class TestFrameTransition implements View.OnClickListener {

        private ViewRect mViewRect = new ViewRect();
        private boolean     isExpand;
        private Animator    mAnimator;
        private FrameLayout mFrameLayout;
        private TextView    mTestText;

        private TransitionFactory.OnTransitionChangeListener mExpandListener;
        private TransitionFactory.OnTransitionChangeListener mCollapseListener;


        public TestFrameTransition(FrameLayout frameLayout) {

            mFrameLayout = frameLayout;
            mTestText = mFrameLayout.findViewById(R.id.testText);

            mExpandListener = new ExpandTransitionListener();
            mCollapseListener = new ExpandTransitionListener();
        }


        @Override
        public void onClick(View v) {

            /* 放大frameLayout */

            if (mAnimator != null && mAnimator.isRunning()) {

                mAnimator.cancel();
            }

            initViewRectIfNeed(mViewRect);

            if (isExpand) {

                collapse();
            } else {

                expand();
            }

            isExpand = !isExpand;
        }


        private void initViewRectIfNeed(ViewRect rect) {

            if (rect.rootTopStart == 0) {
                rect.setRootRect(mFrameLayout);
                rect.setTestTextChangeBoundsRect(mTestText);
            }
        }


        private void collapse() {

            mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                    mFrameLayout,
                    0,
                    mViewRect.rootTopStart,
                    mFrameLayout.getRight(),
                    mFrameLayout.getBottom()
            );

            mAnimator.start();

        }


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

            private int width;
            private int height;

            private int rootTopStart;

            private int testTextLeftStart;
            private int testTextTopStart;
            private int testTextRightStart;
            private int testTextBottomStart;

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

        private class ExpandTransitionListener implements TransitionFactory.OnTransitionChangeListener {

            private TransitionEvaluator mTestTextEvaluator;


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

                testChangeBounds(mViewRect, mTestText, fraction);
            }


            private void testChangeBounds(ViewRect viewRect, View view, float fraction) {

                if (mTestTextEvaluator == null) {

                    ViewVisionState start = new ViewVisionState(
                            view,
                            viewRect.testTextLeftStart,
                            viewRect.testTextTopStart,
                            viewRect.testTextRightStart,
                            viewRect.testTextBottomStart
                    );

                    ViewVisionState end = new ViewVisionState(
                            view,
                            viewRect.testTextLeftEnd,
                            viewRect.testTextTopEnd,
                            viewRect.testTextRightEnd,
                            viewRect.testTextBottomEnd
                    );

                    mTestTextEvaluator = new TransitionEvaluator(view, start, end);
                }

                mTestTextEvaluator.setFraction(fraction);
                Log.i(TAG, "testChangeBounds:" + fraction);
            }
        }
    }
}
