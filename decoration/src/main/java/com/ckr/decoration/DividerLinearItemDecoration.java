package com.ckr.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * Created by Administrator on 2017/3/10 0010.
 * <p>
 * 支持recyclerview的下划线的类
 */

public class DividerLinearItemDecoration extends BaseItemDecoration {
    private static final String TAG = "LinearItemDecoration";

    public DividerLinearItemDecoration(Context context) {
        super(context, LINEAR, VERTICAL);
    }

    public DividerLinearItemDecoration(Context context, int orientation) {
        super(context, LINEAR, orientation);
    }

    public DividerLinearItemDecoration(Context context, int orientation, @DrawableRes int drawableId) {
        super(context, LINEAR, orientation, drawableId);
    }

    public DividerLinearItemDecoration redrawHeaderDivider() {
        this.isRedrawHeaderDivider = true;
        return this;
    }

    public DividerLinearItemDecoration redrawHeaderDividerDrawable(@DrawableRes int drawableId) {
        this.mHeaderDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (this.mHeaderDividerHeight == 0) {
            this.mHeaderDividerHeight = mHeaderDividerDrawable.getIntrinsicHeight();
        }
        return this;
    }

    public DividerLinearItemDecoration redrawHeaderDividerHeight(@IntRange(from = 0) int height) {
        this.mHeaderDividerHeight = height;
        return this;
    }

    public DividerLinearItemDecoration redrawFooterDivider() {
        this.isRedrawFooterDivider = true;
        return this;
    }

    public DividerLinearItemDecoration redrawFooterDividerDrawable(@DrawableRes int drawableId) {
        this.mFooterDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (this.mFooterDividerHeight == 0) {
            this.mFooterDividerHeight = this.mFooterDividerDrawable.getIntrinsicHeight();
        }
        return this;
    }

    public DividerLinearItemDecoration redrawFooterDividerHeight(@IntRange(from = 0) int height) {
        this.mFooterDividerHeight = height;
        return this;
    }

    public DividerLinearItemDecoration redrawLeftDivider() {
        this.isRedrawLeftDivider = true;
        return this;
    }

    public DividerLinearItemDecoration redrawLeftDividerDrawable(@DrawableRes int drawableId) {
        this.mLeftDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (this.mLeftDividerWidth == 0) {
            this.mLeftDividerWidth = mLeftDividerDrawable.getIntrinsicWidth();
        }
        return this;
    }

    public DividerLinearItemDecoration redrawLeftDividerWidth(@IntRange(from = 0) int width) {
        this.mLeftDividerWidth = width;
        return this;
    }

    public DividerLinearItemDecoration redrawRightDivider() {
        this.isRedrawRightDivider = true;
        return this;
    }

    public DividerLinearItemDecoration redrawRightDividerDrawable(@DrawableRes int drawableId) {
        this.mRightDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (this.mRightDividerWidth == 0) {
            this.mRightDividerWidth = mRightDividerDrawable.getIntrinsicWidth();
        }
        return this;
    }

    public DividerLinearItemDecoration redrawRightDividerWidth(@IntRange(from = 0) int width) {
        this.mRightDividerWidth = width;
        return this;
    }

    public DividerLinearItemDecoration redrawDivider(@IntRange(from = 0) int dividerLineIndex) {
        this.mDividerIndex = dividerLineIndex;
        this.isRedrawDivider = true;
        return this;
    }

    public DividerLinearItemDecoration redrawDividerHeight(@IntRange(from = 0) int height) {
        this.mRedrawDividerHeight = height;
        return this;
    }

    public DividerLinearItemDecoration redrawDividerWidth(@IntRange(from = 0) int width) {
        this.mRedrawDividerWidth = width;
        return this;
    }

    public DividerLinearItemDecoration redrawDividerDrawable(@DrawableRes int drawableId) {
        this.mDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (mOrientation == VERTICAL) {
            if (this.mRedrawDividerHeight == 0) {
                this.mRedrawDividerHeight = mDividerDrawable.getIntrinsicHeight();
            }
        } else {
            if (mRedrawDividerWidth == 0) {
                this.mRedrawDividerWidth = mDividerDrawable.getIntrinsicWidth();
            }
        }
        return this;
    }

