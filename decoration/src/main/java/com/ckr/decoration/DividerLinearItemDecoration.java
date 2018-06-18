package com.ckr.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import static com.ckr.decoration.DecorationLog.Logd;
import static com.ckr.decoration.DecorationLog.Loge;


/**
 * Created by PC大佬 on 2018/1/6.
 */

public class DividerLinearItemDecoration extends BaseItemDecoration {
	private static final String TAG = "LinearItemDecoration";
	private int mDividerPaddingLeft;//分割线左边距，仅适用于竖直方向
	private int mDividerPaddingTop;//分割线上边距，仅适用于水平方向
	private int mDividerPaddingRight;//分割线右边距，仅适用于竖直方向
	private int mDividerPaddingBottom;//分割线下边距，仅适用于水平方向
	private boolean isSticky;//固定头部
	private int mStickyHeightOrWidth;//固定头部高度或宽度
	private Drawable mStickyDrawable;//固定头部样式
	private int mStickyTextPaddingLeft = 48;//固定头部的文本左边距
	private int mStickyTextPaddingTop = 60;//固定头部的文本上边距
	private int mStickyTextColor = Color.WHITE;//固定头部的文本的字体颜色
	private int mStickyTextSize = 42;//固定头部的文本的字体大小
	private Paint mStickyTextPaint;
	private float mOffsetY;//字体中间线到基准线baseline的偏移量
	private float mTextHeight;

	public DividerLinearItemDecoration(Context context) {
		super(context, LINEAR, VERTICAL);
	}

	public DividerLinearItemDecoration(Context context, int orientation) {
		super(context, LINEAR, orientation);
	}

	public DividerLinearItemDecoration(Context context, int orientation, @DrawableRes int drawableId) {
		super(context, LINEAR, orientation, drawableId);
	}

	private DividerLinearItemDecoration(Builder builder) {
		super(builder);
		this.mDividerPaddingLeft = builder.mDividerPaddingLeft;
		this.mDividerPaddingTop = builder.mDividerPaddingTop;
		this.mDividerPaddingRight = builder.mDividerPaddingRight;
		this.mDividerPaddingBottom = builder.mDividerPaddingBottom;
		this.isSticky = builder.isSticky;
		this.mStickyHeightOrWidth = builder.mStickyHeightOrWidth;
		this.mStickyDrawable = builder.mStickyDrawable;
		this.mStickyTextPaddingLeft = builder.mStickyTextPaddingLeft;
		this.mStickyTextPaddingTop = builder.mStickyTextPaddingTop;
		this.mStickyTextColor = builder.mStickyTextColor;
		this.mStickyTextSize = builder.mStickyTextSize;
		if (isSticky) {
			mStickyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mStickyTextPaint.setColor(mStickyTextColor);//注意：颜色需为argb，否则，绘制不出
			mStickyTextPaint.setTextSize(mStickyTextSize);
			if (mOrientation == HORIZONTAL) {
				mStickyTextPaint.setTextAlign(Paint.Align.CENTER);
			}
			Paint.FontMetricsInt mFontMetricsInt = mStickyTextPaint.getFontMetricsInt();
			mTextHeight = (mFontMetricsInt.descent - mFontMetricsInt.ascent);
			float textCenter = mTextHeight / 2.0f;
			mOffsetY = -mFontMetricsInt.ascent - textCenter;
		}
	}

	public BaseItemDecoration setDividerPaddingLeft(@IntRange(from = 0) int paddingLeft) {
		this.mDividerPaddingLeft = paddingLeft;
		return this;
	}

	public BaseItemDecoration setDividerPaddingRight(@IntRange(from = 0) int paddingRight) {
		this.mDividerPaddingRight = paddingRight;
		return this;
	}

	public BaseItemDecoration setDividerPaddingTop(@IntRange(from = 0) int paddingTop) {
		this.mDividerPaddingTop = paddingTop;
		return this;
	}

	public BaseItemDecoration setDividerPaddingBottom(@IntRange(from = 0) int paddingBottom) {
		this.mDividerPaddingBottom = paddingBottom;
		return this;
	}

