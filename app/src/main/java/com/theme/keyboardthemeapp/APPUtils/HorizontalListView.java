package com.theme.keyboardthemeapp.APPUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import androidx.core.view.ViewCompat;
import androidx.core.widget.EdgeEffectCompat;

import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HorizontalListView extends AdapterView<ListAdapter> {
    private static final int INSERT_AT_END_OF_LIST = -1;
    private static final int INSERT_AT_START_OF_LIST = 0;
    private static final float FLING_DEFAULT_ABSORB_VELOCITY = 30f;
    private static final float FLING_FRICTION = 0.009f;
    private static final String BUNDLE_ID_CURRENT_X = "BUNDLE_ID_CURRENT_X";
    private static final String BUNDLE_ID_PARENT_STATE = "BUNDLE_ID_PARENT_STATE";
    protected Scroller FlingTracker = new Scroller(getContext());
    private final GestureListener GestureListener = new GestureListener();
    private GestureDetector GestureDetector;
    private int DisplayOffset;
    protected ListAdapter Adapter;
    private List<Queue<View>> RemovedViewsCache = new ArrayList<Queue<View>>();
    private boolean DataChanged = false;
    private Rect Rect = new Rect();
    private View ViewBeingTouched = null;
    private int DividerWidth = 0;
    private Drawable Divider = null;
    protected int CurrentX;
    protected int NextX;
    private Integer RestoreX = null;
    private int MaxX = Integer.MAX_VALUE;
    private int LeftViewAdapterIndex;
    private int RightViewAdapterIndex;
    private int CurrentlySelectedAdapterIndex;
    private RunningOutOfDataListener RunningOutOfDataListener = null;
    private int RunningOutOfDataThreshold = 0;
    private boolean HasNotifiedRunningLowOnData = false;
    private OnScrollStateChangedListener onScrollStateChangedListener = null;
    private OnScrollStateChangedListener.ScrollState CurrentScrollState = OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE;
    private EdgeEffectCompat EdgeGlowLeft;
    private EdgeEffectCompat EdgeGlowRight;
    private int HeightMeasureSpec;
    private boolean BlockTouchAction = false;
    private boolean IsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = false;
    private OnClickListener OnClickListener;

    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        EdgeGlowLeft = new EdgeEffectCompat(context);
        EdgeGlowRight = new EdgeEffectCompat(context);
        GestureDetector = new GestureDetector(context, GestureListener);
        BindGestureDetector();
        InitView();
        RetrieveXmlConfiguration(context, attrs);
        setWillNotDraw(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            HoneycombPlus.setFriction(FlingTracker, FLING_FRICTION);
        }
    }

    private void BindGestureDetector() {
        final OnTouchListener gestureListenerHandler = (v, event) -> GestureDetector.onTouchEvent(event);
        setOnTouchListener(gestureListenerHandler);
    }

    private void requestParentListViewToNotInterceptTouchEvents(Boolean disallowIntercept) {
        if (IsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent != disallowIntercept) {
            View view = this;
            while (view.getParent() instanceof View) {
                if (view.getParent() instanceof ListView || view.getParent() instanceof ScrollView) {
                    view.getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
                    IsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = disallowIntercept;
                    return;
                }
                view = (View) view.getParent();
            }
        }
    }

    private void RetrieveXmlConfiguration(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalListView);
            final Drawable drawable = typedArray.getDrawable(R.styleable.HorizontalListView_android_divider);
            if (drawable != null) {
                setDivider(drawable);
            }
            final int size = typedArray.getDimensionPixelSize(R.styleable.HorizontalListView_dividerWidth, 0);
            if (size != 0) {
                setDividerWidth(size);
            }
            typedArray.recycle();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ID_PARENT_STATE, super.onSaveInstanceState());
        bundle.putInt(BUNDLE_ID_CURRENT_X, CurrentX);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            RestoreX = Integer.valueOf((bundle.getInt(BUNDLE_ID_CURRENT_X)));
            super.onRestoreInstanceState(bundle.getParcelable(BUNDLE_ID_PARENT_STATE));
        }
    }

    public void setDivider(Drawable divider) {
        Divider = divider;
        if (divider != null) {
            setDividerWidth(divider.getIntrinsicWidth());
        } else {
            setDividerWidth(0);
        }
    }

    public void setDividerWidth(int width) {
        DividerWidth = width;
        requestLayout();
        invalidate();
    }

    private void InitView() {
        LeftViewAdapterIndex = -1;
        RightViewAdapterIndex = -1;
        DisplayOffset = 0;
        CurrentX = 0;
        NextX = 0;
        MaxX = Integer.MAX_VALUE;
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }

    private void reset() {
        InitView();
        removeAllViewsInLayout();
        requestLayout();
    }

    private DataSetObserver mAdapterDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            DataChanged = true;
            HasNotifiedRunningLowOnData = false;
            unpressTouchedChild();
            invalidate();
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            HasNotifiedRunningLowOnData = false;
            unpressTouchedChild();
            reset();
            invalidate();
            requestLayout();
        }
    };

    @Override
    public void setSelection(int position) {
        CurrentlySelectedAdapterIndex = position;
    }

    @Override
    public View getSelectedView() {
        return getChild(CurrentlySelectedAdapterIndex);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (Adapter != null) {
            Adapter.unregisterDataSetObserver(mAdapterDataObserver);
        }
        if (adapter != null) {
            HasNotifiedRunningLowOnData = false;
            Adapter = adapter;
            Adapter.registerDataSetObserver(mAdapterDataObserver);
        }
        initializeRecycledViewCache(Adapter.getViewTypeCount());
        reset();
    }

    @Override
    public ListAdapter getAdapter() {
        return Adapter;
    }

    private void initializeRecycledViewCache(int viewTypeCount) {
        RemovedViewsCache.clear();
        for (int i = 0; i < viewTypeCount; i++) {
            RemovedViewsCache.add(new LinkedList<View>());
        }
    }
    private View getRecycledView(int adapterIndex) {
        int itemViewType = Adapter.getItemViewType(adapterIndex);
        if (isItemViewTypeValid(itemViewType)) {
            return RemovedViewsCache.get(itemViewType).poll();
        }
        return null;
    }

    private void recycleView(int adapterIndex, View view) {
        int itemViewType = Adapter.getItemViewType(adapterIndex);
        if (isItemViewTypeValid(itemViewType)) {
            RemovedViewsCache.get(itemViewType).offer(view);
        }
    }

    private boolean isItemViewTypeValid(int itemViewType) {
        return itemViewType < RemovedViewsCache.size();
    }

    private void addAndMeasureChild(final View child, int viewPos) {
        LayoutParams params = getLayoutParams(child);
        addViewInLayout(child, viewPos, params, true);
        measureChild(child);
    }

    private void measureChild(View child) {
        LayoutParams childLayoutParams = getLayoutParams(child);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(HeightMeasureSpec, getPaddingTop() + getPaddingBottom(), childLayoutParams.height);
        int childWidthSpec;
        if (childLayoutParams.width > 0) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(childLayoutParams.width, MeasureSpec.EXACTLY);
        } else {
            childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        child.measure(childWidthSpec, childHeightSpec);
    }

    private LayoutParams getLayoutParams(View child) {
        LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        }

        return layoutParams;
    }

    @SuppressLint("WrongCall")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (Adapter == null) {
            return;
        }

        invalidate();

        if (DataChanged) {
            int oldCurrentX = CurrentX;
            InitView();
            removeAllViewsInLayout();
            NextX = oldCurrentX;
            DataChanged = false;
        }

        if (RestoreX != null) {
            NextX = RestoreX;
            RestoreX = null;
        }

        if (FlingTracker.computeScrollOffset()) {
            NextX = FlingTracker.getCurrX();
        }

        if (NextX < 0) {
            NextX = 0;

            if (EdgeGlowLeft.isFinished()) {
                EdgeGlowLeft.onAbsorb((int) determineFlingAbsorbVelocity());
            }

            FlingTracker.forceFinished(true);
            setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        } else if (NextX > MaxX) {
            NextX = MaxX;

            if (EdgeGlowRight.isFinished()) {
                EdgeGlowRight.onAbsorb((int) determineFlingAbsorbVelocity());
            }

            FlingTracker.forceFinished(true);
            setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        }

        int dx = CurrentX - NextX;
        removeNonVisibleChildren(dx);
        fillList(dx);
        positionChildren(dx);

        CurrentX = NextX;

        if (determineMaxX()) {
            onLayout(changed, left, top, right, bottom);
            return;
        }

        if (FlingTracker.isFinished()) {
            if (CurrentScrollState == OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
        } else {
            ViewCompat.postOnAnimation(this, mDelayedLayout);
        }
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();

        if (CurrentX == 0) {
            return 0;
        } else if (CurrentX < horizontalFadingEdgeLength) {
            return (float) CurrentX / horizontalFadingEdgeLength;
        } else {
            return 1;
        }
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();

        if (CurrentX == MaxX) {
            return 0;
        } else if ((MaxX - CurrentX) < horizontalFadingEdgeLength) {
            return (float) (MaxX - CurrentX) / horizontalFadingEdgeLength;
        } else {
            return 1;
        }
    }

    private float determineFlingAbsorbVelocity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return IceCreamSandwichPlus.getCurrVelocity(FlingTracker);
        } else {
            return FLING_DEFAULT_ABSORB_VELOCITY;
        }
    }

    private Runnable mDelayedLayout = () -> requestLayout();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        HeightMeasureSpec = heightMeasureSpec;
    };

    private boolean determineMaxX() {
        if (isLastItemInAdapter(RightViewAdapterIndex)) {
            View rightView = getRightmostChild();

            if (rightView != null) {
                int oldMaxX = MaxX;

                MaxX = CurrentX + (rightView.getRight() - getPaddingLeft()) - getRenderWidth();

                if (MaxX < 0) {
                    MaxX = 0;
                }

                if (MaxX != oldMaxX) {
                    return true;
                }
            }
        }

        return false;
    }

    private void fillList(final int dx) {
        int edge = 0;
        View child = getRightmostChild();
        if (child != null) {
            edge = child.getRight();
        }

        fillListRight(edge, dx);

        edge = 0;
        child = getLeftmostChild();
        if (child != null) {
            edge = child.getLeft();
        }

        fillListLeft(edge, dx);
    }

    private void removeNonVisibleChildren(final int dx) {
        View child = getLeftmostChild();

        while (child != null && child.getRight() + dx <= 0) {
            DisplayOffset += isLastItemInAdapter(LeftViewAdapterIndex) ? child.getMeasuredWidth() : DividerWidth + child.getMeasuredWidth();

            recycleView(LeftViewAdapterIndex, child);

            removeViewInLayout(child);

            LeftViewAdapterIndex++;

            child = getLeftmostChild();
        }

        child = getRightmostChild();

        while (child != null && child.getLeft() + dx >= getWidth()) {
            recycleView(RightViewAdapterIndex, child);
            removeViewInLayout(child);
            RightViewAdapterIndex--;
            child = getRightmostChild();
        }
    }

    private void fillListRight(int rightEdge, final int dx) {
        while (rightEdge + dx + DividerWidth < getWidth() && RightViewAdapterIndex + 1 < Adapter.getCount()) {
            RightViewAdapterIndex++;

            if (LeftViewAdapterIndex < 0) {
                LeftViewAdapterIndex = RightViewAdapterIndex;
            }

            View child = Adapter.getView(RightViewAdapterIndex, getRecycledView(RightViewAdapterIndex), this);
            addAndMeasureChild(child, INSERT_AT_END_OF_LIST);

            rightEdge += (RightViewAdapterIndex == 0 ? 0 : DividerWidth) + child.getMeasuredWidth();

            determineIfLowOnData();
        }
    }

    private void fillListLeft(int leftEdge, final int dx) {
        while (leftEdge + dx - DividerWidth > 0 && LeftViewAdapterIndex >= 1) {
            LeftViewAdapterIndex--;
            View child = Adapter.getView(LeftViewAdapterIndex, getRecycledView(LeftViewAdapterIndex), this);
            addAndMeasureChild(child, INSERT_AT_START_OF_LIST);

            leftEdge -= LeftViewAdapterIndex == 0 ? child.getMeasuredWidth() : DividerWidth + child.getMeasuredWidth();

            DisplayOffset -= leftEdge + dx == 0 ? child.getMeasuredWidth() : DividerWidth + child.getMeasuredWidth();
        }
    }

    private void positionChildren(final int dx) {
        int childCount = getChildCount();

        if (childCount > 0) {
            DisplayOffset += dx;
            int leftOffset = DisplayOffset;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int left = leftOffset + getPaddingLeft();
                int top = getPaddingTop();
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();

                child.layout(left, top, right, bottom);

                leftOffset += child.getMeasuredWidth() + DividerWidth;
            }
        }
    }

    private View getLeftmostChild() {
        return getChildAt(0);
    }

    private View getRightmostChild() {
        return getChildAt(getChildCount() - 1);
    }

    private View getChild(int adapterIndex) {
        if (adapterIndex >= LeftViewAdapterIndex && adapterIndex <= RightViewAdapterIndex) {
            return getChildAt(adapterIndex - LeftViewAdapterIndex);
        }

        return null;
    }

    private int getChildIndex(final int x, final int y) {
        int childCount = getChildCount();

        for (int index = 0; index < childCount; index++) {
            getChildAt(index).getHitRect(Rect);
            if (Rect.contains(x, y)) {
                return index;
            }
        }

        return -1;
    }

    private boolean isLastItemInAdapter(int index) {
        return index == Adapter.getCount() - 1;
    }

    private int getRenderHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int getRenderWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public void scrollTo(int x) {
        FlingTracker.startScroll(NextX, 0, x - NextX, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
    }

    @Override
    public int getFirstVisiblePosition() {
        return LeftViewAdapterIndex;
    }

    @Override
    public int getLastVisiblePosition() {
        return RightViewAdapterIndex;
    }

    private void drawEdgeGlow(Canvas canvas) {
        if (EdgeGlowLeft != null && !EdgeGlowLeft.isFinished() && isEdgeGlowEnabled()) {
            final int restoreCount = canvas.save();
            final int height = getHeight();

            canvas.rotate(-90, 0, 0);
            canvas.translate(-height + getPaddingBottom(), 0);

            EdgeGlowLeft.setSize(getRenderHeight(), getRenderWidth());
            if (EdgeGlowLeft.draw(canvas)) {
                invalidate();
            }

            canvas.restoreToCount(restoreCount);
        } else if (EdgeGlowRight != null && !EdgeGlowRight.isFinished() && isEdgeGlowEnabled()) {
            final int restoreCount = canvas.save();
            final int width = getWidth();

            canvas.rotate(90, 0, 0);
            canvas.translate(getPaddingTop(), -width);
            EdgeGlowRight.setSize(getRenderHeight(), getRenderWidth());
            if (EdgeGlowRight.draw(canvas)) {
                invalidate();
            }

            canvas.restoreToCount(restoreCount);
        }
    }

    private void drawDividers(Canvas canvas) {
        final int count = getChildCount();

        final Rect bounds = Rect;
        Rect.top = getPaddingTop();
        Rect.bottom = Rect.top + getRenderHeight();

        for (int i = 0; i < count; i++) {
            if (!(i == count - 1 && isLastItemInAdapter(RightViewAdapterIndex))) {
                View child = getChildAt(i);

                bounds.left = child.getRight();
                bounds.right = child.getRight() + DividerWidth;

                if (bounds.left < getPaddingLeft()) {
                    bounds.left = getPaddingLeft();
                }

                if (bounds.right > getWidth() - getPaddingRight()) {
                    bounds.right = getWidth() - getPaddingRight();
                }

                drawDivider(canvas, bounds);

                if (i == 0 && child.getLeft() > getPaddingLeft()) {
                    bounds.left = getPaddingLeft();
                    bounds.right = child.getLeft();
                    drawDivider(canvas, bounds);
                }
            }
        }
    }

    private void drawDivider(Canvas canvas, Rect bounds) {
        if (Divider != null) {
            Divider.setBounds(bounds);
            Divider.draw(canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDividers(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawEdgeGlow(canvas);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
    }

    protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        FlingTracker.fling(NextX, 0, (int) -velocityX, 0, 0, MaxX, 0, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
        return true;
    }

    protected boolean onDown(MotionEvent e) {
        BlockTouchAction = !FlingTracker.isFinished();

        FlingTracker.forceFinished(true);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);

        unpressTouchedChild();

        if (!BlockTouchAction) {
            final int index = getChildIndex((int) e.getX(), (int) e.getY());
            if (index >= 0) {
                ViewBeingTouched = getChildAt(index);

                if (ViewBeingTouched != null) {
                    ViewBeingTouched.setPressed(true);
                    refreshDrawableState();
                }
            }
        }

        return true;
    }

    private void unpressTouchedChild() {
        if (ViewBeingTouched != null) {
            ViewBeingTouched.setPressed(false);
            refreshDrawableState();

            ViewBeingTouched = null;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return HorizontalListView.this.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return HorizontalListView.this.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            requestParentListViewToNotInterceptTouchEvents(true);

            setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_TOUCH_SCROLL);
            unpressTouchedChild();
            NextX += (int) distanceX;
            updateOverscrollAnimation(Math.round(distanceX));
            requestLayout();

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            unpressTouchedChild();
            OnItemClickListener onItemClickListener = getOnItemClickListener();

            final int index = getChildIndex((int) e.getX(), (int) e.getY());

            if (index >= 0 && !BlockTouchAction) {
                View child = getChildAt(index);
                int adapterIndex = LeftViewAdapterIndex + index;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(HorizontalListView.this, child, adapterIndex, Adapter.getItemId(adapterIndex));
                    return true;
                }
            }

            if (OnClickListener != null && !BlockTouchAction) {
                OnClickListener.onClick(HorizontalListView.this);
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            unpressTouchedChild();

            final int index = getChildIndex((int) e.getX(), (int) e.getY());
            if (index >= 0 && !BlockTouchAction) {
                View child = getChildAt(index);
                OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
                if (onItemLongClickListener != null) {
                    int adapterIndex = LeftViewAdapterIndex + index;
                    boolean handled = onItemLongClickListener.onItemLongClick(HorizontalListView.this, child, adapterIndex, Adapter
                            .getItemId(adapterIndex));

                    if (handled) {
                        performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    }
                }
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (FlingTracker == null || FlingTracker.isFinished()) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }

            requestParentListViewToNotInterceptTouchEvents(false);

            releaseEdgeGlow();
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            unpressTouchedChild();
            releaseEdgeGlow();

            requestParentListViewToNotInterceptTouchEvents(false);
        }

        return super.onTouchEvent(event);
    }

    private void releaseEdgeGlow() {
        if (EdgeGlowLeft != null) {
            EdgeGlowLeft.onRelease();
        }

        if (EdgeGlowRight != null) {
            EdgeGlowRight.onRelease();
        }
    }

    public void setRunningOutOfDataListener(RunningOutOfDataListener listener, int numberOfItemsLeftConsideredLow) {
        RunningOutOfDataListener = listener;
        RunningOutOfDataThreshold = numberOfItemsLeftConsideredLow;
    }

    public static interface RunningOutOfDataListener {
        void onRunningOutOfData();
    }

    private void determineIfLowOnData() {
        if (RunningOutOfDataListener != null && Adapter != null &&
                Adapter.getCount() - (RightViewAdapterIndex + 1) < RunningOutOfDataThreshold) {

            if (!HasNotifiedRunningLowOnData) {
                HasNotifiedRunningLowOnData = true;
                RunningOutOfDataListener.onRunningOutOfData();
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        OnClickListener = listener;
    }

    public interface OnScrollStateChangedListener {
        public enum ScrollState {
            SCROLL_STATE_IDLE,
            SCROLL_STATE_TOUCH_SCROLL,

            SCROLL_STATE_FLING
        }

        public void onScrollStateChanged(ScrollState scrollState);
    }

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        onScrollStateChangedListener = listener;
    }

    private void setCurrentScrollState(OnScrollStateChangedListener.ScrollState newScrollState) {
        if (CurrentScrollState != newScrollState && onScrollStateChangedListener != null) {
            onScrollStateChangedListener.onScrollStateChanged(newScrollState);
        }

        CurrentScrollState = newScrollState;
    }

    private void updateOverscrollAnimation(final int scrolledOffset) {
        if (EdgeGlowLeft == null || EdgeGlowRight == null) return;

        int nextScrollPosition = CurrentX + scrolledOffset;

        if (FlingTracker == null || FlingTracker.isFinished()) {
            if (nextScrollPosition < 0) {

                int overscroll = Math.abs(scrolledOffset);

                EdgeGlowLeft.onPull((float) overscroll / getRenderWidth());

                if (!EdgeGlowRight.isFinished()) {
                    EdgeGlowRight.onRelease();
                }
            } else if (nextScrollPosition > MaxX) {

                int overscroll = Math.abs(scrolledOffset);

                EdgeGlowRight.onPull((float) overscroll / getRenderWidth());

                if (!EdgeGlowLeft.isFinished()) {
                    EdgeGlowLeft.onRelease();
                }
            }
        }
    }

    private boolean isEdgeGlowEnabled() {
        if (Adapter == null || Adapter.isEmpty()) return false;

        return MaxX > 0;
    }

    @TargetApi(11)
    private static final class HoneycombPlus {
        static {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                throw new RuntimeException("Should not get to HoneycombPlus class unless sdk is >= 11!");
            }
        }

        public static void setFriction(Scroller scroller, float friction) {
            if (scroller != null) {
                scroller.setFriction(friction);
            }
        }
    }

    @TargetApi(14)
    private static final class IceCreamSandwichPlus {
        static {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                throw new RuntimeException("Should not get to IceCreamSandwichPlus class unless sdk is >= 14!");
            }
        }

        public static float getCurrVelocity(Scroller scroller) {
            return scroller.getCurrVelocity();
        }
    }
}
