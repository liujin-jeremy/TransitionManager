package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import tech.liujin.transition.evaluator.view.TransitionEvaluator;

public class SideActivity extends AppCompatActivity {

      private TextView mChangeLeftText;
      private SeekBar  mChangeLeftTextSeek;
      private TextView mChangeRightText;
      private SeekBar  mChangeRightTextSeek;
      private TextView mChangeTopText;
      private SeekBar  mChangeTopTextSeek;
      private TextView mChangeBottomText;
      private SeekBar  mChangeBottomTextSeek;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SideActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_side );
            initView();
      }

      private void initView ( ) {

            mChangeLeftText = findViewById( R.id.changeLeftText );
            mChangeLeftTextSeek = findViewById( R.id.changeLeftTextSeek );
            testChangeLeftText();
            mChangeRightText = findViewById( R.id.changeRightText );
            mChangeRightTextSeek = findViewById( R.id.changeRightTextSeek );
            testChangeRightText();
            mChangeTopText = findViewById( R.id.changeTopText );
            mChangeTopTextSeek = findViewById( R.id.changeTopTextSeek );
            testChangeTopText();
            mChangeBottomText = findViewById( R.id.changeBottomText );
            mChangeBottomTextSeek = findViewById( R.id.changeBottomTextSeek );
            testChangeBottomText();
      }

      private void testChangeBottomText ( ) {

            mChangeBottomText.setGravity( Gravity.END );
            mChangeBottomText.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        final TransitionEvaluator evaluator = TransitionEvaluator
                            .changeBottom( mChangeBottomText, mChangeBottomText.getBottom() + 100 );

                        mChangeBottomTextSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                              @Override
                              public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                                    float v = progress * 1f / seekBar.getMax();
                                    evaluator.evaluate( v );
                              }
                        } );
                  }
            } );
      }

      private void testChangeTopText ( ) {

            mChangeTopText.setGravity( Gravity.TOP );
            mChangeTopText.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        final TransitionEvaluator evaluator = TransitionEvaluator
                            .changeTop( mChangeTopText, mChangeTopText.getTop() - 100 );

                        mChangeTopTextSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                              @Override
                              public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                                    float v = progress * 1f / seekBar.getMax();
                                    evaluator.evaluate( v );
                              }
                        } );
                  }
            } );
      }

      private void testChangeRightText ( ) {

            mChangeRightText.setGravity( Gravity.START );
            mChangeRightText.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        final TransitionEvaluator evaluator = TransitionEvaluator
                            .changeRight( mChangeRightText, mChangeRightText.getRight() + 200 );

                        mChangeRightTextSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                              @Override
                              public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                                    float v = progress * 1f / seekBar.getMax();
                                    evaluator.evaluate( v );
                              }
                        } );
                  }
            } );
      }

      private void testChangeLeftText ( ) {

            mChangeLeftText.setGravity( Gravity.END );
            mChangeLeftText.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        final TransitionEvaluator evaluator = TransitionEvaluator
                            .changeLeft( mChangeLeftText, mChangeLeftText.getLeft() - 200 );

                        mChangeLeftTextSeek.setOnSeekBarChangeListener( new SimpleOnSeekBarChangeListener() {

                              @Override
                              public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                                    float v = progress * 1f / seekBar.getMax();
                                    evaluator.evaluate( v );
                              }
                        } );
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
