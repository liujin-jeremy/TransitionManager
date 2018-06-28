package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import tech.threekilogram.transition.scene.DelayEvaluator;
import tech.threekilogram.transition.Evaluator;
import tech.threekilogram.transition.scene.SceneManager;
import tech.threekilogram.transition.scene.SegmentFractionEvaluator;

/**
 * @author wuxio
 */
public class SceneActivity extends AppCompatActivity {

    public static void start(Context context) {

        Intent starter = new Intent(context, SceneActivity.class);
        context.startActivity(starter);
    }


    protected FrameLayout  mRoot;
    protected FrameLayout  mScene;
    private   SceneManager mSceneManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_scene);

        initView();
    }


    private void initView() {

        mRoot = (FrameLayout) findViewById(R.id.root);
        mScene = (FrameLayout) findViewById(R.id.scene);

        mRoot.post(new Runnable() {
            @Override
            public void run() {

                mSceneManager = new SceneManager(
                        mScene,
                        0,
                        0,
                        mRoot.getWidth(),
                        mRoot.getBottom(),
                        R.layout.scene_test_1
                );

                Evaluator evaluator01 = mSceneManager.getChildEvaluator(R.id.view02);
                DelayEvaluator delayEvaluator = new DelayEvaluator(evaluator01, 1000);
                mSceneManager.updateChildEvaluator(R.id.view02, delayEvaluator);

                Evaluator evaluator1 = mSceneManager.getChildEvaluator(R.id.view03);
                SegmentFractionEvaluator fractionEvaluator = new SegmentFractionEvaluator(evaluator1, 0.4f,
                        0.9f);
                mSceneManager.updateChildEvaluator(R.id.view03, fractionEvaluator);
            }
        });

        mScene.setOnClickListener(new View.OnClickListener() {

            private Animator start;
            private Animator end;


            @Override
            public void onClick(View v) {

                if (mSceneManager.isCurrentSceneEnd()) {

                    start = mSceneManager.createSceneBeginAnimator();
                    start.setDuration(3000);
                    start.start();

                } else {

                    end = mSceneManager.createSceneEndAnimator();
                    end.setDuration(3000);
                    end.start();
                }
            }
        });
    }
}
