package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import tech.liujin.transition.ViewVisionState;
import tech.liujin.transition.evaluator.Evaluator;
import tech.liujin.transition.evaluator.view.AlphaEvaluator;
import tech.liujin.transition.evaluator.view.ColorEvaluator;
import tech.liujin.transition.evaluator.view.ColorEvaluator.ColorApply;
import tech.liujin.transition.evaluator.view.RotationEvaluator;
import tech.liujin.transition.evaluator.view.RotationXEvaluator;
import tech.liujin.transition.evaluator.view.RotationYEvaluator;
import tech.liujin.transition.evaluator.view.TransitionEvaluator;
import tech.liujin.transition.evaluator.view.TranslateEvaluator;
import tech.liujin.transition.evaluator.view.ViewEvaluator;
import tech.liujin.transition.evaluator.view.VisionStateEvaluator;
import tech.liujin.transition.evaluator.wrapper.DelayEvaluator;
import tech.liujin.transition.evaluator.wrapper.SegmentFractionEvaluator;

public class ExampleActivity extends AppCompatActivity implements OnClickListener {

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
      private Button      mTranslateReverse;
      private Button      mAlphaReverse;
      private Button      mColorReverse;
      private Button      mRotationReverse;
      private Button      mRotationXReverse;
      private Button      mRotationYReverse;
      private Button      mTransitionReverse;
      private ImageView   mVisionImage;
      private SeekBar     mVisionSeek;
      private FrameLayout mVisionContainer;
      private Button      mVisionReverse;

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
            mAlphaImage = (ImageView) findViewById( R.id.alphaImage );
            mAlphaSeek = (SeekBar) findViewById( R.id.alphaSeek );
            mAlphaContainer = (FrameLayout) findViewById( R.id.alphaContainer );
            mColorImage = (ImageView) findViewById( R.id.colorImage );
            mColorSeek = (SeekBar) findViewById( R.id.colorSeek );
            mColorContainer = (FrameLayout) findViewById( R.id.colorContainer );
            mRotationImage = (ImageView) findViewById( R.id.rotationImage );
            mRotationSeek = (SeekBar) findViewById( R.id.rotationSeek );
            mRotationContainer = (FrameLayout) findViewById( R.id.rotationContainer );
            mTransitionImage = (ImageView) findViewById( R.id.transitionImage );
            mTransitionSeek = (SeekBar) findViewById( R.id.transitionSeek );
            mTransitionContainer = (FrameLayout) findViewById( R.id.transitionContainer );
            mSegmentImage = (ImageView) findViewById( R.id.segmentImage );
            mSegmentSeek = (SeekBar) findViewById( R.id.segmentSeek );
            mSegmentContainer = (FrameLayout) findViewById( R.id.segmentContainer );
            mRotationXImage = (ImageView) findViewById( R.id.rotationXImage );
            mRotationXSeek = (SeekBar) findViewById( R.id.rotationXSeek );
            mRotationXContainer = (FrameLayout) findViewById( R.id.rotationXContainer );
            mRotationYImage = (ImageView) findViewById( R.id.rotationYImage );
            mRotationYSeek = (SeekBar) findViewById( R.id.rotationYSeek );
            mRotationYContainer = (FrameLayout) findViewById( R.id.rotationYContainer );
            mDelayImage = (ImageView) findViewById( R.id.delayImage );
            mDelaySeek = (SeekBar) findViewById( R.id.delaySeek );
            mDelayContainer = (FrameLayout) findViewById( R.id.delayContainer );
            mTranslateReverse = (Button) findViewById( R.id.translateReverse );
            mAlphaReverse = (Button) findViewById( R.id.alphaReverse );
            mColorReverse = (Button) findViewById( R.id.colorReverse );
            mRotationReverse = (Button) findViewById( R.id.rotationReverse );
            mRotationXReverse = (Button) findViewById( R.id.rotationXReverse );
            mRotationYReverse = (Button) findViewById( R.id.rotationYReverse );
            mTransitionReverse = (Button) findViewById( R.id.transitionReverse );
            mVisionImage = (ImageView) findViewById( R.id.visionImage );
            mVisionSeek = (SeekBar) findViewById( R.id.visionSeek );
            mVisionContainer = (FrameLayout) findViewById( R.id.visionContainer );
            mVisionReverse = (Button) findViewById( R.id.visionReverse );

            buildTranslateTest();
            buildAlphaTest();
            buildColorTest();
            buildDelayTest();
            buildRotationYTest();
            buildRotationXTest();
            buildSegmentTest();
            buildRotationTest();
            buildTransitionTest();
            buildVisionTest();
      }

      private void buildVisionTest ( ) {

            ViewVisionState state = new ViewVisionState(
                500,
                100,
                1000,
                600,
                30,
                0,
                0,
                0.5f
            );
            final VisionStateEvaluator evaluator = new VisionStateEvaluator( mVisionImage, state );
            mVisionSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );
            mVisionReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      private void buildDelayTest ( ) {

            final Evaluator evaluator = new TranslateEvaluator( mDelayImage, 900, 0 );
            final Evaluator delay = new DelayEvaluator( evaluator, 2000 );
            mDelaySeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        delay.evaluate( v );
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
                        segment.evaluate( v );
                  }
            } );
      }

      private void buildRotationYTest ( ) {

            final Evaluator evaluator = new RotationYEvaluator( mRotationYImage, 180 );
            mRotationYSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );
            mRotationYReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      private void buildRotationXTest ( ) {

            final Evaluator evaluator = new RotationXEvaluator( mRotationXImage, 180 );
            mRotationXSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );
            mRotationXReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
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
                        evaluator.evaluate( v );
                  }
            } );
            mTransitionReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      private void buildRotationTest ( ) {

            final Evaluator evaluator = new RotationEvaluator( mRotationImage, 180 );
            mRotationSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );
            mRotationReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
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
                        evaluator.evaluate( v );
                  }
            } );
            mColorReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      private void buildAlphaTest ( ) {

            final AlphaEvaluator evaluator = new AlphaEvaluator( mAlphaImage, 0f );
            mAlphaSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );
            mAlphaReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      private void buildTranslateTest ( ) {

            final ViewEvaluator evaluator = new TranslateEvaluator(
                mTranslateImage,
                mTranslateImage.getLeft() + 600,
                mTranslateImage.getTop() + 150
            );
            mTranslateSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                  @Override
                  public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                        float v = progress * 1f / seekBar.getMax();
                        evaluator.evaluate( v );
                  }
            } );

            mTranslateReverse.setOnClickListener( new OnClickListener() {

                  @Override
                  public void onClick ( View v ) {

                        ( (ViewEvaluator) evaluator ).setReversed( !( (ViewEvaluator) evaluator ).isReversed() );
                  }
            } );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.translateReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.alphaReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.colorReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.rotationReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.rotationXReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.rotationYReverse:
                        // TODO 19/04/20
                        break;
                  case R.id.transitionReverse:
                        // TODO 19/04/20
                        break;
                  default:
                        break;
            }
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
