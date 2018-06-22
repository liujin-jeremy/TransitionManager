package tech.threekilogram.transitionmanager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author wuxio 2018-06-21:10:29
 */
public class LogConstraintLayout extends ConstraintLayout {

    private static final String TAG = "LogConstraintLayout";


    public LogConstraintLayout(Context context) {

        super(context);
    }


    public LogConstraintLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    public LogConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure:" + "");
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout:" + "");
    }
}