	public BaseItemDecoration setDividerPadding(@IntRange(from = 0) int... padding) {
		for (int i = 0; i < padding.length; i++) {
			if (i == 0) {
				this.mDividerPaddingLeft = padding[i];
			} else if (i == 1) {
				this.mDividerPaddingTop = padding[i];
			} else if (i == 2) {
				this.mDividerPaddingRight = padding[i];
			} else if (i == 3) {
				this.mDividerPaddingBottom = padding[i];
				break;
			}
		}
		return this;
	}

	public BaseItemDecoration setSticky(boolean sticky) {
		isSticky = sticky;
		return this;
	}

	public BaseItemDecoration setStickyHeightOrWidth(@IntRange(from = 0) int stickyHeaderHeight) {
		this.mStickyHeightOrWidth = stickyHeaderHeight;
		return this;
	}

	public BaseItemDecoration setStickyDrawable(@DrawableRes int drawableId) {
		this.mStickyDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
		;
		if (this.mStickyHeightOrWidth == 0) {
			this.mStickyHeightOrWidth = mStickyDrawable.getIntrinsicHeight();
		}
		return this;
	}

	public BaseItemDecoration setStickyTextPaddingLeft(int textPaddingLeft) {
		this.mStickyTextPaddingLeft = textPaddingLeft;
		return this;
	}

	public BaseItemDecoration setStickyTextPaddingTop(int textPaddingTop) {
		this.mStickyTextPaddingTop = textPaddingTop;
		return this;
	}

	/**
	 * @param textColor 颜色需为argb，否则不生效
	 * @return
	 */
	public BaseItemDecoration setStickyTextColor(int textColor) {
		this.mStickyTextColor = textColor;
		return this;
	}

	public BaseItemDecoration setStickyTextSize(int textSize) {
		this.mStickyTextSize = textSize;
		return this;
	}

