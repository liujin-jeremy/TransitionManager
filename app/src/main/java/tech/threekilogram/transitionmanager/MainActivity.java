package tech.threekilogram.transitionmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
      }


      public void toFullTestActivity ( View view ) {

            FullTestActivity.start( this );
      }

      public void toSceneActivity ( View view ) {

            SceneActivity.start( this );
      }

      public void toSceneSetActivity ( View view ) {

            SceneSetActivity.start( this );
      }

      public void toExampleActivity ( View view ) {

            ExampleActivity.start( this );
      }
}
