package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import tech.liujin.transition.MockMeasure;

public class LayoutSideActivity extends AppCompatActivity {

      private static final String TAG = LayoutSideActivity.class.getSimpleName();

      private TextView  mTextView;
      private ImageView mImageView;

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

            mTextView.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        mTextView.setMaxLines( 1 );
                        int height = MockMeasure.measureAtMostHeight( mTextView );
                        mTextView.setMaxLines( 10000 );
                        int mostHeight = MockMeasure.measureAtMostHeight( mTextView );

                        Log.i( TAG, "run: " + height + " " + mostHeight );
                        mTextView.setMaxLines( 1 );
                  }
            } );
      }
}
