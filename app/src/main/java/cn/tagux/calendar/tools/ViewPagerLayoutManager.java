package cn.tagux.calendar.tools;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;

public class ViewPagerLayoutManager extends LinearLayoutManager {
  private static final String TAG = "ViewPagerLayoutManager";
  private PagerSnapHelper mPagerSnapHelper;
  private OnViewPagerListener mOnViewPagerListener;
  private RecyclerView mRecyclerView;
  private int mDrift;//位移，用来判断移动方向

  //缩放量
  private final float mShrinkAmount = 0.15f;

  private final float mShrinkDistance = 0.9f;

  public ViewPagerLayoutManager(Context context, int orientation) {
    super(context, orientation, false);
    init();
  }

  public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
    super(context, orientation, reverseLayout);
    init();
  }

  private void init() {
    mPagerSnapHelper = new PagerSnapHelper();
  }

  @Override
  public void onAttachedToWindow(RecyclerView view) {
    super.onAttachedToWindow(view);

    mPagerSnapHelper.attachToRecyclerView(view);
    this.mRecyclerView = view;
    mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
  }

  /**
   * 滑动状态的改变
   * 缓慢拖拽-> SCROLL_STATE_DRAGGING
   * 快速滚动-> SCROLL_STATE_SETTLING
   * 空闲状态-> SCROLL_STATE_IDLE
   *
   * @param state
   */
  @Override
  public void onScrollStateChanged(int state) {
    View view = mPagerSnapHelper.findSnapView(this);
    switch (state) {
      case RecyclerView.SCROLL_STATE_IDLE:
        if (view != null) {
          int positionIdle = getPosition(view);
          int childCount = getChildCount();
          if (mOnViewPagerListener != null && getChildCount() <= 2) {
            mOnViewPagerListener.onPageSelected(positionIdle,
                positionIdle == getItemCount() - 1);
          }
        }
        // if (view != null) {
        //   view.animate().scaleX(1F).scaleY(1F).start(); }
        break;
      case SCROLL_STATE_DRAGGING:
        // if (view != null) {
        //   view.animate()
        //       .scaleY(.8F)
        //       .scaleX(.8F)
        //       .start();
        // }
        break;
      default:
        break;
    }
  }

  /**
   * 监听竖直方向的相对偏移量
   *
   * @param dy
   * @param recycler
   * @param state
   * @return
   */
  @Override
  public int scrollVerticallyBy(int dy,
                                RecyclerView.Recycler recycler,
                                RecyclerView.State state) {
    this.mDrift = dy;
    return super.scrollVerticallyBy(dy, recycler, state);
  }

  /**
   * 监听水平方向的相对偏移量
   *
   * @param dx
   * @param recycler
   * @param state`
   * @return
   */
  @Override
  public int scrollHorizontallyBy(int dx,
                                  RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {
    this.mDrift = dx;

    int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

    float midpoint = getWidth() / 2f;

    float d0 = 0f;
    float d1 = mShrinkDistance * midpoint;
    float s0 = 1f;
    float s1 = 1f - mShrinkAmount;

    for (int i = 0; i < getChildCount(); i++) {
      View child = getChildAt(i);
      if (child == null) continue;
      float childMidpoint = (getDecoratedRight(child) +
          getDecoratedLeft(child)) / 2f;
      float d = Math.min(d1, Math.abs(midpoint - childMidpoint));

      float scale = s0 + (s1 - s0) *
          (d - d0) / (d1 - d0);
      child.setScaleX(scale);
      child.setScaleY(scale);
    }
    return scrolled;

  }

  /**
   * if return >= 0 snap is exist
   * if return < 0 snap is not exist
   *
   * @return
   */
  public int findSnapPosition() {
    View viewIdle = mPagerSnapHelper.findSnapView(this);
    if (viewIdle != null) {
      return getPosition(viewIdle);
    } else {
      return -1;
    }
  }

  /**
   * 设置监听
   *
   * @param listener
   */
  public void setOnViewPagerListener(OnViewPagerListener listener) {
    this.mOnViewPagerListener = listener;
  }

  private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener =
      new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
          if (mOnViewPagerListener != null && getChildCount() == 1) {
            mOnViewPagerListener.onInitComplete();
          }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
          if (mOnViewPagerListener != null) {
            if (mDrift >= 0) {
              mOnViewPagerListener.onPageRelease(true, getPosition(view));
            } else {
              mOnViewPagerListener.onPageRelease(false, getPosition(view));
            }
          }

        }
      };

  public interface OnViewPagerListener {

    /**
     * 初始化第一个View
     */
    void onInitComplete();

    /**
     * 选中的监听以及判断是否滑动到底部
     *
     * @param position
     * @param isBottom
     */
    void onPageSelected(int position, boolean isBottom);

    /**
     * 释放的监听
     *
     * @param isNext
     * @param position
     */
    void onPageRelease(boolean isNext, int position);
  }
}





