package com.youtubedl.ui.component.widget

import android.content.Context
import android.support.v4.view.*
import android.support.v4.widget.ScrollerCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.webkit.WebView

class NestedScrollWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle
) : WebView(context, attrs, defStyleAttr), NestedScrollingChild, NestedScrollingParent {

    private val mScrollOffset = IntArray(2)
    private val mScrollConsumed = IntArray(2)

    private var mLastMotionY: Int = 0
    private val mChildHelper: NestedScrollingChildHelper
    private var mIsBeingDragged = false
    private var mVelocityTracker: VelocityTracker? = null
    private var mTouchSlop: Int = 0
    private var mActivePointerId = INVALID_POINTER
    private var mNestedYOffset: Int = 0
    private var mScroller: ScrollerCompat? = null
    private var mMinimumVelocity: Int = 0
    private var mMaximumVelocity: Int = 0

    private val scrollRange: Int
        get() = computeVerticalScrollRange()

    init {
        overScrollMode = WebView.OVER_SCROLL_NEVER
        initScrollView()
        mChildHelper = NestedScrollingChildHelper(this)
        isNestedScrollingEnabled = true
    }

    private fun initScrollView() {
        mScroller = ScrollerCompat.create(context, null)
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val action = ev.action
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged) {
            return true
        }

        when (action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                val activePointerId = mActivePointerId
                val pointerIndex = ev.findPointerIndex(activePointerId)
                if (activePointerId != INVALID_POINTER && pointerIndex != INVALID_POINTER) {
                    val y = ev.getY(pointerIndex).toInt()
                    val yDiff = Math.abs(y - mLastMotionY)
                    if (yDiff > mTouchSlop && nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL == 0) {
                        mIsBeingDragged = true
                        mLastMotionY = y
                        initVelocityTrackerIfNotExists()
                        mVelocityTracker!!.addMovement(ev)
                        mNestedYOffset = 0
                        val parent = parent
                        parent?.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }

            MotionEvent.ACTION_DOWN -> {
                val y = ev.y.toInt()

                mLastMotionY = y
                mActivePointerId = ev.getPointerId(0)

                initOrResetVelocityTracker()
                mVelocityTracker!!.addMovement(ev)

                mScroller!!.computeScrollOffset()
                mIsBeingDragged = !mScroller!!.isFinished
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mIsBeingDragged = false
                mActivePointerId = INVALID_POINTER
                recycleVelocityTracker()
                if (mScroller!!.springBack(scrollX, scrollY, 0, 0, 0, scrollRange)) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                stopNestedScroll()
            }
            MotionEventCompat.ACTION_POINTER_UP -> onSecondaryPointerUp(ev)
        }

        return mIsBeingDragged
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        initVelocityTrackerIfNotExists()

        val vtev = MotionEvent.obtain(ev)

        val actionMasked = MotionEventCompat.getActionMasked(ev)

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0
        }
        vtev.offsetLocation(0f, mNestedYOffset.toFloat())

        when (actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (mIsBeingDragged == !mScroller!!.isFinished) {
                    val parent = parent
                    parent?.requestDisallowInterceptTouchEvent(true)
                }

                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }

                mLastMotionY = ev.y.toInt()
                mActivePointerId = ev.getPointerId(0)
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = ev.findPointerIndex(mActivePointerId)
                if (activePointerIndex != -1) {
                    val y = ev.getY(activePointerIndex).toInt()
                    var deltaY = mLastMotionY - y
                    if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                        deltaY -= mScrollConsumed[1]
                        vtev.offsetLocation(0f, mScrollOffset[1].toFloat())
                        mNestedYOffset += mScrollOffset[1]
                    }
                    if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                        val parent = parent
                        parent?.requestDisallowInterceptTouchEvent(true)
                        mIsBeingDragged = true
                        if (deltaY > 0) {
                            deltaY -= mTouchSlop
                        } else {
                            deltaY += mTouchSlop
                        }
                    }
                    if (mIsBeingDragged) {
                        mLastMotionY = y - mScrollOffset[1]

                        val oldY = scrollY
                        val scrolledDeltaY = scrollY - oldY
                        val unconsumedY = deltaY - scrolledDeltaY
                        if (dispatchNestedScroll(0, scrolledDeltaY, 0, unconsumedY, mScrollOffset)) {
                            mLastMotionY -= mScrollOffset[1]
                            vtev.offsetLocation(0f, mScrollOffset[1].toFloat())
                            mNestedYOffset += mScrollOffset[1]
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mIsBeingDragged) {
                    val velocityTracker = mVelocityTracker
                    velocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                    val initialVelocity = VelocityTrackerCompat.getYVelocity(
                        velocityTracker,
                        mActivePointerId
                    ).toInt()

                    if (Math.abs(initialVelocity) > mMinimumVelocity) {
                        flingWithNestedDispatch(-initialVelocity)
                    } else if (mScroller!!.springBack(
                            scrollX, scrollY, 0, 0, 0,
                            scrollRange
                        )
                    ) {
                        ViewCompat.postInvalidateOnAnimation(this)
                    }
                }
                mActivePointerId = INVALID_POINTER
                endDrag()
            }
            MotionEvent.ACTION_CANCEL -> {
                if (mIsBeingDragged && childCount > 0) {
                    if (mScroller!!.springBack(
                            scrollX, scrollY, 0, 0, 0,
                            scrollRange
                        )
                    ) {
                        ViewCompat.postInvalidateOnAnimation(this)
                    }
                }
                mActivePointerId = INVALID_POINTER
                endDrag()
            }
            MotionEventCompat.ACTION_POINTER_DOWN -> {
                val index = MotionEventCompat.getActionIndex(ev)
                mLastMotionY = ev.getY(index).toInt()
                mActivePointerId = ev.getPointerId(index)
            }
            MotionEventCompat.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(ev)
                mLastMotionY = ev.getY(ev.findPointerIndex(mActivePointerId)).toInt()
            }
        }

        if (mVelocityTracker != null) {
            mVelocityTracker!!.addMovement(vtev)
        }
        vtev.recycle()
        return super.onTouchEvent(ev)
    }

    private fun endDrag() {
        mIsBeingDragged = false

        recycleVelocityTracker()
        stopNestedScroll()
    }

    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex =
            ev.action and MotionEventCompat.ACTION_POINTER_INDEX_MASK shr MotionEventCompat.ACTION_POINTER_INDEX_SHIFT
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mLastMotionY = ev.getY(newPointerIndex).toInt()
            mActivePointerId = ev.getPointerId(newPointerIndex)
            if (mVelocityTracker != null) {
                mVelocityTracker!!.clear()
            }
        }
    }

    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else {
            mVelocityTracker!!.clear()
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    private fun flingWithNestedDispatch(velocityY: Int) {
        val scrollY = scrollY
        val canFling = (scrollY > 0 || velocityY > 0) && (scrollY < scrollRange || velocityY < 0)
        if (!dispatchNestedPreFling(0f, velocityY.toFloat())) {
            dispatchNestedFling(0f, velocityY.toFloat(), canFling)
            if (canFling) {
                fling(velocityY)
            }
        }
    }

    fun fling(velocityY: Int) {
        if (childCount > 0) {
            val height = height - paddingBottom - paddingTop
            val bottom = getChildAt(0).height

            mScroller!!.fling(
                scrollX, scrollY, 0, velocityY, 0, 0, 0,
                Math.max(0, bottom - height), 0, height / 2
            )

            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun getNestedScrollAxes(): Int {
        return ViewCompat.SCROLL_AXIS_NONE
    }

    companion object {
        private const val INVALID_POINTER = -1
    }
}