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

      public void toExampleActivity ( View view ) {

            ExampleActivity.start( this );
      }

      public void toFullTestActivity ( View view ) {

            FullTestActivity.start( this );
      }

      public void toSceneActivity ( View view ) {

            SceneActivity.start( this );
      }

      public void toSide ( View view ) {

            SideActivity.start( this );
      }

      public void toLayoutSide ( View view ) {

            LayoutSideActivity.start( this );
      }
}
