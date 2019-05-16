package tech.threekilogram.transitionmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import tech.liujin.transition.MockMeasure;
import tech.liujin.transition.animator.AnimatorFactory;
import tech.liujin.transition.evaluator.view.TransitionEvaluator;

public class LayoutSideActivity extends AppCompatActivity {

      private static final String TAG = LayoutSideActivity.class.getSimpleName();

      private TextView            mTextView;
      private ImageView           mImageView;
      private Animator            mAnimator;
      private TransitionEvaluator mEvaluator;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, LayoutSideActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_layout_side );
            initView();
      }

      private void initView ( ) {

            mTextView = (TextView) findViewById( R.id.textView );
            mImageView = (ImageView) findViewById( R.id.imageView );
            mImageView.setVisibility( View.GONE );

            mTextView.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        mTextView.setMaxLines( 1 );
                        int height = MockMeasure.measureAtMostHeight( mTextView );
                        mTextView.setMaxLines( 10000 );
                        int mostHeight = MockMeasure.measureAtMostHeight( mTextView );

                        Log.i( TAG, "run: " + height + " " + mostHeight );

                        mEvaluator = TransitionEvaluator.changeHeight( mTextView, mostHeight );
                        mAnimator = AnimatorFactory.makeAnimator( mEvaluator );
                        mAnimator.setDuration( 2000 );
                  }
            } );

            mTextView.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        if( mAnimator.isRunning() ) {
                              return;
                        }

                        mEvaluator.setReversed( !mEvaluator.isReversed() );
                        mAnimator.start();
                  }
            } );
      }
}
