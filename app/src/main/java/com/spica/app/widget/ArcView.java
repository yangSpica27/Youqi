package com.spica.app.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;

/**
 * @ClassName ArcView
 * @Description TODO
 * @Author Spica2 7
 * @Date 2021/12/9 15:12
 */
public class ArcView extends View {

  //根据数据显示的圆弧Paint
  private Paint mArcPaint;
  //背景圆
  private Paint mBkgArcPaint;
  //圆点
  private Paint mPCirclePaint;
  private Paint mCCirclePaint;
  private float radius = dp2px(6);
  //渐变处理
  private LinearGradient linearGradient;
  //渐变的颜色
  private int[] changeColors = new int[]{ Color.parseColor("#FFFFFF"), Color.parseColor("#00FFFFFF")};
  //文字描述的paint
  private Paint mTextPaint;
  //圆弧开始的角度
  private float startAngle = 170;
  //圆弧结束的角度
  private float endAngle = 45;
  //圆弧背景的开始和结束间的夹角大小
  private float mAngle = 200;
  //当前进度夹角大小
  private float mIncludedAngle = 0;
  //圆弧的画笔的宽度
  private float mStrokeWith = 4;
  //断点圆弧的画笔的宽度
  private float mPointStrokeWith = 1;
  //中心的文字描述
  private String mDes = "";
  //中心的文字描述边上的百分号
  private String mChar = "%";
  //动画效果的数据及最大/小值
  private int mAnimatorValue, mMinValue, mMaxValue;
  //中心点的XY坐标
  private float centerX, centerY;
  //最外层大圆的半径
  private float pRadius;
  private float height, width;
  //动画时间
  private int time = 10000;

  public ArcView(Context context) {
    this(context, null);
    //初始化paint
    initPaint();
  }

