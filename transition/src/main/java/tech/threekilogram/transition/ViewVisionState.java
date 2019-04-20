package tech.threekilogram.transition;

/**
 * @author wuxio 2018-06-21:18:14
 */

import android.support.annotation.FloatRange;
import android.view.View;

/**
 * 记录一个view的坐标，角度，透明度
 *
 * @author wuxio
 */
@SuppressWarnings("WeakerAccess")
public class ViewVisionState {

      /**
       * 坐标
       */
      int   mLeft;
      int   mTop;
      int   mRight;
      int   mBottom;
      /**
       * 旋转角度
       */
      float mRotation;
      float mRotationX;
      float mRotationY;
      /**
       * alphaChanged
       */
      float mAlpha;

      public ViewVisionState ( ) { }

      /**
       * 创建一个当前的可见状态
       */
      public ViewVisionState ( View view ) {

            this(
                view.getLeft(),
                view.getTop(),
                view.getRight(),
                view.getBottom(),
                view.getRotation(),
                view.getRotationX(),
                view.getRotationY(),
                view.getAlpha()
            );
      }

      /**
       * 根据另一个可视状态创建一个一模一样的
       */
      public ViewVisionState ( ViewVisionState visionState ) {

            this(
                visionState.getLeft(),
                visionState.getTop(),
                visionState.getRight(),
                visionState.getBottom(),
                visionState.getRotation(),
                visionState.mRotationX,
                visionState.mRotationY,
                visionState.getAlpha()
            );
      }

      /**
       * 创建一个未来的可见状态
       */
      public ViewVisionState (
          View view,
          int left,
          int top,
          int right,
          int bottom ) {

            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mRotation = view.getRotation();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mAlpha = view.getAlpha();
      }

      /**
       * 创建一个未来的可见状态
       */
      public ViewVisionState (
          int left,
          int top,
          int right,
          int bottom,
          float rotation,
          float rotationX,
          float rotationY,
          float alpha ) {

            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mRotation = rotation;
            this.mRotationX = rotationX;
            this.mRotationY = rotationY;
            this.mAlpha = alpha;
      }

      public int getLeft ( ) {

            return mLeft;
      }

      public int getTop ( ) {

            return mTop;
      }

      public int getRight ( ) {

            return mRight;
      }

      public int getBottom ( ) {

            return mBottom;
      }

      public float getRotation ( ) {

            return mRotation;
      }

      public float getRotationX ( ) {

            return mRotationX;
      }

      public float getRotationY ( ) {

            return mRotationY;
      }

      public float getAlpha ( ) {

            return mAlpha;
      }

      public void setLeft ( int left ) {

            this.mLeft = left;
      }

      public void setTop ( int top ) {

            this.mTop = top;
      }

      public void setRight ( int right ) {

            this.mRight = right;
      }

      public void setBottom ( int bottom ) {

            this.mBottom = bottom;
      }

      public void setRotation ( float rotation ) {

            this.mRotation = rotation;
      }

      public void setRotationX ( float rotationX ) {

            mRotationX = rotationX;
      }

      public void setRotationY ( float rotationY ) {

            mRotationY = rotationY;
      }

      public void setAlpha ( @FloatRange(from = 0, to = 1f) float alpha ) {

            this.mAlpha = alpha;
      }

      public static void calculateDiff ( ViewVisionState start, ViewVisionState end, float progress, ViewVisionState result ) {

            /* 计算出当前的进度的值 */
            result.mLeft = (int) ( start.getLeft() + ( end.getLeft() - start.getLeft() ) * progress );
            result.mTop = (int) ( start.getTop() + ( end.getTop() - start.getTop() ) * progress );
            result.mRight = (int) ( start.getRight() + ( end.getRight() - start.getRight() ) * progress );
            result.mBottom = (int) ( start.getBottom() + ( end.getBottom() - start.getBottom() ) * progress );
            result.mRotation = start.getRotation() + ( end.getRotation() - start.getRotation() ) * progress;
            result.mRotationX = start.getRotationX() + ( end.getRotationX() - start.getRotationX() ) * progress;
            result.mRotationY = start.getRotationY() + ( end.getRotationY() - start.getRotationY() ) * progress;
            result.mAlpha = start.getAlpha() + ( end.getAlpha() - start.getAlpha() ) * progress;
      }

      /**
       * 根据一个view的显示状态，重新设置值
       *
       * @param view 新的状态的view
       */
      public void update ( View view ) {

            mLeft = view.getLeft();
            mTop = view.getTop();
            mRight = view.getRight();
            mBottom = view.getBottom();

            mRotation = view.getRotation();
            mAlpha = view.getAlpha();
      }

      /**
       * @param view 应用给view
       */
      public void applyTo ( View view ) {

            view.layout( mLeft, mTop, mRight, mBottom );
            view.setRotation( mRotation );
            view.setAlpha( mAlpha );
      }
}