    public DividerLinearItemDecoration subDivider(@IntRange(from = 0) int startIndex, @IntRange(from = 1) int endIndex) {
        int subLen = endIndex - startIndex;
        if (subLen < 0) {
            throw new IndexOutOfBoundsException(startIndex + ">=" + endIndex);
        }
        this.mStartIndex = startIndex;
        this.mEndIndex = endIndex;
        this.isSubDivider = true;
        return this;
    }

    public DividerLinearItemDecoration setSubDividerHeight(@IntRange(from = 0) int height) {
        this.mSubDividerHeight = height;
        return this;
    }

    public DividerLinearItemDecoration setSubDividerWidth(@IntRange(from = 0) int width) {
        this.mSubDividerWidth = width;
        return this;
    }

    public DividerLinearItemDecoration setSubDividerDrawable(@DrawableRes int drawableId) {
        this.mSubDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        if (mOrientation == VERTICAL) {
            if (this.mSubDividerHeight == 0) {
                this.mSubDividerHeight = mSubDrawable.getIntrinsicHeight();
            }
        } else {
            if (this.mSubDividerWidth == 0) {
                this.mSubDividerWidth = mSubDrawable.getIntrinsicWidth();
            }
        }
        return this;
    }

    private DividerLinearItemDecoration(Builder builder) {
        super(builder);
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();//可视item的个数
        int itemCount = parent.getAdapter().getItemCount();//item个数
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        boolean headerPosHandle = true;
        boolean footerPosHandle = true;
        boolean isSubDividerHandle = true;
        boolean isRedrawDividerHandle = true;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            if (!noDrawHeaderDivider) {
                if (headerPosHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (0 == adapterPosition) {
                        headerPosHandle = false;
                        Log.d(TAG, "drawVertical: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
                        bottom = child.getTop() - params.topMargin;
                        if (isRedrawHeaderDivider) {
                            top = bottom - mHeaderDividerHeight;
                            if (mHeaderDividerDrawable != null) {
                                mHeaderDividerDrawable.setBounds(left, top, right, bottom);
                                mHeaderDividerDrawable.draw(c);
                            } else {
                                mDivider.setBounds(left, top, right, bottom);
                                mDivider.draw(c);
                            }
                        } else {
                            top = bottom - mDividerHeight;
                            mDivider.setBounds(left, top, right, bottom);
                            mDivider.draw(c);
                        }
                    } else {
                        headerPosHandle = false;
                    }
                }
            }
            int bottomDividerHeight = mDividerHeight;
            if (noDrawFooterDivider) {
                if (footerPosHandle) {
                    if (childCount - 1 == i) {
                        int adapterPosition = parent.getChildAdapterPosition(child);
                        if (itemCount - 1 == adapterPosition) {
                            bottomDividerHeight = 0;
                            footerPosHandle = false;
                        } else {
                            footerPosHandle = false;
                        }
                    }
                }
            } else {
                if (isRedrawFooterDivider) {
                    if (footerPosHandle) {
                        if (childCount - 1 == i) {
                            int adapterPosition = parent.getChildAdapterPosition(child);
                            if (itemCount - 1 == adapterPosition) {
                                bottomDividerHeight = mFooterDividerHeight;
                                footerPosHandle = false;
                                if (mFooterDividerDrawable != null) {
                                    top = child.getBottom() + params.bottomMargin;
                                    bottom = top + bottomDividerHeight;
                                    mFooterDividerDrawable.setBounds(left, top, right, bottom);
                                    mFooterDividerDrawable.draw(c);
                                    continue;
                                }
                            } else {
                                footerPosHandle = false;
                            }
                        }
                    }
                }
//                Log.d(TAG, "drawVertical: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
//                if (isRedrawFooterDivider) {
//                    Log.e(TAG, "drawVertical: mFooterDividerHeight"+mFooterDividerHeight );
//                    bottomDividerHeight = mFooterDividerHeight;
//                } else {
//                }
            }
            if (isRedrawDivider) {
                if (isRedrawDividerHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (Math.min(mDividerIndex, itemCount - 2) == adapterPosition) {
                        isRedrawDividerHandle = false;
                        top = child.getBottom() + params.bottomMargin;
                        bottom = top + mRedrawDividerHeight;
                        if (mDividerDrawable != null) {
                            mDividerDrawable.setBounds(left, top, right, bottom);
                            mDividerDrawable.draw(c);
                        } else {
                            mDivider.setBounds(left, top, right, bottom);
                            mDivider.draw(c);
                        }
                        continue;
                    }
                }
            }
            if (isSubDivider) {
                if (isSubDividerHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (mStartIndex >= itemCount - 1) {
                        isSubDivider = false;
                    } else {
                        Log.d(TAG, "drawVertical: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",adapterPosition:" + adapterPosition);
                        if (adapterPosition >= mStartIndex) {
                            if (adapterPosition < Math.min(mEndIndex, itemCount - 1)) {
                                bottomDividerHeight = mSubDividerHeight;
                                if (adapterPosition == mEndIndex - 1) {
                                    isSubDividerHandle = false;
                                }
                                if (mSubDrawable != null) {
                                    top = child.getBottom() + params.bottomMargin;
                                    bottom = top + bottomDividerHeight;
                                    mSubDrawable.setBounds(left, top, right, bottom);
                                    mSubDrawable.draw(c);
                                    continue;
                                }
                            } else {
                                isSubDividerHandle = false;
                            }
                        }
                    }
                }
            }

            top = child.getBottom() + params.bottomMargin;
            bottom = top + bottomDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        int itemCount = parent.getAdapter().getItemCount();
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();
        boolean leftPosHandle = true;
        boolean rightPosHandle = true;
        boolean isSubDividerHandle = true;
        boolean isRedrawDividerHandle = true;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            if (!noDrawLeftDivider) {
                if (leftPosHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (0 == adapterPosition) {
                        leftPosHandle = false;
                        Log.d(TAG, "drawHorizontal: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
                        right = child.getLeft() - params.rightMargin;
                        if (isRedrawLeftDivider) {
                            left = right - mLeftDividerWidth;
                            if (mLeftDividerDrawable != null) {
                                mLeftDividerDrawable.setBounds(left, top, right, bottom);
                                mLeftDividerDrawable.draw(c);
                            } else {
                                mDivider.setBounds(left, top, right, bottom);
                                mDivider.draw(c);
                            }
                        } else {
                            left = right - mDividerWidth;
                            mDivider.setBounds(left, top, right, bottom);
                            mDivider.draw(c);
                        }
                    } else {
                        leftPosHandle = false;
                    }
                }
            }
            int rightDividerWidth = mDividerWidth;
            if (noDrawRightDivider) {
                if (rightPosHandle) {
                    if (childCount - 1 == i) {
                        int adapterPosition = parent.getChildAdapterPosition(child);
                        if (itemCount - 1 == adapterPosition) {
                            Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
                            rightDividerWidth = 0;
                            rightPosHandle = false;
                        } else {
                            rightPosHandle = false;
                        }
                    }
                }
            } else {
                if (rightPosHandle) {
                    if (childCount - 1 == i) {
                        int adapterPosition = parent.getChildAdapterPosition(child);
                        if (itemCount - 1 == adapterPosition) {
                            Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
                            rightDividerWidth = mRightDividerWidth;
                            rightPosHandle = false;
                            if (mRightDividerDrawable != null) {
                                left = child.getRight() + params.rightMargin;
                                right = left + rightDividerWidth;
                                mRightDividerDrawable.setBounds(left, top, right, bottom);
                                mRightDividerDrawable.draw(c);
                                continue;
                            }
                        } else {
                            rightPosHandle = false;
                        }
                    }
                }
            }
            if (isRedrawDivider) {
                if (isRedrawDividerHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (Math.min(mDividerIndex, itemCount - 2) == adapterPosition) {
                        isRedrawDividerHandle = false;
                        left = child.getRight() + params.rightMargin;
                        right = left + mRedrawDividerWidth;
                        if (mDividerDrawable != null) {
                            mDividerDrawable.setBounds(left, top, right, bottom);
                            mDividerDrawable.draw(c);
                        } else {
                            mDivider.setBounds(left, top, right, bottom);
                            mDivider.draw(c);
                        }
                        continue;
                    }
                }
            }
            if (isSubDivider) {
                if (isSubDividerHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (mStartIndex >= itemCount - 1) {
                        isSubDivider = false;
                    } else {
                        if (adapterPosition >= mStartIndex) {
                            if (adapterPosition < Math.min(mEndIndex, itemCount - 1)) {
                                rightDividerWidth = mSubDividerWidth;
                                if (mSubDrawable != null) {
                                    left = child.getRight() + params.rightMargin;
                                    right = left + rightDividerWidth;
                                    mSubDrawable.setBounds(left, top, right, bottom);
                                    mSubDrawable.draw(c);
                                    continue;
                                }
                                if (adapterPosition == mEndIndex - 1) {
                                    isSubDividerHandle = false;
                                }
                            } else {
                                isSubDividerHandle = false;
                            }
                        } else {
                            isSubDividerHandle = false;
                        }
                    }
                }
            }

            left = child.getRight() + params.rightMargin;
            right = left + rightDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 要想清楚outRect作用,请看{@link android.support.v7.widget.LinearLayoutManager}源码，如：measureChild().
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        Log.e(TAG, "getItemOffsets: itemPosition:" + itemPosition);
        if (mOrientation == VERTICAL) {
            int top = 0;
            int bottom = mDividerHeight;
            if (!noDrawHeaderDivider) {
                if (itemPosition == 0) {
                    if (isRedrawHeaderDivider) {
                        top = mHeaderDividerHeight;
                    } else {
                        top = mDividerHeight;
                    }
                }
            }
            if (noDrawFooterDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (itemPosition == itemCount - 1) {
                    bottom = 0;
                }
            } else {
                if (isRedrawFooterDivider) {
                    int itemCount = parent.getAdapter().getItemCount();
                    if (itemPosition == itemCount - 1) {
                        Log.e(TAG, "getItemOffsets: mFooterDividerHeight" + mFooterDividerHeight);
                        bottom = mFooterDividerHeight;
                    }
                }
            }
            if (isSubDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (mStartIndex >= itemCount - 1) {
                    isSubDivider = false;
                } else {
                    if (itemPosition >= mStartIndex && itemPosition < Math.min(mEndIndex, itemCount - 1)) {
                        Log.e(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
                        bottom = mSubDividerHeight;
                    }
                }

            }
            if (isRedrawDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (Math.min(mDividerIndex, itemCount - 2) == itemPosition) {
                    bottom = mRedrawDividerHeight;
                }
            }
            outRect.set(0, top, 0, bottom);
        } else {
            int left = 0;
            int right = mDividerWidth;
            if (!noDrawLeftDivider) {
                if (itemPosition == 0) {
                    if (isRedrawLeftDivider) {
                        left = mLeftDividerWidth;
                    } else {
                        left = mDividerWidth;
                    }
                }
            }
            if (noDrawRightDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (itemPosition == itemCount - 1) {
                    right = 0;
                }
            } else {
                if (isRedrawRightDivider) {
                    int itemCount = parent.getAdapter().getItemCount();
                    if (itemPosition == itemCount - 1) {
                        right = mRightDividerWidth;
                    }
                }
            }
            if (isSubDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (mStartIndex >= itemCount - 1) {
                    isSubDivider = false;
                } else {
                    if (itemPosition >= mStartIndex && itemPosition < Math.min(mEndIndex, itemCount - 1)) {
                        Log.e(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
                        right = mSubDividerWidth;
                    }
                }
            }
            if (isRedrawDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (Math.min(mDividerIndex, itemCount - 2) == itemPosition) {
                    right = mRedrawDividerWidth;
                }
            }
            outRect.set(left, 0, right, 0);
        }
    }

    public static class Builder extends BaseBuilder {

        public Builder(Context context) {
            super(context, LINEAR);
        }

        public Builder(Context context, int mOrientation) {
            super(context, LINEAR, mOrientation);
        }

        public DividerLinearItemDecoration build() {
            return new DividerLinearItemDecoration(this);
        }
    }
}
