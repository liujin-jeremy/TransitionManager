package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import tech.threekilogram.transition.OnTransitionChangeListener;
import tech.threekilogram.transition.TransitionFactory;

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

        mTestFrame = (FrameLayout) findViewById(R.id.testFrame);
        mTestFrame.setOnClickListener(new TestFrameTransition(mTestFrame));
    }


    /**
     * 点击执行动画
     */
    private class TestFrameTransition implements View.OnClickListener {

        private FrameLayout mFrameLayout;
        private boolean     isExpand;

        private Animator          mAnimator;
        private LocationContainer mContainer;


        public TestFrameTransition(FrameLayout frameLayout) {

            mFrameLayout = frameLayout;

        }


        @Override
        public void onClick(View v) {

            /* 放大frameLayout */

            initViewRect();

            if (mAnimator != null && mAnimator.isRunning()) {

                mAnimator.cancel();
            }

            if (isExpand) {

                collapse();
            } else {

                expand();
            }

            isExpand = !isExpand;
        }


        private void initViewRect() {

            if (mContainer == null) {

                mContainer = new LocationContainer(mFrameLayout);
                mContainer.initRect();
            }
        }


        private void expand() {

            mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                    mFrameLayout,
                    0,
                    0,
                    mFrameLayout.getRight(),
                    mFrameLayout.getBottom(),
                    new OnTransitionChangeListener() {
                        @Override
                        public void onChange(
                                View view,
                                float process,
                                int left,
                                int top,
                                int right,
                                int bottom,
                                float rotation,
                                float alpha) {

                        }
                    }
            );
            mAnimator.start();
        }


        private void collapse() {

            mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                    mFrameLayout,
                    mContainer.rectRootStart.left,
                    mContainer.rectRootStart.top,
                    mFrameLayout.getRight(),
                    mFrameLayout.getBottom()
            );
            mAnimator.start();
        }


        /**
         * 记录所有view起始结束位置
         */
        private class LocationContainer {

            private FrameLayout root;
            private Rect        rectRootStart;
            private Rect        rectRootEnd;

            private TextView testText;
            private Rect     rectTestTextStart;
            private Rect     rectTestTextEnd;


            public LocationContainer(FrameLayout root) {

                this.root = root;
                testText = root.findViewById(R.id.testText);
            }


            public void initRect() {

                rectRootStart = new Rect(
                        root.getLeft(),
                        root.getTop(),
                        root.getRight(),
                        root.getBottom()
                );

                rectRootEnd = new Rect(
                        0,
                        0,
                        root.getRight(),
                        root.getBottom()
                );

                rectTestTextStart = new Rect(
                        testText.getLeft(),
                        testText.getTop(),
                        testText.getRight(),
                        testText.getBottom()
                );

                rectTestTextEnd = new Rect(
                        testText.getLeft(),
                        testText.getTop() + 500,
                        testText.getRight(),
                        testText.getBottom() + 500
                );
            }
        }

        /**
         * 展开折叠子view
         */
        private class AnimatorUpdateListener implements Animator.AnimatorListener {

            private boolean isExpand;


            public void setExpand(boolean expand) {

                isExpand = expand;
            }


            @Override
            public void onAnimationStart(Animator animation) {

                if (isExpand) {

                    Animator animator = TransitionFactory.makeChangeBoundsAnimator(
                            mContainer.testText,
                            mContainer.rectTestTextStart.left,
                            mContainer.rectTestTextStart.top,
                            mContainer.rectTestTextStart.right,
                            mContainer.rectTestTextStart.bottom
                    );

                    animator.start();

                } else {

                    Animator animator = TransitionFactory.makeChangeBoundsAnimator(
                            mContainer.testText,
                            mContainer.rectTestTextEnd.left,
                            mContainer.rectTestTextEnd.top,
                            mContainer.rectTestTextEnd.right,
                            mContainer.rectTestTextEnd.bottom
                    );

                    animator.start();
                }

                Log.i(TAG, "onAnimationStart:" + "");
            }


            @Override
            public void onAnimationEnd(Animator animation) {

            }


            @Override
            public void onAnimationCancel(Animator animation) {

            }


            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }
    }
}
