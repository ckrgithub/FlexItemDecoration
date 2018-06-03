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

	public BaseItemDecoration redrawHeaderDivider() {
		this.isRedrawHeaderDivider = true;
		return this;
	}

	public BaseItemDecoration redrawHeaderDividerDrawable(@DrawableRes int drawableId) {
		this.mHeaderDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
		if (this.mHeaderDividerHeight == 0) {
			this.mHeaderDividerHeight = mHeaderDividerDrawable.getIntrinsicHeight();
		}
		return this;
	}

	public BaseItemDecoration redrawHeaderDividerHeight(@IntRange(from = 0) int height) {
		this.mHeaderDividerHeight = height;
		return this;
	}

	public BaseItemDecoration redrawFooterDivider() {
		this.isRedrawFooterDivider = true;
		return this;
	}

	public BaseItemDecoration redrawFooterDividerDrawable(@DrawableRes int drawableId) {
		this.mFooterDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
		if (this.mFooterDividerHeight == 0) {
			this.mFooterDividerHeight = this.mFooterDividerDrawable.getIntrinsicHeight();
		}
		return this;
	}

	public BaseItemDecoration redrawFooterDividerHeight(@IntRange(from = 0) int height) {
		this.mFooterDividerHeight = height;
		return this;
	}

	public BaseItemDecoration redrawLeftDivider() {
		this.isRedrawLeftDivider = true;
		return this;
	}

	public BaseItemDecoration redrawLeftDividerDrawable(@DrawableRes int drawableId) {
		this.mLeftDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
		if (this.mLeftDividerWidth == 0) {
			this.mLeftDividerWidth = mLeftDividerDrawable.getIntrinsicWidth();
		}
		return this;
	}

	public BaseItemDecoration redrawLeftDividerWidth(@IntRange(from = 0) int width) {
		this.mLeftDividerWidth = width;
		return this;
	}

	public BaseItemDecoration redrawRightDivider() {
		this.isRedrawRightDivider = true;
		return this;
	}

	public BaseItemDecoration redrawRightDividerDrawable(@DrawableRes int drawableId) {
		this.mRightDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
		if (this.mRightDividerWidth == 0) {
			this.mRightDividerWidth = mRightDividerDrawable.getIntrinsicWidth();
		}
		return this;
	}

	public BaseItemDecoration redrawRightDividerWidth(@IntRange(from = 0) int width) {
		this.mRightDividerWidth = width;
		return this;
	}

	public BaseItemDecoration redrawDivider(@IntRange(from = 0) int dividerLineIndex) {
		this.mDividerIndex = dividerLineIndex;
		this.isRedrawDivider = true;
		return this;
	}

	public BaseItemDecoration redrawDividerHeight(@IntRange(from = 0) int height) {
		this.mRedrawDividerHeight = height;
		return this;
	}

	public BaseItemDecoration redrawDividerWidth(@IntRange(from = 0) int width) {
		this.mRedrawDividerWidth = width;
		return this;
	}

	public BaseItemDecoration redrawDividerDrawable(@DrawableRes int drawableId) {
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

	public BaseItemDecoration subDivider(@IntRange(from = 0) int startIndex, @IntRange(from = 1) int endIndex) {
		int subLen = endIndex - startIndex;
		if (subLen < 0) {
			throw new IndexOutOfBoundsException(startIndex + ">=" + endIndex);
		}
		this.mStartIndex = startIndex;
		this.mEndIndex = endIndex;
		this.isSubDivider = true;
		return this;
	}

	public BaseItemDecoration setSubDividerHeight(@IntRange(from = 0) int height) {
		this.mSubDividerHeight = height;
		return this;
	}

	public BaseItemDecoration setSubDividerWidth(@IntRange(from = 0) int width) {
		this.mSubDividerWidth = width;
		return this;
	}

	public BaseItemDecoration setSubDividerDrawable(@DrawableRes int drawableId) {
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

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
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
		private Context mContext;
		private Drawable mDivider;
		private int mFlag;//标记网格布局还是线性布局
		private int mOrientation = VERTICAL;
		private boolean noDrawHeaderDivider;//头部部分割线是否绘制
		private boolean noDrawFooterDivider;//底部分割线是否绘制
		protected boolean noDrawLeftDivider;//最左边分割线是否绘制
		protected boolean noDrawRightDivider;//最右边分割线是否绘制

		private boolean isSubDivider = false;//分割线截取绘制
		private int mStartIndex;//分割线开始绘制的下标
		private int mEndIndex;//分割线停止绘制的下标
		private int mSubDividerHeight;//分割线的高度，仅适用于竖直方向
		private int mSubDividerWidth;//分割线的宽带，仅适用于水平方向
		private Drawable mSubDrawable;

		private boolean isRedrawDivider = false;//分割线的定制(注：不包括头部、底部、最左边、最右边分割线定制)
		private int mDividerIndex = -1;//分割线定制的下标，优先级高于分割线截取绘制
		private Drawable mDividerDrawable;
		private int mRedrawDividerHeight;
		private int mRedrawDividerWidth;

		private boolean isRedrawHeaderDivider = false;//头部分割线是否定制，仅适用于竖直方向
		private Drawable mHeaderDividerDrawable;
		private int mHeaderDividerHeight;
		private boolean isRedrawFooterDivider = false;//底部分割线是否定制，仅适用于竖直方向
		private Drawable mFooterDividerDrawable;
		private int mFooterDividerHeight;
		private boolean isRedrawLeftDivider = false;//最左边分割线是否定制，仅适用于水平方向
		private Drawable mLeftDividerDrawable;
		private int mLeftDividerWidth;
		private boolean isRedrawRightDivider = false;//最右边分割线是否定制，仅适用于水平方向
		private Drawable mRightDividerDrawable;
		private int mRightDividerWidth;

		protected boolean isShowOtherStyle;//仅适用于网格分割线

		/**
		 * @param context 用于资源文件访问
		 * @param flag    布局方式，如：{@link #LINEAR} or {@link #GRID}
		 */
		protected BaseBuilder(Context context, int flag) {
			this.mContext = context;
			this.mFlag = flag;
		}

		/**
		 * @param context      用于资源文件访问
		 * @param flag         布局方式，如：{@link #LINEAR} or {@link #GRID}
		 * @param mOrientation 布局方向，如：{@link #HORIZONTAL} or {@link #VERTICAL}
		 */
		protected BaseBuilder(Context context, int flag, int mOrientation) {
			if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
				throw new IllegalArgumentException("invalid orientation");
			}
			this.mContext = context;
			this.mFlag = flag;
			this.mOrientation = mOrientation;
		}

		/**
		 * 设置分割线的样式
		 *
		 * @param drawableId drawable资源id
		 * @return
		 */
		public BaseBuilder setDivider(@DrawableRes int drawableId) {
			this.mDivider = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			return this;
		}

		/**
		 * @param noDrawHeaderDivider 是否绘制头部分割线
		 * @return
		 */
		public BaseBuilder removeHeaderDivider(boolean noDrawHeaderDivider) {
			this.noDrawHeaderDivider = noDrawHeaderDivider;
			return this;
		}

		/**
		 * @param noDrawFooterDivider 是否绘制底部分割线
		 * @return
		 */
		public BaseBuilder removeFooterDivider(boolean noDrawFooterDivider) {
			this.noDrawFooterDivider = noDrawFooterDivider;
			return this;
		}

		/**
		 * @param noDrawLeftDivider 是否绘制最左边分割线
		 * @return
		 */
		public BaseBuilder removeLeftDivider(boolean noDrawLeftDivider) {
			this.noDrawLeftDivider = noDrawLeftDivider;
			return this;
		}

		/**
		 * @param noDrawRightDivider 是否绘制最右边分割线
		 * @return
		 */
		public BaseBuilder removeRightDivider(boolean noDrawRightDivider) {
			this.noDrawRightDivider = noDrawRightDivider;
			return this;
		}

		/**
		 * 头部分割线的定制
		 *
		 * @return
		 */
		public BaseBuilder redrawHeaderDivider() {
			this.isRedrawHeaderDivider = true;
			return this;
		}

		/**
		 * @param drawableId 头部分割线drawable资源id
		 * @return
		 */
		public BaseBuilder redrawHeaderDividerDrawable(@DrawableRes int drawableId) {
			this.mHeaderDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			if (this.mHeaderDividerHeight == 0) {
				this.mHeaderDividerHeight = mHeaderDividerDrawable.getIntrinsicHeight();
			}
			return this;
		}

		/**
		 * @param height 头部分割线高度
		 * @return
		 */
		public BaseBuilder redrawHeaderDividerHeight(@IntRange(from = 0) int height) {
			this.mHeaderDividerHeight = height;
			return this;
		}

		/**
		 * 底部分割线的定制
		 *
		 * @return
		 */
		public BaseBuilder redrawFooterDivider() {
			this.isRedrawFooterDivider = true;
			return this;
		}

		/**
		 * @param drawableId 底部分割线的drawable资源id
		 * @return
		 */
		public BaseBuilder redrawFooterDividerDrawable(@DrawableRes int drawableId) {
			this.mFooterDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			if (this.mFooterDividerHeight == 0) {
				this.mFooterDividerHeight = this.mFooterDividerDrawable.getIntrinsicHeight();
			}
			return this;
		}

		/**
		 * @param height 底部分割线的高度
		 * @return
		 */
		public BaseBuilder redrawFooterDividerHeight(@IntRange(from = 0) int height) {
			this.mFooterDividerHeight = height;
			return this;
		}

		/**
		 * 最左边分割线的定制
		 *
		 * @return
		 */
		public BaseBuilder redrawLeftDivider() {
			this.isRedrawLeftDivider = true;
			return this;
		}

		/**
		 * @param drawableId 最左边分割线的drawable资源id
		 * @return
		 */
		public BaseBuilder redrawLeftDividerDrawable(@DrawableRes int drawableId) {
			this.mLeftDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			if (this.mLeftDividerWidth == 0) {
				this.mLeftDividerWidth = mLeftDividerDrawable.getIntrinsicWidth();
			}
			return this;
		}

		/**
		 * @param width 最左边分割线的宽度
		 * @return
		 */
		public BaseBuilder redrawLeftDividerWidth(@IntRange(from = 0) int width) {
			this.mLeftDividerWidth = width;
			return this;
		}

		/**
		 * 最右边分割线的定制
		 *
		 * @return
		 */
		public BaseBuilder redrawRightDivider() {
			this.isRedrawRightDivider = true;
			return this;
		}

		/**
		 * @param drawableId 最右边分割线的drawable资源id
		 * @return
		 */
		public BaseBuilder redrawRightDividerDrawable(@DrawableRes int drawableId) {
			this.mRightDividerDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			if (this.mRightDividerWidth == 0) {
				this.mRightDividerWidth = mRightDividerDrawable.getIntrinsicWidth();
			}
			return this;
		}

		/**
		 * @param width 最右边分割线的宽度
		 * @return
		 */
		public BaseBuilder redrawRightDividerWidth(@IntRange(from = 0) int width) {
			this.mRightDividerWidth = width;
			return this;
		}

		/**
		 * 分割线的定制
		 *
		 * @param dividerLineIndex 分割线的下标
		 * @return
		 */
		public BaseBuilder redrawDivider(@IntRange(from = 0) int dividerLineIndex) {
			this.mDividerIndex = dividerLineIndex;
			this.isRedrawDivider = true;
			return this;
		}

		/**
		 * @param height 定制分割线的高度
		 * @return
		 */
		public BaseBuilder redrawDividerHeight(@IntRange(from = 0) int height) {
			this.mRedrawDividerHeight = height;
			return this;
		}

		/**
		 * @param width 定制分割线的宽度
		 * @return
		 */
		public BaseBuilder redrawDividerWidth(@IntRange(from = 0) int width) {
			this.mRedrawDividerWidth = width;
			return this;
		}

		/**
		 * @param drawableId 定制分割线的drawable资源id
		 * @return
		 */
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

		/**
		 * 分割线的批量定制
		 *
		 * @param startIndex 当mOrientation==Vertical时，startIndex代表起始行的下标；否则，startIndex代表起始列的下标
		 * @param endIndex   当mOrientation==Vertical时，endIndex代表末尾行的下标；否则，endIndex代表末尾列的下标
		 * @return
		 */
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

		/**
		 * @param height 分割线的高度
		 * @return
		 */
		public BaseBuilder setSubDividerHeight(@IntRange(from = 0) int height) {
			this.mSubDividerHeight = height;
			return this;
		}

		/**
		 * @param width 分割线的宽度
		 * @return
		 */
		public BaseBuilder setSubDividerWidth(@IntRange(from = 0) int width) {
			this.mSubDividerWidth = width;
			return this;
		}

		/**
		 * @param drawableId 分割线的drawable资源id
		 * @return
		 */
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
