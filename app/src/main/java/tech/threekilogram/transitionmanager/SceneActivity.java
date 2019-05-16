package tech.threekilogram.transitionmanager;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import tech.liujin.transition.evaluator.wrapper.SegmentFractionEvaluator;
import tech.liujin.transition.scene.SceneManager;

/**
 * @author wuxio
 */
public class SceneActivity extends AppCompatActivity {

      private UpdateListener mListener;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SceneActivity.class );
            context.startActivity( starter );
      }

      protected FrameLayout   mRoot;
      protected FrameLayout   mScene;
      private   SceneManager  mSceneManager;
      private   ValueAnimator mAnimator;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            super.setContentView( R.layout.activity_scene );

            initView();
      }

      private void initView ( ) {

            mRoot = findViewById( R.id.root );
            mScene = findViewById( R.id.scene );

            mSceneManager = new SceneManager( mRoot, R.layout.activity_scene_end );
            mRoot.post( new Runnable() {

                  @Override
                  public void run ( ) {

//                        mSceneManager.updateChildEvaluator(
//                            R.id.view02,
//                            new DelayEvaluator( mSceneManager.getChildEvaluator( R.id.view02 ), 3000 )
//                        );
                        mSceneManager.updateChildEvaluator(
                            R.id.view03,
                            new SegmentFractionEvaluator( mSceneManager.getChildEvaluator( R.id.view03 ), 0.2f, 0.7f )
                        );
                  }
            } );

            mAnimator = ValueAnimator.ofInt( 0, 1 );
            mAnimator.setDuration( 1200 );
            mListener = new UpdateListener();
            mAnimator.addUpdateListener( mListener );

            mScene.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        if( mAnimator.isRunning() ) {
                              return;
                        }

                        mListener.setFlip( !mListener.isFlip );
                        mAnimator.start();
                  }
            } );
      }

      private class UpdateListener implements AnimatorUpdateListener {

            private boolean isFlip = true;

            public void setFlip ( boolean flip ) {

                  isFlip = flip;
                  mSceneManager.setReversed( isFlip );
            }

            @Override
            public void onAnimationUpdate ( ValueAnimator animation ) {

                  float animatedFraction = animation.getAnimatedFraction();
                  mSceneManager.evaluate( animatedFraction );
            }
      }
}