  public ArcView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
    //初始化paint
    initPaint();
  }

  public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //初始化paint
    initPaint();
  }

  private void initPaint() {
    //圆弧的paint
    mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //抗锯齿
    mArcPaint.setAntiAlias(true);
    //圆弧的背景色
    mArcPaint.setColor(Color.parseColor("#14FFFFFF"));
    //设置透明度（数值为0-255）
    //        mArcPaint.setAlpha(100);
    //设置画笔的画出的形状
    mArcPaint.setStrokeJoin(Paint.Join.ROUND);
    mArcPaint.setStrokeCap(Paint.Cap.ROUND);
    //设置画笔类型
    mArcPaint.setStyle(Paint.Style.STROKE);
    mArcPaint.setStrokeWidth(dp2px(mStrokeWith));

    //夫圆点
    mPCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //抗锯齿
    mPCirclePaint.setAntiAlias(true);
    //圆弧的背景色
    mPCirclePaint.setColor(Color.parseColor("#FFFFFF"));
    //设置画笔的画出的形状
    mPCirclePaint.setStrokeJoin(Paint.Join.ROUND);
    mPCirclePaint.setStrokeCap(Paint.Cap.ROUND);
    //设置画笔类型
    mPCirclePaint.setStyle(Paint.Style.FILL);

    //子圆点
    mCCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //抗锯齿
    mCCirclePaint.setAntiAlias(true);
    //圆弧的背景色
    mCCirclePaint.setColor(Color.parseColor("#FF6672"));
    //设置画笔的画出的形状
    mCCirclePaint.setStrokeJoin(Paint.Join.ROUND);
    mCCirclePaint.setStrokeCap(Paint.Cap.ROUND);
    //设置画笔类型
    mCCirclePaint.setStyle(Paint.Style.FILL);


    //背景填充圆弧的paint
    mBkgArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //抗锯齿
    mBkgArcPaint.setAntiAlias(true);
    //圆弧的背景色
    mBkgArcPaint.setColor(Color.parseColor("#4DFFFFFF"));
    //设置画笔类型
    mBkgArcPaint.setStyle(Paint.Style.FILL);

    //中心文字的paint
    mTextPaint = new Paint();
    mTextPaint.setAntiAlias(true);
    mTextPaint.setColor(Color.parseColor("#FFFFFF"));
    //设置文本的对齐方式
    mTextPaint.setTextAlign(Paint.Align.CENTER);
    //mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp_12));
    mTextPaint.setTextSize(dp2px(40));

  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    height = h;
    width = w;
    pRadius = w / 2;
    centerX = w / 2;
    centerY = h / 2;
  }

  @SuppressLint("DrawAllocation")
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //绘制弧度
    drawArc(canvas);
    //绘制断点弧度
    drawPointArc(canvas);
    //绘制背景圆
    drawBkgArc(canvas);
    //绘制文本
    drawText(canvas);
  }

  /**
   * 绘制背景圆
   *
   * @param canvas
   */
  private void drawBkgArc(Canvas canvas) {
    //绘制圆弧背景
    //渐变背景
    linearGradient = new LinearGradient(0, dp2px(40), 0, height / 2, changeColors, null, Shader.TileMode.CLAMP);
    mBkgArcPaint.setShader(linearGradient);
    RectF mRectF = new RectF(mPointStrokeWith + dp2px(33), mPointStrokeWith + dp2px(33), getWidth() - mPointStrokeWith - dp2px(33), getHeight() - mPointStrokeWith - dp2px(33));
    canvas.drawArc(mRectF, startAngle, mAngle, false, mBkgArcPaint);
  }


  /**
   * 绘制文本
   *
   * @param canvas
   */
  private void drawText(Canvas canvas) {
    Rect mRect = new Rect();
    String mValue = String.valueOf(mAnimatorValue);
    //绘制中心的数值
    mTextPaint.setTextSize(dp2px(40));
    mTextPaint.getTextBounds(mValue, 0, mValue.length(), mRect);
    canvas.drawText(String.valueOf(mAnimatorValue), centerX, centerY - 0.2f * mRect.height(), mTextPaint);

    //绘制中心数值的百分比
    mTextPaint.setTextSize(dp2px(20));
    mTextPaint.getTextBounds(mValue, 0, mValue.length(), mRect);
    //x轴：centerX+绘制中心的数值的宽度/2
    canvas.drawText(mChar, centerX + width / 5, centerY - height / 30, mTextPaint);
    //        canvas.drawText(mChar, centerX + mRect.width() / 2.0f + 1.6f * mRect.height(), centerY - 0.8f * mRect.height(), mTextPaint);

    //绘制中心文字描述
    mTextPaint.setTextSize(dp2px(14));
    mTextPaint.getTextBounds(mDes, 0, mDes.length(), mRect);
    canvas.drawText(mDes, centerX, centerY + 1.6f * mRect.height(), mTextPaint);

    //绘制最小值
    //        String minValue=String.valueOf(mMinValue);
    //        String maxValue=String.valueOf(mMaxValue);
    //        mTextPaint.setTextSize(dp2px(18));
    //        mTextPaint.getTextBounds(minValue,0,minValue.length(),mRect);
    //        canvas.drawText(minValue, (float) (centerX-0.6*centerX-dp2px(5)), (float) (centerY+0.75*centerY+mRect.height()+dp2px(5)),mTextPaint);
    //        //绘制最大指
    //        mTextPaint.getTextBounds(maxValue,0,maxValue.length(),mRect);
    //        canvas.drawText(maxValue, (float) (centerX+0.6*centerX+dp2px(5)), (float) (centerY+0.75*centerY+mRect.height()+dp2px(5)),mTextPaint);
  }

  /**
   * 绘制当前的断点圆弧
   *
   * @param canvas
   */
  private void drawPointArc(Canvas canvas) {
    //绘制圆弧背景
    mArcPaint.setStrokeWidth(dp2px(mPointStrokeWith));
    //设置虚线
    mArcPaint.setPathEffect(new DashPathEffect(new float[]{dp2px(1), dp2px(6)}, 0));
    RectF mRectF = new RectF(mPointStrokeWith + dp2px(20), mPointStrokeWith + dp2px(20), getWidth() - mPointStrokeWith - dp2px(20), getHeight() - mPointStrokeWith - dp2px(20));
    canvas.drawArc(mRectF, startAngle, mAngle, false, mArcPaint);
  }

  /**
   * 绘制当前的圆弧
   *
   * @param canvas
   */
  private void drawArc(Canvas canvas) {
    mArcPaint.setStrokeWidth(dp2px(mStrokeWith));
    mArcPaint.setPathEffect(null);
    //圆弧的背景色
    mArcPaint.setColor(Color.parseColor("#14FFFFFF"));
    //绘制圆弧背景
    RectF mRectF = new RectF(mStrokeWith + dp2px(5), mStrokeWith + dp2px(5), getWidth() - mStrokeWith - dp2px(5), getHeight() - mStrokeWith);
    canvas.drawArc(mRectF, startAngle, mAngle, false, mArcPaint);
    //绘制当前数值对应的圆弧
    mArcPaint.setColor(Color.parseColor("#FFFFFF"));
    //根据当前数据绘制对应的圆弧
    canvas.drawArc(mRectF, startAngle, mIncludedAngle, false, mArcPaint);
    //根据当前数据绘制对应的夫圆
    float radian = (float) Math.toRadians(startAngle + mIncludedAngle);
    float x = (float) (centerX + (pRadius - radius) * Math.cos(radian));
    float y = (float) (centerY + (pRadius - radius) * Math.sin(radian));
    canvas.drawCircle(x, y, radius, mPCirclePaint);
    //根据当前数据绘制对应的子圆
    canvas.drawCircle(x, y, radius / 2, mCCirclePaint);
  }

  /**
   * 为绘制弧度及数据设置动画
   *
   * @param startAngle   开始的弧度
   * @param currentAngle 需要绘制的弧度
   * @param currentValue 需要绘制的数据
   * @param time         动画执行的时长
   */
  private void setAnimation(float startAngle, float currentAngle, int currentValue, int time) {
    //绘制当前数据对应的圆弧的动画效果
    ValueAnimator progressAnimator = ValueAnimator.ofFloat(startAngle, currentAngle);
    progressAnimator.setDuration(time);
    progressAnimator.setTarget(mIncludedAngle);
    progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mIncludedAngle = (float) animation.getAnimatedValue();
        //重新绘制，不然不会出现效果
        postInvalidate();
      }
    });
    //开始执行动画
    progressAnimator.start();
    //中心数据的动画效果
    ValueAnimator valueAnimator = ValueAnimator.ofInt(mAnimatorValue, currentValue);
    valueAnimator.setDuration(time);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatorValue = (int) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });
    valueAnimator.start();
  }

  /**
   * 设置数据
   *
   * @param minValue     最小值
   * @param maxValue     最大值
   * @param currentValue 当前绘制的值
   * @param des          描述信息
   */
  public void setValues(int minValue, int maxValue, int currentValue, String des) {
    mDes = des;
    mMaxValue = maxValue;
    mMinValue = minValue;
    //完全覆盖
    if (currentValue > maxValue) {
      currentValue = maxValue;
    }
    //计算弧度比重
    float scale = (float) currentValue / maxValue;
    //计算弧度
    float currentAngle = scale * mAngle;
    //开始执行动画
    setAnimation(0, currentAngle, currentValue, time);
  }

  public float dp2px(float dp) {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return dp * metrics.density;
  }
}



