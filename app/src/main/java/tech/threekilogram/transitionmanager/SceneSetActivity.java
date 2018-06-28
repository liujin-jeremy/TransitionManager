package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import tech.threekilogram.transition.scene.SceneManager;

/**
 * @author wuxio
 */
public class SceneSetActivity extends AppCompatActivity {

      public static void start (Context context) {

            Intent starter = new Intent(context, SceneSetActivity.class);
            context.startActivity(starter);
      }

      protected FrameLayout  mRoot;
      protected FrameLayout  mScene;
      private   SceneManager mSceneManager;

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_scene);

            initView();
      }

      private void initView () {

            mRoot = (FrameLayout) findViewById(R.id.root);
            mScene = (FrameLayout) findViewById(R.id.scene);

            mRoot.post(new Runnable() {

                  @Override
                  public void run () {

                        mSceneManager = new SceneManager(
                            mScene,
                            0,
                            0,
                            mRoot.getWidth(),
                            mRoot.getBottom(),
                            R.layout.scene_test_1
                        );
                  }
            });

            mScene.setOnClickListener(new View.OnClickListener() {

                  @Override
                  public void onClick (View v) {

                        if(mSceneManager.isCurrentSceneEnd()) {

                              mSceneManager.setSceneToBegin();
                        } else {

                              mSceneManager.setSceneToEnd();
                        }
                  }
            });
      }
}
