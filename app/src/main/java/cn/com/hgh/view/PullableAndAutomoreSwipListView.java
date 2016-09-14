package cn.com.hgh.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

public class PullableAndAutomoreSwipListView extends ListView implements Pullable {
	
	//侧滑部分
	private Boolean mIsHorizontal;

    private View mPreItemView;

    private View mCurrentItemView;

    private float mFirstX;

    private float mFirstY;

    private int mRightViewWidth=0;

    // private boolean mIsInAnimation = false;
    private final int mDuration = 100;

    private final int mDurationStep = 10;

    private boolean mIsShown;

    //自动加载部分
    private View footView;
	private TextView noMoreTv;
	private ProgressBar pBar;
	private int hiddenheigt=-200;
    
    public PullableAndAutomoreSwipListView(Context context) {
        this(context,null);
        footView=inflate(context, R.layout.auto_load_more_listview_footview, null);
		noMoreTv=(TextView) footView.findViewById(R.id.load_nomore_tv);
		pBar = (ProgressBar) footView.findViewById(R.id.load_nomore_pb);
		setFootViseble(false,0);
		addFooterView(footView);
    }

    public PullableAndAutomoreSwipListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        footView=inflate(context, R.layout.auto_load_more_listview_footview, null);
		noMoreTv=(TextView) footView.findViewById(R.id.load_nomore_tv);
		pBar = (ProgressBar) footView.findViewById(R.id.load_nomore_pb);
		setFootViseble(false,0);
		addFooterView(footView);
    }

    public PullableAndAutomoreSwipListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        footView=inflate(context, R.layout.auto_load_more_listview_footview, null);
		noMoreTv=(TextView) footView.findViewById(R.id.load_nomore_tv);
		pBar = (ProgressBar) footView.findViewById(R.id.load_nomore_pb);
		setFootViseble(false,0);
		addFooterView(footView);
        
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,  
                R.styleable.swipelistviewstyle);  
        
      //获取自定义属性和默认值  
      mRightViewWidth = (int) mTypedArray.getDimension(R.styleable.swipelistviewstyle_right_width, 120);
      
      mTypedArray.recycle();  
    }

    /**
     *设置底部的显示与隐藏，为了没有数据时不出现底部footerview
     * @param visible
     * @param type
     */
    public void setFootViseble(boolean visible,int type) {
		
		if (visible) {
			if (type==1) {
				pBar.setVisibility(VISIBLE);
			}else {
				pBar.setVisibility(GONE);
			}
			footView.setPadding(0, 0, 0, 0); 
		}else {
			
			footView.setPadding(0, hiddenheigt-200, 0, 0); 
		}
		
	}
    
    /**
     * 是否此时已经不能自动加载
     * @return
     */
    public boolean isNomore() {
		
		if (pBar.getVisibility()==VISIBLE) {
			return false;
		};
		return true;
	}
    
    /**
	 * 设置底部显示方式
	 * @param nomore 0,隐藏1，显示加载更多2，显示没有更多数据
	 */
	public void setNomore(int nomore){
		switch (nomore) {
		case 0:
			setFootViseble(false,0);
			break;
		case 1:
			noMoreTv.setText("正在加载更多");
			setFootViseble(true,1);
			break;
		case 2:
			setFootViseble(true,2);
			noMoreTv.setText("没有更多内容");
			break;
		default:
			break;
		}
		
	};
    
    
    /**
     * return true, deliver to listView. return false, deliver to child. if
     * move, return true
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float lastX = ev.getX();
        float lastY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsHorizontal = null;
                System.out.println("onInterceptTouchEvent----->ACTION_DOWN");
                mFirstX = lastX;
                mFirstY = lastY;
                int motionPosition = pointToPosition((int)mFirstX, (int)mFirstY);

                if (motionPosition >= 0) {
                    View currentItemView = getChildAt(motionPosition - getFirstVisiblePosition());
                    mPreItemView = mCurrentItemView;
                    mCurrentItemView = currentItemView;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = lastX - mFirstX;
                float dy = lastY - mFirstY;

                if (Math.abs(dx) >= 5 && Math.abs(dy) >= 5) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                System.out.println("onInterceptTouchEvent----->ACTION_UP");
                if (mIsShown && (mPreItemView != mCurrentItemView || isHitCurItemLeft(lastX))) {
                    System.out.println("1---> hiddenRight");
                    /**
                     * 情况一：
                     * <p>
                     * 一个Item的右边布局已经显示，
                     * <p>
                     * 这时候点击任意一个item, 那么那个右边布局显示的item隐藏其右边布局
                     */
                    hiddenRight(mPreItemView);
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isHitCurItemLeft(float x) {
        return x < getWidth() - mRightViewWidth;
    }

    /**
     * @param dx
     * @param dy
     * @return judge if can judge scroll direction
     */
    private boolean judgeScrollDirection(float dx, float dy) {
        boolean canJudge = true;

        if (Math.abs(dx) > 30 && Math.abs(dx) >  Math.abs(dy)) {
            mIsHorizontal = true;
            System.out.println("mIsHorizontal---->" + mIsHorizontal);
        } else if (Math.abs(dy) > 30 && Math.abs(dy) > Math.abs(dx)) {
            mIsHorizontal = false;
            System.out.println("mIsHorizontal---->" + mIsHorizontal);
        } else {
            canJudge = false;
        }

        return canJudge;
    }

    /**
     * return false, can't move any direction. return true, cant't move
     * vertical, can move horizontal. return super.onTouchEvent(ev), can move
     * both.
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float lastX = ev.getX();
        float lastY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("---->ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = lastX - mFirstX;
                float dy = lastY - mFirstY;

                // confirm is scroll direction
                if (mIsHorizontal == null) {
                    if (!judgeScrollDirection(dx, dy)) {
                        break;
                    }
                }

                if (mIsHorizontal) {
                    if (mIsShown && mPreItemView != mCurrentItemView) {
                        System.out.println("2---> hiddenRight");
                        /**
                         * 情况二：
                         * <p>
                         * 一个Item的右边布局已经显示，
                         * <p>
                         * 这时候左右滑动另外一个item,那个右边布局显示的item隐藏其右边布局
                         * <p>
                         * 向左滑动只触发该情况，向右滑动还会触发情况五
                         */
                        hiddenRight(mPreItemView);
                    }

                    if (mIsShown && mPreItemView == mCurrentItemView) {
                        dx = dx - mRightViewWidth;
                        System.out.println("======dx " + dx);
                    }

                    // can't move beyond boundary
                    if (dx < 0 && dx > -mRightViewWidth) {
                    	if (mCurrentItemView!=null) {
                    		mCurrentItemView.scrollTo((int)(-dx), 0);
						}
                        
                    }

                    return true;
                } else {
                    if (mIsShown) {
                        System.out.println("3---> hiddenRight");
                        /**
                         * 情况三：
                         * <p>
                         * 一个Item的右边布局已经显示，
                         * <p>
                         * 这时候上下滚动ListView,那么那个右边布局显示的item隐藏其右边布局
                         */
                        hiddenRight(mPreItemView);
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                System.out.println("============ACTION_UP");
                clearPressedState();
                if (mIsShown) {
                    System.out.println("4---> hiddenRight");
                    /**
                     * 情况四：
                     * <p>
                     * 一个Item的右边布局已经显示，
                     * <p>
                     * 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
                     */
                    hiddenRight(mPreItemView);
                }

                if (mIsHorizontal != null && mIsHorizontal) {
                    if (mFirstX - lastX > mRightViewWidth / 2) {
                        showRight(mCurrentItemView);
                    } else {
                        System.out.println("5---> hiddenRight");
                        /**
                         * 情况五：
                         * <p>
                         * 向右滑动一个item,且滑动的距离超过了右边View的宽度的一半，隐藏之。
                         */
                        hiddenRight(mCurrentItemView);
                    }

                    return true;
                }

                break;
        }

        return super.onTouchEvent(ev);
    }
    /**
     * 滑动会原来的位置
     */
    private void scrollBack() {
        if (mIsShown) {
            /**
             * 一个Item的右边布局已经显示，
             * <p>
             * 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
             */
            hiddenRight(mPreItemView);
        }
    }
    /**
     * 提供给外部调用，用以将侧滑出来的滑回去
     */
    public void slideBack() {
        this.scrollBack();
    }
    private void clearPressedState() {
    	if (mCurrentItemView!=null) {
    		mCurrentItemView.setPressed(false);
            setPressed(false);
            refreshDrawableState();
		}
        
        // invalidate();
    }

    private void showRight(View view) {
        System.out.println("=========showRight");
        if (mCurrentItemView!=null) {
        	 Message msg = new MoveHandler().obtainMessage();
             msg.obj = view;
             msg.arg1 = view.getScrollX();
             msg.arg2 = mRightViewWidth;
             msg.sendToTarget();
             mIsShown = true;
		}
       
    }

    private void hiddenRight(View view) {
        System.out.println("=========hiddenRight");
        if (view == null) {
            return;
        }
        Message msg = new MoveHandler().obtainMessage();//
        msg.obj = view;
        msg.arg1 = view.getScrollX();
        msg.arg2 = 0;

        msg.sendToTarget();

        mIsShown = false;
    }

    /**
     * show or hide right layout animation
     */
    @SuppressLint("HandlerLeak")
    class MoveHandler extends Handler {
        int stepX = 0;

        int fromX;

        int toX;

        View view;

        private boolean mIsInAnimation = false;

        private void animatioOver() {
            mIsInAnimation = false;
            stepX = 0;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (stepX == 0) {
                if (mIsInAnimation) {
                    return;
                }
                mIsInAnimation = true;
                view = (View)msg.obj;
                fromX = msg.arg1;
                toX = msg.arg2;
                stepX = (int)((toX - fromX) * mDurationStep * 1.0 / mDuration);
                if (stepX < 0 && stepX > -1) {
                    stepX = -1;
                } else if (stepX > 0 && stepX < 1) {
                    stepX = 1;
                }
                if (Math.abs(toX - fromX) < 10) {
                    view.scrollTo(toX, 0);
                    animatioOver();
                    return;
                }
            }

            fromX += stepX;
            boolean isLastStep = (stepX > 0 && fromX > toX) || (stepX < 0 && fromX < toX);
            if (isLastStep) {
                fromX = toX;
            }

            view.scrollTo(fromX, 0);
            invalidate();

            if (!isLastStep) {
                this.sendEmptyMessageDelayed(0, mDurationStep);
            } else {
                animatioOver();
            }
        }
    }

    public int getRightViewWidth() {
        return mRightViewWidth;
    }

    public void setRightViewWidth(int mRightViewWidth) {
        this.mRightViewWidth = mRightViewWidth;
    }

    @Override
	public boolean canPullDown()
	{
		if (getCount() == 0)
		{
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0)
		{
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp()
	{
		/*if (getCount() == 0)
		{
			// 没有item的时候也可以上拉加载
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1))
		{
			// 滑到底部了
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}*/
		return false;//不能上拉
	}
	
	/**
	 * 限制滑到最顶或最底部时继续滑动的高度为100
	 */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// TODO Auto-generated method stub
			  
		    return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, 160, isTouchEvent);
		  
	}
}
