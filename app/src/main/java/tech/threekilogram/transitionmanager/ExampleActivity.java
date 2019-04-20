package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import tech.threekilogram.transition.Evaluator;
import tech.threekilogram.transition.impl.AlphaEvaluator;
import tech.threekilogram.transition.impl.ColorEvaluator;
import tech.threekilogram.transition.impl.ColorEvaluator.ColorApply;
import tech.threekilogram.transition.impl.DelayEvaluator;
import tech.threekilogram.transition.impl.RotationEvaluator;
import tech.threekilogram.transition.impl.RotationXEvaluator;
import tech.threekilogram.transition.impl.RotationYEvaluator;
import tech.threekilogram.transition.impl.SegmentFractionEvaluator;
import tech.threekilogram.transition.impl.TransitionEvaluator;
import tech.threekilogram.transition.impl.TranslateEvaluator;

public class ExampleActivity extends AppCompatActivity {

      private ImageView   mTranslateImage;
      private SeekBar     mTranslateSeek;
      private FrameLayout mTranslateContainer;
      private ImageView   mAlphaImage;
      private SeekBar     mAlphaSeek;
      private FrameLayout mAlphaContainer;
      private ImageView   mColorImage;
      private SeekBar     mColorSeek;
      private FrameLayout mColorContainer;
      private ImageView   mRotationImage;
      private SeekBar     mRotationSeek;
      private FrameLayout mRotationContainer;
      private ImageView   mTransitionImage;
      private SeekBar     mTransitionSeek;
      private FrameLayout mTransitionContainer;
      private ImageView   mSegmentImage;
      private SeekBar     mSegmentSeek;
      private FrameLayout mSegmentContainer;
      private ImageView   mRotationXImage;
      private SeekBar     mRotationXSeek;
      private FrameLayout mRotationXContainer;
      private ImageView   mRotationYImage;
      private SeekBar     mRotationYSeek;
      private FrameLayout mRotationYContainer;
      private ImageView   mDelayImage;
      private SeekBar     mDelaySeek;
      private FrameLayout mDelayContainer;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, ExampleActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_example );
            initView();
      }

      private void initView ( ) {

            mTranslateContainer = (FrameLayout) findViewById( R.id.translateContainer );
            mTranslateImage = (ImageView) findViewById( R.id.translateImage );
            mTranslateSeek = (SeekBar) findViewById( R.id.translateSeek );
            buildTranslateTest();
            mAlphaImage = (ImageView) findViewById( R.id.alphaImage );
            mAlphaSeek = (SeekBar) findViewById( R.id.alphaSeek );
            mAlphaContainer = (FrameLayout) findViewById( R.id.alphaContainer );
            buildAlphaTest();
            mColorImage = (ImageView) findViewById( R.id.colorImage );
            mColorSeek = (SeekBar) findViewById( R.id.colorSeek );
            buildColorTest();
            mColorContainer = (FrameLayout) findViewById( R.id.colorContainer );
            mRotationImage = (ImageView) findViewById( R.id.rotationImage );
            mRotationSeek = (SeekBar) findViewById( R.id.rotationSeek );
            mRotationContainer = (FrameLayout) findViewById( R.id.rotationContainer );
            buildRotationTest();
            mTransitionImage = (ImageView) findViewById( R.id.transitionImage );
            mTransitionSeek = (SeekBar) findViewById( R.id.transitionSeek );
            mTransitionContainer = (FrameLayout) findViewById( R.id.transitionContainer );
            buildTransitionTest();
            mSegmentImage = (ImageView) findViewById( R.id.segmentImage );
            mSegmentSeek = (SeekBar) findViewById( R.id.segmentSeek );
            mSegmentContainer = (FrameLayout) findViewById( R.id.segmentContainer );
            buildSegmentTest();
            mRotationXImage = (ImageView) findViewById( R.id.rotationXImage );
            mRotationXSeek = (SeekBar) findViewById( R.id.rotationXSeek );
            mRotationXContainer = (FrameLayout) findViewById( R.id.rotationXContainer );
            buildRotationXTest();
            mRotationYImage = (ImageView) findViewById( R.id.rotationYImage );
            mRotationYSeek = (SeekBar) findViewById( R.id.rotationYSeek );
            mRotationYContainer = (FrameLayout) findViewById( R.id.rotationYContainer );
            buildRotationYTest();
            mDelayImage = (ImageView) findViewById( R.id.delayImage );
            mDelaySeek = (SeekBar) findViewById( R.id.delaySeek );
            mDelayContainer = (FrameLayout) findViewById( R.id.delayContainer );
            buildDelayTest();
      }

      private void buildDelayTest ( ) {

            final Evaluator evaluator = new TranslateEvaluator( mDelayImage, 900, 0 );
            final Evaluator delay = new DelayEvaluator( evaluator, 2000 );
            mDelaySeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        delay.setFraction( v );
                  }
            } );
      }

      private void buildRotationYTest ( ) {

            final Evaluator evaluator = new RotationYEvaluator( mRotationYImage, 180 );
            mRotationYSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildRotationXTest ( ) {

            final Evaluator evaluator = new RotationXEvaluator( mRotationXImage, 180 );
            mRotationXSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildSegmentTest ( ) {

            Evaluator evaluator = new TranslateEvaluator(
                mSegmentImage,
                800,
                150
            );
            final Evaluator segment = new SegmentFractionEvaluator( evaluator, 0.3f, 0.7f );

            mSegmentSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        segment.setFraction( v );
                  }
            } );
      }

      private void buildTransitionTest ( ) {

            final Evaluator evaluator = new TransitionEvaluator(
                mTransitionImage,
                500,
                50,
                900,
                450
            );
            mTransitionSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildRotationTest ( ) {

            final Evaluator evaluator = new RotationEvaluator( mRotationImage, 180 );
            mRotationSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildColorTest ( ) {

            final Evaluator evaluator = new ColorEvaluator(
                mColorImage,
                getResources().getColor( R.color.gold ),
                Color.RED,
                new ColorApply() {

                      @Override
                      public void onNewColorEvaluated ( View view, float process, int colorNew ) {

                            view.setBackgroundColor( colorNew );
                      }
                }
            );
            mColorSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildAlphaTest ( ) {

            final AlphaEvaluator evaluator = new AlphaEvaluator( mAlphaImage, 0f );
            mAlphaSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private void buildTranslateTest ( ) {

            final Evaluator evaluator = new TranslateEvaluator(
                mTranslateImage,
                mTranslateImage.getLeft() + 600,
                mTranslateImage.getTop() + 150
            );
            mTranslateSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.setFraction( v );
                  }
            } );
      }

      private abstract class SimpleOnSeekBarChangeListener implements OnSeekBarChangeListener {

            @Override
            public void onStartTrackingTouch ( SeekBar seekBar ) {

            }

            @Override
            public void onStopTrackingTouch ( SeekBar seekBar ) {

            }
      }
}
