package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import tech.threekilogram.transition.ViewVisionState;

import static tech.threekilogram.transition.TransitionFactory.OnTransitionChangeListener;
import static tech.threekilogram.transition.TransitionFactory.makeChangeAnimator;
import static tech.threekilogram.transition.TransitionFactory.makeChangeBoundsAnimator;

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

                }
            };


            @Override
            public void onClick(View v) {

                if (locationLeft == null) {
                    locationLeft = new ViewVisionState(v);
                    locationLeft.setAlpha(1f);
                }

                if (locationRight == null) {

                    int newRight = container.getWidth();
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = container.getHeight();
                    int newTop = newBottom - button.getHeight() - 100;

                    locationRight = new ViewVisionState(
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            90,
                            v.getAlpha()
                    );
                    locationRight.setAlpha(0.5f);
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = makeChangeAnimator(
                            v,
                            locationLeft
                    );
                    mAnimator.start();

                } else {

                    mAnimator = makeChangeAnimator(
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
                        float rotation, float alpha) {

                    //Log.i(TAG, "onChange:" + process + " " + left + " " + top + " " + right + " " + bottom);

                }
            };


            @Override
            public void onClick(View v) {

                if (locationLeft == null) {
                    locationLeft = new ViewVisionState(v);
                }

                if (locationRight == null) {

                    int newRight = container.getWidth();
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = container.getHeight();
                    int newTop = newBottom - button.getHeight() - 100;

                    locationRight = new ViewVisionState(
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            90,
                            v.getAlpha()
                    );
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = makeChangeAnimator(
                            v,
                            locationLeft,
                            mAction
                    );
                    mAnimator.start();

                } else {

                    mAnimator = makeChangeAnimator(
                            v,
                            locationRight,
                            mAction
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

            ViewVisionState mVisionState;

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

                    //Log.i(TAG, "onChange:" + process + " " + left + " " + top + " " + right + " " + bottom);

                }
            };


            @Override
            public void onClick(View v) {

                if (mVisionState == null) {
                    mVisionState = new ViewVisionState(v);
                }

                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (isTransToRight) {

                    mAnimator = makeChangeBoundsAnimator(
                            v,
                            mVisionState.getLeft(),
                            mVisionState.getTop(),
                            mVisionState.getRight(),
                            mVisionState.getBottom(),
                            mAction,
                            true
                    );
                    mAnimator.start();

                } else {

                    int newRight = container.getWidth();
                    int newLeft = newRight - button.getWidth() - 100;
                    int newBottom = container.getHeight();
                    int newTop = newBottom - button.getHeight() - 100;

                    mAnimator = makeChangeBoundsAnimator(
                            v,
                            newLeft,
                            newTop,
                            newRight,
                            newBottom,
                            mAction,
                            true
                    );
                    mAnimator.start();
                }

                isTransToRight = !isTransToRight;
            }
        });
    }
}