	//绘制竖直分割线
	@Override
	public void drawVertical(Canvas c, RecyclerView parent) {
		final int childCount = parent.getChildCount();//可视item的个数
		RecyclerView.Adapter adapter = parent.getAdapter();
		int itemCount = adapter.getItemCount();//item个数
		boolean clipToPadding = isClipToPadding(parent);
		int left = 0, top = 0, right = 0, bottom = 0;
		top = parent.getPaddingTop() + mDividerPaddingTop;
		bottom = parent.getHeight() - parent.getPaddingBottom() - mDividerPaddingBottom;
		boolean leftPosHandle = true, rightPosHandle = true, isSubDividerHandle = true, isRedrawDividerHandle = true;
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			//<editor-fold desc="悬浮左部样式的分割线">
			if (isSticky) {
				if (adapter instanceof OnHeaderListener) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					OnHeaderListener listener = ((OnHeaderListener) adapter);
					String headerName = listener.getHeaderName(adapterPosition);
					if (!TextUtils.isEmpty(headerName)) {
						if (adapterPosition != 0 && !headerName.equals(listener.getHeaderName(adapterPosition - 1))) {
							right = child.getLeft() - params.leftMargin;//计算分割线的右边
							left = right - mStickyHeightOrWidth;//计算分割线的左边
							int stickTop = parent.getPaddingTop();
							int stickyBottom = parent.getHeight() - parent.getPaddingBottom();
							if (clipToPadding) {
								int paddingLeft = parent.getPaddingLeft();
								if (paddingLeft > left) {
									left = paddingLeft;
								}
								if (paddingLeft > right) {
									continue;
								}
							}
							if (mStickyDrawable != null) {
								mStickyDrawable.setBounds(left, stickTop, right, stickyBottom);
								mStickyDrawable.draw(c);
							} else {
								mDivider.setBounds(left, stickTop, right, stickyBottom);
								mDivider.draw(c);
							}
							int x = left + mStickyHeightOrWidth / 2;
							float baseline = stickTop + mStickyTextPaddingLeft;
							c.drawText(headerName, x, baseline, mStickyTextPaint);
							continue;
						}
					}
				}
			}
			//<editor-fold desc="最左边分割线绘制与定制">
			if (!noDrawLeftDivider && !isSticky) {//最左边分割线处理
				if (i == 0) {
					if (leftPosHandle) {
						leftPosHandle = false;
						int adapterPosition = parent.getChildAdapterPosition(child);
						if (0 == adapterPosition) {
							Logd(TAG, "drawVertical: adapterPosition:" + adapterPosition);
							right = child.getLeft() - params.leftMargin;
							if (isRedrawLeftDivider) {//最左边分割线的定制
								left = right - mLeftDividerWidth;
								if (clipToPadding) {
									int paddingLeft = parent.getPaddingLeft();
									if (paddingLeft > left) {
										left = paddingLeft;
									}
									if (paddingLeft > right) {
										continue;
									}
								}
								if (mLeftDividerDrawable != null) {
									mLeftDividerDrawable.setBounds(left, top, right, bottom);
									mLeftDividerDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
							} else {
								left = right - mDividerWidth;
								if (clipToPadding) {
									int paddingLeft = parent.getPaddingLeft();
									if (paddingLeft > left) {
										left = paddingLeft;
									}
									if (paddingLeft > right) {
										continue;
									}
								}
								mDivider.setBounds(left, top, right, bottom);
								mDivider.draw(c);
							}
						}
						continue;
					}
				}
			} else {
				if (i == 0) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (0 == adapterPosition) {
						continue;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="最右边分割线绘制与定制">
			if (!noDrawRightDivider) {//最右边分割线处理
				if (childCount - 1 == i) {
					if (rightPosHandle) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						rightPosHandle = false;
						if (itemCount - 1 == adapterPosition) {
							Logd(TAG, "drawVertical: adapterPosition:" + adapterPosition);
							left = child.getRight() + params.rightMargin;
							if (isRedrawRightDivider) {//最右边分割线的定制
								right = left + mRightDividerWidth;
								if (mRightDividerDrawable != null) {
									mRightDividerDrawable.setBounds(left, top, right, bottom);
									mRightDividerDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
							} else {
								right = left + mDividerWidth;
								mDivider.setBounds(left, top, right, bottom);
								mDivider.draw(c);
							}
						}
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线定制">
			if (isRedrawDivider) {//分割线的定制
				if (isRedrawDividerHandle) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (Math.min(mDividerIndex, itemCount - 1) == adapterPosition) {
						isRedrawDividerHandle = false;
						right = child.getLeft() - params.leftMargin;
						left = right - mRedrawDividerWidth;
						if (clipToPadding) {
							int paddingLeft = parent.getPaddingLeft();
							if (paddingLeft > left) {
								left = paddingLeft;
							}
							if (paddingLeft > right) {
								continue;
							}
						}
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
			//</editor-fold>
			//<editor-fold desc="分割线批量定制">
			if (isSubDivider) {//分割线的截取
				if (isSubDividerHandle) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (mStartIndex > itemCount - 1) {
						isSubDivider = false;
					} else {
						if (adapterPosition != 0 && adapterPosition >= mStartIndex) {
							Loge(TAG, "drawVertical: isSubDivider;" + adapterPosition);
							if (adapterPosition <= Math.min(mEndIndex - 1, itemCount - 1)) {
								if (adapterPosition == mEndIndex - 1) {
									isSubDividerHandle = false;
								}
								right = child.getLeft() - params.leftMargin;
								left = right - mSubDividerWidth;
								if (clipToPadding) {
									int paddingLeft = parent.getPaddingLeft();
									if (paddingLeft > left) {
										left = paddingLeft;
									}
									if (paddingLeft > right) {
										continue;
									}
								}
								if (mSubDrawable != null) {
									mSubDrawable.setBounds(left, top, right, bottom);
									mSubDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
								continue;
							} else {
								isSubDividerHandle = false;
							}
						}
					}
				}
			}
			//</editor-fold>
			right = child.getLeft() - params.leftMargin;
			left = right - mDividerWidth;
			if (clipToPadding) {
				int paddingLeft = parent.getPaddingLeft();
				if (paddingLeft > left) {
					left = paddingLeft;
				}
				if (paddingLeft > right) {
					continue;
				}
			}
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	private boolean isClipToPadding(RecyclerView parent) {
		return parent.getClipToPadding();
	}

	//绘制水平分割线
	@Override
	public void drawHorizontal(Canvas c, RecyclerView parent) {
		int childCount = parent.getChildCount();//可视item的个数
		RecyclerView.Adapter adapter = parent.getAdapter();
		int itemCount = adapter.getItemCount();//item个数
		boolean clipToPadding = isClipToPadding(parent);
		int left = 0, top = 0, right = 0, bottom = 0;
		left = parent.getPaddingLeft() + mDividerPaddingLeft;
		right = parent.getWidth() - parent.getPaddingRight() - mDividerPaddingRight;
		boolean headerPosHandle = true, footerPosHandle = true, isSubDividerHandle = true, isRedrawDividerHandle = true;
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			//<editor-fold desc="悬浮头部样式的分割线">
			if (isSticky) {
				if (adapter instanceof OnHeaderListener) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					OnHeaderListener listener = ((OnHeaderListener) adapter);
					String headerName = listener.getHeaderName(adapterPosition);
					if (!TextUtils.isEmpty(headerName)) {
						if (adapterPosition != 0 && !headerName.equals(listener.getHeaderName(adapterPosition - 1))) {
							bottom = child.getTop() - params.topMargin;//计算分割线的下边
							top = bottom - mStickyHeightOrWidth;//计算分割线的上边
							int stickyLeft = parent.getPaddingLeft();
							int stickyRight = parent.getWidth() - parent.getPaddingRight();
							if (clipToPadding) {
								int paddingTop = parent.getPaddingTop();
								if (paddingTop > top) {
									top = paddingTop;
								}
								if (paddingTop > bottom) {
									continue;
								}
							}
							if (mStickyDrawable != null) {
								mStickyDrawable.setBounds(stickyLeft, top, stickyRight, bottom);
								mStickyDrawable.draw(c);
							} else {
								mDivider.setBounds(stickyLeft, top, stickyRight, bottom);
								mDivider.draw(c);
							}
							int x = stickyLeft + mStickyTextPaddingLeft;
							float baseline = top + mStickyHeightOrWidth / 2 + mOffsetY;
							c.drawText(headerName, x, baseline, mStickyTextPaint);
							continue;
						}
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="顶部分割线绘制与定制">
			if (!noDrawHeaderDivider && !isSticky) {//顶部分割线处理
				if (i == 0) {
					if (headerPosHandle) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						headerPosHandle = false;
						if (0 == adapterPosition) {
							Logd(TAG, "drawHorizontal:  adapterPosition:" + adapterPosition);
							bottom = child.getTop() - params.topMargin;
							if (isRedrawHeaderDivider) {//顶部分割线定制
								top = bottom - mHeaderDividerHeight;
								if (clipToPadding) {
									int paddingTop = parent.getPaddingTop();
									if (paddingTop > top) {
										top = paddingTop;
									}
									if (paddingTop > bottom) {
										continue;
									}
								}
								if (mHeaderDividerDrawable != null) {
									mHeaderDividerDrawable.setBounds(left, top, right, bottom);
									mHeaderDividerDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
							} else {
								top = bottom - mDividerHeight;
								if (clipToPadding) {
									int paddingTop = parent.getPaddingTop();
									if (paddingTop > top) {
										top = paddingTop;
									}
									if (paddingTop > bottom) {
										continue;
									}
								}
								mDivider.setBounds(left, top, right, bottom);
								mDivider.draw(c);
							}
						}
						continue;
					}
				}
			} else {
				if (i == 0) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (0 == adapterPosition) {
						continue;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="底部分割线绘制与定制">
			if (!noDrawFooterDivider) {
				if (childCount - 1 == i) {
					if (footerPosHandle) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						footerPosHandle = false;
						if (itemCount - 1 == adapterPosition) {
							top = child.getBottom() + params.bottomMargin;
							if (isRedrawFooterDivider) {//底部分割线定制
								bottom = top + mFooterDividerHeight;
								if (mFooterDividerDrawable != null) {
									mFooterDividerDrawable.setBounds(left, top, right, bottom);
									mFooterDividerDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
							} else {
								bottom = top + mDividerHeight;
								mDivider.setBounds(left, top, right, bottom);
								mDivider.draw(c);
							}
						}
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线定制">
			if (isRedrawDivider) {//分割线的定制
				if (isRedrawDividerHandle) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (Math.min(mDividerIndex, itemCount - 1) == adapterPosition) {
						isRedrawDividerHandle = false;
						bottom = child.getTop() - params.topMargin;
						top = bottom - mRedrawDividerHeight;
						if (clipToPadding) {
							int paddingTop = parent.getPaddingTop();
							if (paddingTop > top) {
								top = paddingTop;
							}
							if (paddingTop > bottom) {
								continue;
							}
						}
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
			//</editor-fold>
			//<editor-fold desc="分割线批量定制">
			if (isSubDivider) {//分割线的截取
				if (isSubDividerHandle) {
					int adapterPosition = parent.getChildAdapterPosition(child);
					if (mStartIndex > itemCount - 1) {
						isSubDivider = false;
					} else {
						if (adapterPosition != 0 && adapterPosition >= mStartIndex) {
							if (adapterPosition <= Math.min(mEndIndex - 1, itemCount - 1)) {
								Loge(TAG, "drawHorizontal: isSubDivider;" + adapterPosition);
								if (adapterPosition == mEndIndex - 1) {
									isSubDividerHandle = false;
								}
								bottom = child.getTop() - params.topMargin;
								top = bottom - mSubDividerHeight;
								if (clipToPadding) {
									int paddingTop = parent.getPaddingTop();
									if (paddingTop > top) {
										top = paddingTop;
									}
									if (paddingTop > bottom) {
										continue;
									}
								}
								if (mSubDrawable != null) {
									mSubDrawable.setBounds(left, top, right, bottom);
									mSubDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
								continue;
							} else {
								isSubDividerHandle = false;
							}
						}
					}
				}
			}
			//</editor-fold>
			bottom = child.getTop() - params.topMargin;
			top = bottom - mDividerHeight;
			if (clipToPadding) {
				int paddingTop = parent.getPaddingTop();
				if (paddingTop > top) {
					top = paddingTop;
				}
				if (paddingTop > bottom) {
					continue;
				}
			}
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);
		if (isSticky) {
			RecyclerView.Adapter adapter = parent.getAdapter();
			if (adapter instanceof OnHeaderListener) {
				OnHeaderListener listener = ((OnHeaderListener) adapter);
				View child = parent.getChildAt(1);//得到第二个可视item
				int adapterPosition = parent.getChildAdapterPosition(child);
				adapterPosition = Math.max(0, adapterPosition);
				String headerName = listener.getHeaderName(adapterPosition);
				Loge(TAG, "onDrawOver: headerName:" + headerName + ",adapterPosition:" + adapterPosition);
				if (!TextUtils.isEmpty(headerName)) {
					boolean clipToPadding = isClipToPadding(parent);
					boolean flag = false;
					if (mOrientation == VERTICAL) {
						int stickyTop = !clipToPadding ? parent.getTop() : parent.getPaddingTop();
						int stickyBottom = stickyTop + mStickyHeightOrWidth;
						int left = parent.getPaddingLeft();
						int right = parent.getWidth() - parent.getPaddingRight();
						if (!headerName.equals(listener.getHeaderName(Math.max(0, adapterPosition - 1)))) {//判断与上一个的头部文本是否不同
							final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
									.getLayoutParams();
							int bottom = child.getTop() - params.topMargin;//分割线的底部
							int top = bottom - mStickyHeightOrWidth;//分割线的顶部
							Logd(TAG, "onDrawOver: stickyBottom:" + stickyBottom + ",stickyTop:" + stickyTop + ",top:" + top + ",bottom:" + bottom);
							if (stickyBottom >= top && top > stickyTop) {//分割线的顶部是否与悬浮的头部重叠
								bottom = top;
								top = top - mStickyHeightOrWidth;
								if (mStickyDrawable != null) {
									mStickyDrawable.setBounds(left, top, right, bottom);
									mStickyDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
								int x = left + mStickyTextPaddingLeft;
								float baseline = top + mStickyHeightOrWidth / 2 + mOffsetY;
								String lastHeaderName = listener.getHeaderName(Math.max(0, adapterPosition - 1));
								if (!TextUtils.isEmpty(lastHeaderName)) {//得到上一个item的头部文本
									c.drawText(lastHeaderName, x, baseline, mStickyTextPaint);
								}
								return;
							} else {
								if (top <= stickyTop) {
									flag = true;
								}
							}
						}
						if (mStickyDrawable != null) {
							mStickyDrawable.setBounds(left, stickyTop, right, stickyBottom);
							mStickyDrawable.draw(c);
						} else {
							mDivider.setBounds(left, stickyTop, right, stickyBottom);
							mDivider.draw(c);
						}
						int x = left + mStickyTextPaddingLeft;
						float baseline = stickyTop + mStickyHeightOrWidth / 2 + mOffsetY;
						String lastHeaderName = listener.getHeaderName(flag ? adapterPosition : Math.max(0, adapterPosition - 1));//得到上一个item的头部文本
						if (!TextUtils.isEmpty(lastHeaderName)) {
							c.drawText(lastHeaderName, x, baseline, mStickyTextPaint);
						}
					} else {
						int top = parent.getPaddingTop();
						int bottom = parent.getHeight() - parent.getPaddingBottom();
						int stickyLeft = !clipToPadding ? parent.getLeft() : parent.getPaddingLeft();
						int stickyRight = stickyLeft + mStickyHeightOrWidth;
						if (adapterPosition != 0 && !headerName.equals(listener.getHeaderName(Math.max(0, adapterPosition - 1)))) {//判断与上一个的头部文本是否不同
							final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
									.getLayoutParams();
							int right = child.getLeft() - params.leftMargin;
							int left = right - mStickyHeightOrWidth;
							Logd(TAG, "onDrawOver: stickyRight:" + stickyRight + ",stickyLeft:" + stickyLeft + ",left:" + left + ",right:" + right);
							if (stickyRight >= left && left > stickyLeft) {//分割线的顶部是否与悬浮的头部重叠
								right = left;
								left = left - mStickyHeightOrWidth;
								if (mStickyDrawable != null) {
									mStickyDrawable.setBounds(left, top, right, bottom);
									mStickyDrawable.draw(c);
								} else {
									mDivider.setBounds(left, top, right, bottom);
									mDivider.draw(c);
								}
								int x = left + mStickyHeightOrWidth / 2;
								float baseline = top + mStickyTextPaddingLeft;
								String lastHeaderName = listener.getHeaderName(Math.max(0, adapterPosition - 1));
								if (!TextUtils.isEmpty(lastHeaderName)) {//得到上一个item的头部文本'
									c.drawText(lastHeaderName, x, baseline, mStickyTextPaint);
								}
								return;
							} else {
								if (left <= stickyLeft) {
									flag = true;
								}
							}
						}
						if (mStickyDrawable != null) {
							mStickyDrawable.setBounds(stickyLeft, top, stickyRight, bottom);
							mStickyDrawable.draw(c);
						} else {
							mDivider.setBounds(stickyLeft, top, stickyRight, bottom);
							mDivider.draw(c);
						}
						int x = stickyLeft + mStickyHeightOrWidth / 2;
						float baseline = top + mStickyTextPaddingTop;
						String lastHeaderName = listener.getHeaderName(flag ? adapterPosition : Math.max(0, adapterPosition - 1));//得到上一个item的头部文本
						if (!TextUtils.isEmpty(lastHeaderName)) {
							int len = lastHeaderName.length();
							if (len > 1) {
								char[] array = lastHeaderName.toCharArray();
								int length = array.length;
								for (int i = 0; i < length; i++) {
									char c1 = array[i];
									c.drawText(String.valueOf(c1), x, baseline, mStickyTextPaint);
									baseline += mTextHeight;
								}
							} else {
								c.drawText(lastHeaderName, x, baseline, mStickyTextPaint);
							}

						}
					}
				}
			}
		}
	}

	/**
	 * 要想清楚outRect作用,请看{@link android.support.v7.widget.LinearLayoutManager}源码，如：measureChild().
	 */
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int itemPosition = parent.getChildAdapterPosition(view);
		Loge(TAG, "getItemOffsets: itemPosition:" + itemPosition);
		if (mOrientation == VERTICAL) {
			int top = mDividerHeight;
			int bottom = 0;
			//<editor-folder desc="悬浮头部">
			if (isSticky) {//悬浮头部
				RecyclerView.Adapter adapter = parent.getAdapter();
				if (adapter instanceof OnHeaderListener) {
					OnHeaderListener listener = ((OnHeaderListener) adapter);
					String headerName = listener.getHeaderName(itemPosition);
					if (!TextUtils.isEmpty(headerName)) {
						if (itemPosition == 0 || !headerName.equals(listener.getHeaderName(itemPosition - 1))) {
							Logd(TAG, "getItemOffsets: headerName:" + headerName + ",itemPosition:" + itemPosition);
							top = mStickyHeightOrWidth;
							outRect.set(0, top, 0, bottom);
							return;
						}
					}
				}
			}
			//</editor-folder>
			//<editor-fold desc="顶部分割线绘制与定制">
			if (noDrawHeaderDivider) {
				if (itemPosition == 0) {
					top = 0;
				}
			} else {
				if (itemPosition == 0) {
					if (isRedrawHeaderDivider) {
						top = mHeaderDividerHeight;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="底部分割线绘制与定制">
			if (!noDrawFooterDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (itemPosition == itemCount - 1) {
					Logd(TAG, "getItemOffsets: mFooterDividerHeight" + mFooterDividerHeight);
					if (isRedrawFooterDivider) {
						bottom = mFooterDividerHeight;
					} else {
						bottom = mDividerHeight;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线批量定制">
			if (isSubDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (mStartIndex > itemCount - 1) {
					isSubDivider = false;
				} else {
					if (itemPosition != 0 && itemPosition >= mStartIndex && itemPosition <= Math.min(mEndIndex - 1, itemCount - 1)) {
						Logd(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
						top = mSubDividerHeight;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线定制">
			if (isRedrawDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (itemPosition != 0 && Math.min(mDividerIndex, itemCount - 1) == itemPosition) {
					top = mRedrawDividerHeight;
				}
			}
			//</editor-fold>
			outRect.set(0, top, 0, bottom);
		} else {
			int left = mDividerWidth;
			int right = 0;
			//<editor-folder desc="悬浮左边">
			if (isSticky) {//悬浮左边
				RecyclerView.Adapter adapter = parent.getAdapter();
				if (adapter instanceof OnHeaderListener) {
					OnHeaderListener listener = ((OnHeaderListener) adapter);
					String headerName = listener.getHeaderName(itemPosition);
					if (!TextUtils.isEmpty(headerName)) {
						if (itemPosition == 0 || !headerName.equals(listener.getHeaderName(itemPosition - 1))) {
							Logd(TAG, "getItemOffsets: headerName:" + headerName + ",itemPosition:" + itemPosition);
							left = mStickyHeightOrWidth;
							outRect.set(left, 0, right, 0);
							return;
						}
					}
				}
			}
			//</editor-folder>
			//<editor-fold desc="最左边分割线绘制与定制">
			if (noDrawLeftDivider) {
				if (itemPosition == 0) {
					left = 0;
				}
			} else {
				if (itemPosition == 0) {
					if (isRedrawLeftDivider) {
						left = mLeftDividerWidth;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="最右边分割线绘制与定制">
			if (!noDrawRightDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (itemPosition == itemCount - 1) {
					if (isRedrawRightDivider) {
						right = mRightDividerWidth;
					} else {
						right = mDividerWidth;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线批量定制">
			if (isSubDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (mStartIndex > itemCount - 1) {
					isSubDivider = false;
				} else {
					if (itemPosition != 0 && itemPosition >= mStartIndex && itemPosition <= Math.min(mEndIndex - 1, itemCount - 1)) {
						Logd(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
						left = mSubDividerWidth;
					}
				}
			}
			//</editor-fold>
			//<editor-fold desc="分割线定制">
			if (isRedrawDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				if (itemPosition != 0 && Math.min(mDividerIndex, itemCount - 1) == itemPosition) {
					left = mRedrawDividerWidth;
				}
			}
			//</editor-fold>
			outRect.set(left, 0, right, 0);
		}
	}

	public static class Builder extends BaseBuilder {
		private int mDividerPaddingLeft;//分割线左边距，仅适用于竖直方向
		private int mDividerPaddingTop;//分割线上边距，仅适用于水平方向
		private int mDividerPaddingRight;//分割线右边距，仅适用于竖直方向
		private int mDividerPaddingBottom;//分割线下边距，仅适用于水平方向
		private boolean isSticky;//固定头部
		private int mStickyHeightOrWidth;//固定头部高度或宽度
		private Drawable mStickyDrawable;//固定头部样式
		private int mStickyTextPaddingLeft = 48;//固定头部的文本左边距
		private int mStickyTextPaddingTop = 60;//固定头部的文本上边距
		private int mStickyTextColor = Color.WHITE;//固定头部的文本的字体颜色
		private int mStickyTextSize = 42;//固定头部的文本的字体大小

		public Builder(Context context) {
			super(context, LINEAR);
		}

		public Builder(Context context, int mOrientation) {
			super(context, LINEAR, mOrientation);
		}

		public Builder setDividerPaddingLeft(@IntRange(from = 0) int paddingLeft) {
			this.mDividerPaddingLeft = paddingLeft;
			return this;
		}

		public Builder setDividerPaddingRight(@IntRange(from = 0) int paddingRight) {
			this.mDividerPaddingRight = paddingRight;
			return this;
		}

		public Builder setDividerPaddingTop(@IntRange(from = 0) int paddingTop) {
			this.mDividerPaddingTop = paddingTop;
			return this;
		}

		public Builder setDividerPaddingBottom(@IntRange(from = 0) int paddingBottom) {
			this.mDividerPaddingBottom = paddingBottom;
			return this;
		}

		public Builder setDividerPadding(@IntRange(from = 0) int... padding) {
			for (int i = 0; i < padding.length; i++) {
				if (i == 0) {
					this.mDividerPaddingLeft = padding[i];
				} else if (i == 1) {
					this.mDividerPaddingTop = padding[i];
				} else if (i == 2) {
					this.mDividerPaddingRight = padding[i];
				} else if (i == 3) {
					this.mDividerPaddingBottom = padding[i];
					break;
				}
			}
			return this;
		}

		public Builder setSticky(boolean sticky) {
			isSticky = sticky;
			return this;
		}

		public Builder setStickyHeightOrWidth(@IntRange(from = 0) int stickyHeaderHeight) {
			this.mStickyHeightOrWidth = stickyHeaderHeight;
			return this;
		}

		public Builder setStickyDrawable(@DrawableRes int drawableId) {
			this.mStickyDrawable = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
			;
			if (this.mStickyHeightOrWidth == 0) {
				this.mStickyHeightOrWidth = mStickyDrawable.getIntrinsicHeight();
			}
			return this;
		}

		public Builder setStickyTextPaddingLeft(int textPaddingLeft) {
			this.mStickyTextPaddingLeft = textPaddingLeft;
			return this;
		}

		public Builder setStickyTextPaddingTop(int textPaddingTop) {
			this.mStickyTextPaddingTop = textPaddingTop;
			return this;
		}

		/**
		 * @param textColor 颜色需为argb，否则不生效
		 * @return
		 */
		public Builder setStickyTextColor(int textColor) {
			this.mStickyTextColor = textColor;
			return this;
		}

		public Builder setStickyTextSize(int textSize) {
			this.mStickyTextSize = textSize;
			return this;
		}

		public DividerLinearItemDecoration build() {
			return new DividerLinearItemDecoration(this);
		}
	}
}
