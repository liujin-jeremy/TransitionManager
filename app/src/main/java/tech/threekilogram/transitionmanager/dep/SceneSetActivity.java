package tech.threekilogram.transitionmanager.dep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import tech.liujin.transition.scene.SceneManager;
import tech.threekilogram.transitionmanager.R;

/**
 * @author wuxio
 */
public class SceneSetActivity extends AppCompatActivity {

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SceneSetActivity.class );
            context.startActivity( starter );
      }

      protected FrameLayout  mRoot;
      protected FrameLayout  mScene;
      private   SceneManager mSceneManager;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            super.setContentView( R.layout.activity_scene );
      }
}
