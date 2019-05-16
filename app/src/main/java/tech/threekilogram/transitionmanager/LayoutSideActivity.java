package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import tech.liujin.transition.MockMeasure;
import tech.liujin.transition.view.CollapsingLayout;

public class LayoutSideActivity extends AppCompatActivity {

      private static final String TAG = LayoutSideActivity.class.getSimpleName();

      private TextView         mTextView;
      private ImageView        mImageView;
      private CollapsingLayout mCollapsingHeight;
      private SeekBar          mSeek;

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

            mTextView = findViewById( R.id.textView );
            mImageView = findViewById( R.id.imageView );
            mCollapsingHeight = findViewById( R.id.collapsingSize );

            mTextView.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        mTextView.setMaxLines( 1 );
                        int height = MockMeasure.measureAtMostHeight( mTextView );
                        mTextView.setMaxLines( 1000 );
                        int mostHeight = MockMeasure.measureAtMostHeight( mTextView );

                        mCollapsingHeight.setSize( height, mostHeight );
                  }
            } );
            mSeek = (SeekBar) findViewById( R.id.seek );
            mSeek.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        mCollapsingHeight.collapsing( v );
                  }

                  @Override
                  public void onStartTrackingTouch ( SeekBar seekBar ) {

                  }

                  @Override
                  public void onStopTrackingTouch ( SeekBar seekBar ) {

                  }
            } );
      }
}
