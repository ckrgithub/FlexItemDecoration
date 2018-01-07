package com.ckr.flexitemdecoration.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;

/**
 * @author zhy
 */
public class DividerGridItemDecoration2 extends RecyclerView.ItemDecoration {
    private static final String TAG = "GridItemDecoration";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int mSpanCount = 1;
    private boolean noDrawHeaderDivider;
    private boolean noDrawFooterDivider;
    private boolean noDrawLeftDivider;
    private boolean noDrawRightDivider;
    private int mDividerHeight;
    private int mDividerWidth;

    public DividerGridItemDecoration2(Context context) {
        initDefaultDivider(context);
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
    }

    public DividerGridItemDecoration2(Context context, int drawableId) {
        mDivider = ContextCompat.getDrawable(context.getApplicationContext(), drawableId);
    }

    private void initDefaultDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    private DividerGridItemDecoration2(Builder builder) {
        this.mDivider = builder.mDivider;
        if (this.mDivider == null) {
            initDefaultDivider(builder.context);
        }
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
        this.mSpanCount = builder.mSpanCount;
        this.noDrawHeaderDivider = builder.noDrawHeaderDivider;
        this.noDrawFooterDivider = builder.noDrawFooterDivider;
        this.noDrawLeftDivider = builder.noDrawLeftDivider;
        this.noDrawRightDivider = builder.noDrawRightDivider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    //绘制水平分割线
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();//可视item的个数
        int itemCount = parent.getAdapter().getItemCount();//item个数
        Log.d(TAG, "drawHorizontal: childCount:" + childCount + ",itemCount:" + itemCount);
        int width = mDivider.getIntrinsicWidth();//分割线的宽度
        int height = mDivider.getIntrinsicHeight();//分割线的高度
        Log.d(TAG, "drawHorizontal: width:" + width + ",height:" + height);
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        boolean headerPosHandle = true;
        boolean bottomPosHandle = true;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int leftDividerWidth = width;
            int rightDividerWidth = width;
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

            int topDividerHeight = height;
            int bottomDividerHeight = height;
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
                if (bottomPosHandle) {
                    int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
                    int startNum = childCount - (itemCount - rowNum * mSpanCount);
                    if (startNum <= i) {
                        int adapterPosition = parent.getChildAdapterPosition(child);
                        if (rowNum * mSpanCount <= adapterPosition) {
                            Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
                            bottomDividerHeight = 0;
                        } else {
                            bottomPosHandle = false;
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

    //// TODO: 2017/10/25 修改绘制方式
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        int width = mDivider.getIntrinsicWidth();
        int height = mDivider.getIntrinsicHeight();//分割线的高度
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
            int leftDividerWidth = width;
            int rightDividerWidth = width;
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

    //请看layoutManager的子类中的measureChild()
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

    public static class Builder {
        private Context context;
        private Drawable mDivider;
        private int mSpanCount = 1;
        private boolean noDrawHeaderDivider;
        private boolean noDrawFooterDivider;
        private boolean noDrawLeftDivider;
        private boolean noDrawRightDivider;

        public Builder(Context context, int mSpanCount) {
            this.context = context;
            this.mSpanCount = mSpanCount;
            if (this.mSpanCount==1) {
                noDrawLeftDivider=true;
                noDrawRightDivider=true;
            }
        }

        public Builder setDivider(@DrawableRes int drawableId) {
            this.mDivider = ContextCompat.getDrawable(context.getApplicationContext(), drawableId);
            return this;
        }

        public Builder removeHeaderDivider(boolean noDrawHeaderDivider) {
            this.noDrawHeaderDivider = noDrawHeaderDivider;
            return this;
        }

        public Builder removeFooterDivider(boolean noDrawFooterDivider) {
            this.noDrawFooterDivider = noDrawFooterDivider;
            return this;
        }

        public Builder removeLeftDivider(boolean noDrawLeftDivider) {
            this.noDrawLeftDivider = noDrawLeftDivider;
            return this;
        }

        public Builder removeRightDivider(boolean noDrawRightDivider) {
            this.noDrawRightDivider = noDrawRightDivider;
            return this;
        }

        public DividerGridItemDecoration2 build() {
            return new DividerGridItemDecoration2(this);
        }
    }
}
