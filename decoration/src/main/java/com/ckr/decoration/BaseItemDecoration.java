package com.ckr.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by PC大佬 on 2018/1/6.
 */

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "BaseItemDecoration";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    protected static final int LINEAR = 1;
    protected static final int GRID = 2;
    protected int mFlag = 0;
    protected int mOrientation = VERTICAL;
    protected Drawable mDivider;
    protected boolean noDrawHeaderDivider;
    protected boolean noDrawFooterDivider;
    protected boolean noDrawLeftDivider;
    protected boolean noDrawRightDivider;
    protected int mDividerHeight;
    protected int mDividerWidth;
    protected Context mContext;

    protected boolean isSubDivider = false;//分割线截取绘制
    protected int mStartIndex;//分割线开始绘制的下标
    protected int mEndIndex;//分割线停止绘制的下标
    protected int mSubDividerHeight;
    protected int mSubDividerWidth;
    protected Drawable mSubDrawable;
    protected boolean isRedrawDivider = false;
    protected int mDividerIndex = -1;//分割线定制的下标，优先级高于分割线截取绘制
    protected Drawable mDividerDrawable;
    protected int mRedrawDividerHeight;
    protected int mRedrawDividerWidth;
    protected boolean isRedrawHeaderDivider = false;//头部分割线是否定制
    protected Drawable mHeaderDividerDrawable;
    protected int mHeaderDividerHeight;
    protected boolean isRedrawFooterDivider = false;//底部分割线是否定制
    protected Drawable mFooterDividerDrawable;
    protected int mFooterDividerHeight;
    protected boolean isRedrawLeftDivider = false;//最左边分割线是否定制
    protected Drawable mLeftDividerDrawable;
    protected int mLeftDividerWidth;
    protected boolean isRedrawRightDivider = false;//最右边分割线是否定制
    protected Drawable mRightDividerDrawable;
    protected int mRightDividerWidth;
    protected boolean isShowOtherStyle;


    protected BaseItemDecoration(Context context, int mFlag, int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != GRID) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mContext = context;
        this.mFlag = mFlag;
        this.mOrientation = orientation;
        initDefaultDivider(context);
    }

    protected BaseItemDecoration(Context context, int mFlag, int orientation, int drawableId) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != GRID) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mContext = context;
        this.mFlag = mFlag;
        this.mOrientation = orientation;
        mDivider = ContextCompat.getDrawable(context.getApplicationContext(), drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
    }

    protected BaseItemDecoration(BaseBuilder baseBuilder) {
        this.mDivider = baseBuilder.mDivider;
        if (this.mDivider == null) {
            initDefaultDivider(baseBuilder.mContext);
        } else {
            mDividerHeight = mDivider.getIntrinsicHeight();
            mDividerWidth = mDivider.getIntrinsicWidth();
        }
        this.mContext = baseBuilder.mContext;
        this.mFlag = baseBuilder.mFlag;
        this.mOrientation = baseBuilder.mOrientation;
        this.noDrawHeaderDivider = baseBuilder.noDrawHeaderDivider;
        this.noDrawFooterDivider = baseBuilder.noDrawFooterDivider;
        this.noDrawLeftDivider = baseBuilder.noDrawLeftDivider;
        this.noDrawRightDivider = baseBuilder.noDrawRightDivider;
        this.isShowOtherStyle = baseBuilder.isShowOtherStyle;

        this.isSubDivider = baseBuilder.isSubDivider;
        this.mStartIndex = baseBuilder.mStartIndex;
        this.mEndIndex = baseBuilder.mEndIndex;
        this.mSubDividerHeight = baseBuilder.mSubDividerHeight;
        this.mSubDividerWidth = baseBuilder.mSubDividerWidth;
        this.mSubDrawable = baseBuilder.mSubDrawable;
        this.isRedrawDivider = baseBuilder.isRedrawDivider;
        this.mDividerIndex = baseBuilder.mDividerIndex;
        this.mDividerDrawable = baseBuilder.mDividerDrawable;
        this.mRedrawDividerHeight = baseBuilder.mRedrawDividerHeight;
        this.mRedrawDividerWidth = baseBuilder.mRedrawDividerWidth;
        this.isRedrawHeaderDivider = baseBuilder.isRedrawHeaderDivider;
        this.mHeaderDividerDrawable = baseBuilder.mHeaderDividerDrawable;
        this.mHeaderDividerHeight = baseBuilder.mHeaderDividerHeight;
        this.isRedrawFooterDivider = baseBuilder.isRedrawFooterDivider;
        this.mFooterDividerDrawable = baseBuilder.mFooterDividerDrawable;
        this.mFooterDividerHeight = baseBuilder.mFooterDividerHeight;
        this.isRedrawLeftDivider = baseBuilder.isRedrawLeftDivider;
        this.mLeftDividerDrawable = baseBuilder.mLeftDividerDrawable;
        this.mLeftDividerWidth = baseBuilder.mLeftDividerWidth;
        this.isRedrawRightDivider = baseBuilder.isRedrawRightDivider;
        this.mRightDividerDrawable = baseBuilder.mRightDividerDrawable;
        this.mRightDividerWidth = baseBuilder.mRightDividerWidth;
    }

    private void initDefaultDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mDividerHeight = mDivider.getIntrinsicHeight() * 5;
        mDividerWidth = mDivider.getIntrinsicWidth() * 5;
    }

    public BaseItemDecoration setDivider(@DrawableRes int drawableId) {
        this.mDivider = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
        return this;
    }

    public BaseItemDecoration removeHeaderDivider(boolean noDrawHeaderDivider) {
        this.noDrawHeaderDivider = noDrawHeaderDivider;
        return this;
    }

    public BaseItemDecoration removeFooterDivider(boolean noDrawFooterDivider) {
        this.noDrawFooterDivider = noDrawFooterDivider;
        return this;
    }

    public BaseItemDecoration removeLeftDivider(boolean noDrawLeftDivider) {
        this.noDrawLeftDivider = noDrawLeftDivider;
        return this;
    }

    public BaseItemDecoration removeRightDivider(boolean noDrawRightDivider) {
        this.noDrawRightDivider = noDrawRightDivider;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mFlag == LINEAR) {
            if (mOrientation == VERTICAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        } else if (mFlag == GRID) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    protected abstract void drawVertical(Canvas c, RecyclerView parent);

    protected abstract void drawHorizontal(Canvas c, RecyclerView parent);

    public static abstract class BaseBuilder {
        protected Context mContext;
        protected Drawable mDivider;
        protected int mFlag;
        protected int mOrientation = VERTICAL;
        protected boolean noDrawHeaderDivider;
        protected boolean noDrawFooterDivider;
        protected boolean noDrawLeftDivider;
        protected boolean noDrawRightDivider;
        protected boolean isSubDivider = false;//分割线截取绘制
        protected int mStartIndex;//分割线开始绘制的下标
        protected int mEndIndex;//分割线停止绘制的下标
        private int mSubDividerHeight;
        private int mSubDividerWidth;
        private Drawable mSubDrawable;
        private boolean isRedrawDivider = false;
        private int mDividerIndex = -1;//分割线定制的下标，优先级高于分割线截取绘制
        private Drawable mDividerDrawable;
        private int mRedrawDividerHeight;
        private int mRedrawDividerWidth;
        private boolean isRedrawHeaderDivider = false;//头部分割线是否定制
        private Drawable mHeaderDividerDrawable;
        private int mHeaderDividerHeight;
        private boolean isRedrawFooterDivider = false;//底部分割线是否定制
        private Drawable mFooterDividerDrawable;
        private int mFooterDividerHeight;
        private boolean isRedrawLeftDivider = false;//最左边分割线是否定制
        private Drawable mLeftDividerDrawable;
        private int mLeftDividerWidth;
        private boolean isRedrawRightDivider = false;//最右边分割线是否定制
        private Drawable mRightDividerDrawable;
        private int mRightDividerWidth;
        private boolean isShowOtherStyle;


        protected BaseBuilder(Context context, int flag) {
            this.mContext = context;
            this.mFlag = flag;
        }

        protected BaseBuilder(Context context, int flag, int mOrientation) {
            if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
                throw new IllegalArgumentException("invalid orientation");
            }
            this.mContext = context;
            this.mFlag = flag;
            this.mOrientation = mOrientation;
        }

        public BaseBuilder setDivider(@DrawableRes int drawableId) {
            this.mDivider = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
            return this;
        }

        public BaseBuilder removeHeaderDivider(boolean noDrawHeaderDivider) {
            this.noDrawHeaderDivider = noDrawHeaderDivider;
            return this;
        }

        public BaseBuilder removeFooterDivider(boolean noDrawFooterDivider) {
            this.noDrawFooterDivider = noDrawFooterDivider;
            return this;
        }

        public BaseBuilder removeLeftDivider(boolean noDrawLeftDivider) {
            this.noDrawLeftDivider = noDrawLeftDivider;
            return this;
        }

        public BaseBuilder removeRightDivider(boolean noDrawRightDivider) {
            this.noDrawRightDivider = noDrawRightDivider;
            return this;
        }

        public BaseBuilder setShowOtherStyle(boolean showOtherStyle) {
            isShowOtherStyle = showOtherStyle;
            return this;
        }

       /* public BaseBuilder setOrientation(int mOrientation) {
            if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
                throw new IllegalArgumentException("invalid orientation");
            }
            this.mOrientation = mOrientation;
            return this;
        }*/


        public BaseBuilder redrawHeaderDivider() {
            this.isRedrawHeaderDivider = true;
            return this;
        }

        public BaseBuilder redrawHeaderDividerDrawable(@DrawableRes int drawableId) {
            this.mHeaderDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
            if (this.mHeaderDividerHeight == 0) {
                this.mHeaderDividerHeight = mHeaderDividerDrawable.getIntrinsicHeight();
            }
            return this;
        }

        public BaseBuilder redrawHeaderDividerHeight(@IntRange(from = 0) int height) {
            this.mHeaderDividerHeight = height;
            return this;
        }

        public BaseBuilder redrawFooterDivider() {
            this.isRedrawFooterDivider = true;
            return this;
        }

        public BaseBuilder redrawFooterDividerDrawable(@DrawableRes int drawableId) {
            this.mFooterDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
            if (this.mFooterDividerHeight == 0) {
                this.mFooterDividerHeight = this.mFooterDividerDrawable.getIntrinsicHeight();
            }
            return this;
        }

        public BaseBuilder redrawFooterDividerHeight(@IntRange(from = 0) int height) {
            this.mFooterDividerHeight = height;
            return this;
        }

        public BaseBuilder redrawLeftDivider() {
            this.isRedrawLeftDivider = true;
            return this;
        }

        public BaseBuilder redrawLeftDividerDrawable(@DrawableRes int drawableId) {
            this.mLeftDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
            if (this.mLeftDividerWidth == 0) {
                this.mLeftDividerWidth = mLeftDividerDrawable.getIntrinsicWidth();
            }
            return this;
        }

        public BaseBuilder redrawLeftDividerWidth(@IntRange(from = 0) int width) {
            this.mLeftDividerWidth = width;
            return this;
        }

        public BaseBuilder redrawRightDivider() {
            this.isRedrawRightDivider = true;
            return this;
        }

        public BaseBuilder redrawRightDividerDrawable(@DrawableRes int drawableId) {
            this.mRightDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
            if (this.mRightDividerWidth == 0) {
                this.mRightDividerWidth = mRightDividerDrawable.getIntrinsicWidth();
            }
            return this;
        }

        public BaseBuilder redrawRightDividerWidth(@IntRange(from = 0) int width) {
            this.mRightDividerWidth = width;
            return this;
        }

        public BaseBuilder redrawDivider(@IntRange(from = 0) int dividerLineIndex) {
            this.mDividerIndex = dividerLineIndex;
            this.isRedrawDivider = true;
            return this;
        }

        public BaseBuilder redrawDividerHeight(@IntRange(from = 0) int height) {
            this.mRedrawDividerHeight = height;
            return this;
        }

        public BaseBuilder redrawDividerWidth(@IntRange(from = 0) int width) {
            this.mRedrawDividerWidth = width;
            return this;
        }

        public BaseBuilder redrawDividerDrawable(@DrawableRes int drawableId) {
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

        public BaseBuilder subDivider(@IntRange(from = 0) int startIndex, @IntRange(from = 1) int endIndex) {
            int subLen = endIndex - startIndex;
            if (subLen <= 0) {
                throw new IndexOutOfBoundsException(startIndex + ">=" + endIndex);
            }
            this.mStartIndex = startIndex;
            this.mEndIndex = endIndex;
            this.isSubDivider = true;
            return this;
        }

        public BaseBuilder setSubDividerHeight(@IntRange(from = 0) int height) {
            this.mSubDividerHeight = height;
            return this;
        }

        public BaseBuilder setSubDividerWidth(@IntRange(from = 0) int width) {
            this.mSubDividerWidth = width;
            return this;
        }

        public BaseBuilder setSubDividerDrawable(@DrawableRes int drawableId) {
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
    }

}
