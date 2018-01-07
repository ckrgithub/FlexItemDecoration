package com.ckr.flexitemdecoration.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
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

    public DividerLinearItemDecoration(Context context, int orientation) {
        super(context, orientation);
    }

    public DividerLinearItemDecoration(Context context, int orientation, int drawableId) {
        super(context, orientation, drawableId);
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
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            if (!noDrawHeaderDivider) {
                if (headerPosHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (0 == adapterPosition) {
                        headerPosHandle = false;
                        Log.d(TAG, "drawHorizontal: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
                        bottom = child.getTop() - params.topMargin;
                        top = bottom - mDividerHeight;
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
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
                            Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
                            bottomDividerHeight = 0;
                            footerPosHandle = false;
                        } else {
                            footerPosHandle = false;
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
                        left = right - mDividerWidth;
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
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
        Log.d(TAG, "getItemOffsets: itemPosition:" + itemPosition);
        if (mOrientation == VERTICAL) {
            int top = 0;
            int bottom = mDividerHeight;
            if (!noDrawHeaderDivider) {
                if (itemPosition == 0) {
                    top = mDividerHeight;
                }
            }
            if (noDrawFooterDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (itemPosition == itemCount - 1) {
                    bottom = 0;
                }
            }
            outRect.set(0, top, 0, bottom);
        } else {
            int left = 0;
            int right = mDividerWidth;
            if (!noDrawLeftDivider) {
                if (itemPosition == 0) {
                    left = mDividerHeight;
                }
            }
            if (noDrawRightDivider) {
                int itemCount = parent.getAdapter().getItemCount();
                if (itemPosition == itemCount - 1) {
                    right = 0;
                }
            }
            outRect.set(left, 0, right, 0);
        }
    }

    public static class Builder extends BaseBuilder {

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int mOrientation) {
            super(context, mOrientation);
        }

        @Override
        public Builder setOrientation(int mOrientation) {
            if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
                throw new IllegalArgumentException("invalid orientation");
            }
            this.mOrientation = mOrientation;
            return this;
        }

        public BaseItemDecoration build() {
            return new DividerLinearItemDecoration(this);

        }
    }
}
