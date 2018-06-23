package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import tech.threekilogram.transition.OnTransitionChangeListener;
import tech.threekilogram.transition.TransitionFactory;
import tech.threekilogram.transition.ViewVisionState;

/**
 * @author wuxio
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    protected Button      mChangeBoundsButton;
    protected FrameLayout mChangeBoundsButtonContainer;
    protected ImageView   mChangeRotateButton;
    protected FrameLayout mChangeRotateButtonContainer;
    protected TextView    mChangeAlphaButton;
    protected FrameLayout mChangeAlphaButtonContainer;


    public static void start(Context context) {

        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);
        initView();

    }


    private void initView() {

        mChangeBoundsButton = findViewById(R.id.changeBoundsButton);
        mChangeBoundsButtonContainer = findViewById(R.id.changeBoundsButtonContainer);
        testChangeBounds(mChangeBoundsButtonContainer, mChangeBoundsButton);

        mChangeRotateButton = findViewById(R.id.changeRotateButton);
        mChangeRotateButtonContainer = findViewById(R.id.changeRotateButtonContainer);
        testChangeBoundsRotate(mChangeRotateButtonContainer, mChangeRotateButton);

        mChangeAlphaButton = (TextView) findViewById(R.id.changeAlphaButton);
        mChangeAlphaButtonContainer = (FrameLayout) findViewById(R.id.changeAlphaButtonContainer);
        testChangeAlpha(mChangeAlphaButtonContainer, mChangeAlphaButton);
    }


    private void testChangeAlpha(final FrameLayout container, final TextView button) {

        button.setOnClickListener(new View.OnClickListener() {

            private boolean isTransToRight = false;
            private Animator mAnimator;

            ViewVisionState locationLeft;
            ViewVisionState locationRight;

            /**
             * 使用该Action 在变化过程中保证文字位于center
             */
            private OnTransitionChangeListener mAction = new OnTransitionChangeListener() {
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

                    //                    Log.i(TAG, "onChange:" + process + " " + left + " " + top + " " +
                    // right + " " +
                    //                            bottom);

                    /* 因为该框架通过更改Layout位置重新布局view来实现变换的,
                     所以getWidth 和getMeasuredWidth 一般情况下不会相同,(height 同样)
                     一些与宽度高度相关的属性可能不会起作用:例如android:gravity="center",
                     此时需要在变化过程中重新测量一下,使该属性起作用
                     */
                    //                    TransitionFactory.remeasureViewWithExactSpec(view, right - left,
                    // bottom - top);
                }
            };


            @Override
            public void onClick(View v) {

                if (locationLeft == null) {
                    locationLeft = new ViewVisionState(v);
                    locationLeft.setOnTransitionChangeListener(mAction);
                    locationLeft.setAlpha(1f);
                }

                if (locationRight == null) {

                    int newRight = container.getWidth();
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = container.getHeight();
                    int newTop = newBottom - button.getHeight() - 100;

                    locationRight = new ViewVisionState(
                            v,
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            90,
                            v.getAlpha()
                    );
                    locationRight.setOnTransitionChangeListener(mAction);
                    locationRight.setAlpha(0.5f);
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = TransitionFactory.makeChangeAnimator(
                            v,
                            locationLeft
                    );
                    mAnimator.start();

                } else {

                    mAnimator = TransitionFactory.makeChangeAnimator(
                            v,
                            locationRight
                    );
                    mAnimator.start();
                }

                isTransToRight = !isTransToRight;
            }
        });

    }


    private void testChangeBoundsRotate(final FrameLayout container, final ImageView button) {

        button.setOnClickListener(new View.OnClickListener() {

            private boolean isTransToRight = false;
            private Animator mAnimator;

            ViewVisionState locationLeft;
            ViewVisionState locationRight;

            /**
             * 使用该Action 在变化过程中保证文字位于center
             */
            private OnTransitionChangeListener mAction = new OnTransitionChangeListener() {
                @Override
                public void onChange(
                        View view,
                        float process,
                        int left,
                        int top,
                        int right,
                        int bottom,
                        float rotation,float alpha) {

                    Log.i(TAG, "onChange:" + process + " " + left + " " + top + " " + right + " " +
                            bottom);

                    /* 因为该框架通过更改Layout位置重新布局view来实现变换的,
                     所以getWidth 和getMeasuredWidth 一般情况下不会相同,(height 同样)
                     一些与宽度高度相关的属性可能不会起作用:例如android:gravity="center",
                     此时需要在变化过程中重新测量一下,使该属性起作用
                     */
                    TransitionFactory.remeasureViewWithExactSpec(view, right - left, bottom - top);
                }
            };


            @Override
            public void onClick(View v) {

                if (locationLeft == null) {
                    locationLeft = new ViewVisionState(v);
                    locationLeft.setOnTransitionChangeListener(mAction);
                }

                if (locationRight == null) {

                    int newRight = container.getWidth();
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = container.getHeight();
                    int newTop = newBottom - button.getHeight() - 100;

                    locationRight = new ViewVisionState(
                            v,
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            90,
                            v.getAlpha()
                    );
                    locationRight.setOnTransitionChangeListener(mAction);
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = TransitionFactory.makeChangeAnimator(
                            v,
                            locationLeft
                    );
                    mAnimator.start();

                } else {

                    mAnimator = TransitionFactory.makeChangeAnimator(
                            v,
                            locationRight
                    );
                    mAnimator.start();
                }

                isTransToRight = !isTransToRight;
            }
        });

    }


    private void testChangeBounds(final FrameLayout container, final Button button) {

        button.setOnClickListener(new View.OnClickListener() {

            private boolean isTransToRight = false;
            private Animator mAnimator;

            ViewVisionState location;

            /**
             * 使用该Action 在变化过程中保证文字位于center
             */
            private OnTransitionChangeListener mAction = new OnTransitionChangeListener() {
                @Override
                public void onChange(
                        View view,
                        float process,
                        int left,
                        int top,
                        int right,
                        int bottom,
                        float rotation,float alpha) {

                    Log.i(TAG, "onChange:" + process + " " + left + " " + top + " " + right + " " +
                            bottom);

                    /* 因为该框架通过更改Layout位置重新布局view来实现变换的,
                     所以getWidth 和getMeasuredWidth 一般情况下不会相同,(height 同样)
                     一些与宽度高度相关的属性可能不会起作用:例如android:gravity="center",
                     此时需要在变化过程中重新测量一下,使该属性起作用
                     */
                    TransitionFactory.remeasureViewWithExactSpec(view, right - left, bottom - top);
                }
            };


            @Override
            public void onClick(View v) {

                int width = container.getWidth();
                int height = container.getHeight();

                if (location == null) {
                    location = new ViewVisionState(v);
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                            v,
                            location.getLeft(),
                            location.getTop(),
                            location.getRight(),
                            location.getBottom(),
                            mAction
                    );
                    mAnimator.start();

                } else {

                    int newRight = width;
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = height;
                    int newTop = newBottom - button.getHeight() - 100;

                    mAnimator = TransitionFactory.makeChangeBoundsAnimator(
                            v,
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            mAction
                    );
                    mAnimator.start();
                }

                isTransToRight = !isTransToRight;
            }
        });
    }
}
