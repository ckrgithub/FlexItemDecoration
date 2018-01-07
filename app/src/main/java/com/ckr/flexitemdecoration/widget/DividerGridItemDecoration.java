package com.ckr.flexitemdecoration.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * @author zhy
 */
public class DividerGridItemDecoration extends BaseItemDecoration {
    private static final String TAG = "GridItemDecoration";
    protected int mSpanCount = 1;

    public DividerGridItemDecoration(Context context) {
        super(context,GRID);
    }

    public DividerGridItemDecoration(Context context, int drawableId) {
        super(context, GRID,drawableId);
    }

    private DividerGridItemDecoration(Builder builder) {
        super(builder);
        this.mSpanCount = builder.mSpanCount;
    }

    //绘制水平分割线
    @Override
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();//可视item的个数
        int itemCount = parent.getAdapter().getItemCount();//item个数
        Log.d(TAG, "drawHorizontal: childCount:" + childCount + ",itemCount:" + itemCount);
        Log.d(TAG, "drawHorizontal: width:" + mDividerWidth + ",height:" + mDividerHeight);
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        boolean headerPosHandle = true;
        boolean footerPosHandle = true;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int leftDividerWidth = mDividerWidth;
            int rightDividerWidth = mDividerWidth;
            if (noDrawLeftDivider) {
                if (i % mSpanCount == 0) {
                    leftDividerWidth = 0;
                }
            }
            if (noDrawRightDivider) {
                if (i % mSpanCount == mSpanCount - 1) {
                    rightDividerWidth = 0;
                }
            }
            left = child.getLeft() - params.leftMargin - leftDividerWidth;//计算分割线的左边
            right = child.getRight() + params.rightMargin + rightDividerWidth;//计算分割线的右边

            int topDividerHeight = mDividerHeight;
            int bottomDividerHeight = mDividerHeight;
            if (noDrawHeaderDivider) {//顶部分割线处理
                if (headerPosHandle) {
                    int adapterPosition = parent.getChildAdapterPosition(child);
                    if (mSpanCount > adapterPosition) {
                        Log.d(TAG, "drawHorizontal: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
                        topDividerHeight = 0;
                        if (adapterPosition == mSpanCount - 1) {
                            headerPosHandle = false;
                        }
                    } else {
                        headerPosHandle = false;
                    }
                }
            }
            if (noDrawFooterDivider) {//底部分割线处理
                if (footerPosHandle) {
                    int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
                    int startNum = childCount - (itemCount - rowNum * mSpanCount);
                    if (startNum <= i) {
                        int adapterPosition = parent.getChildAdapterPosition(child);
                        if (rowNum * mSpanCount <= adapterPosition) {
                            Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
                            bottomDividerHeight = 0;
                            if (adapterPosition ==itemCount - 1) {
                                footerPosHandle = false;
                            }
                        } else {
                            footerPosHandle = false;
                        }
                    }
                }
            }
            //---------item的上方的分割线绘制---------
            bottom = child.getTop() - params.topMargin;//计算分割线的下边
            top = bottom - topDividerHeight;//计算分割线的上边
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            //------------------end-------------------
            //---------item的下方的分割线绘制---------
            top = child.getBottom() + params.bottomMargin;//计算分割线的上边
            bottom = top + bottomDividerHeight;//计算分割线的下边
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            //------------------end-------------------
        }
    }

    @Override
    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            top = child.getTop() - params.topMargin;
            bottom = child.getBottom() + params.bottomMargin;
            int leftDividerWidth = mDividerWidth;
            int rightDividerWidth = mDividerWidth;
            if (noDrawLeftDivider) {
                if (i % mSpanCount == 0) {
                    leftDividerWidth = 0;
                }
            }
            if (noDrawRightDivider) {
                if (i % mSpanCount == mSpanCount - 1) {
                    rightDividerWidth = 0;
                }
            }
            //---------item的左边的分割线绘制---------
            right = child.getLeft() - params.rightMargin;
            left = right - leftDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            //------------------end-------------------
            //---------item的右边的分割线绘制---------
            left = child.getRight() + params.rightMargin;
            right = left + rightDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            //------------------end-------------------
        }
    }

    /**
     * 要想清楚outRect作用，请看{@link android.support.v7.widget.GridLayoutManager}源码，如：measureChild().
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        Log.e(TAG, "getItemOffsets: pos:" + itemPosition);
        int left = mDividerWidth;
        int top = mDividerHeight;
        int right = mDividerWidth;
        int bottom = mDividerHeight;
        if (noDrawHeaderDivider) {
            if (mSpanCount > itemPosition) {
                Log.d(TAG, "getItemOffsets: noDrawHeaderDivider:" + itemPosition);
                top = 0;
            }
        }
        if (noDrawFooterDivider) {
            int itemCount = parent.getAdapter().getItemCount();
            int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
            if (rowNum * mSpanCount <= itemPosition) {
                Log.d(TAG, "getItemOffsets: noDrawFooterDivider:" + itemPosition);
                bottom = 0;
            }
        }
        if (noDrawLeftDivider) {
            if (itemPosition % mSpanCount == 0) {
                left = 0;
            }
        }
        if (noDrawRightDivider) {
            if (itemPosition % mSpanCount == mSpanCount - 1) {
                right = 0;
            }
        }
        /*
        * left：代表item的左边分割线占有的x轴长度
        * top：代表item的顶部分割线占有的y轴长度
        * right：代表item的右边分割线占有的x轴长度
        * bottom：代表item的底部分割线占有的y轴长度
        * */
        outRect.set(left, top, right, bottom);
    }

    public static class Builder extends BaseBuilder {
        private int mSpanCount = 1;

        public Builder(Context context, int mSpanCount) {
            super(context);
            this.mOrientation = GRID;
            this.mSpanCount = mSpanCount;
            if (this.mSpanCount == 1) {
                noDrawLeftDivider = true;
                noDrawRightDivider = true;
            }
        }

        @Override
        public Builder setOrientation(int mOrientation) {
            return this;
        }


        public BaseItemDecoration build() {
            return new DividerGridItemDecoration(this);

        }
    }

}
