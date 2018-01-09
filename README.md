# FlexItemDecoration
灵活的分割线，可绘制头部、底部、最左边、最右边分割线，还可以定制一行的分割线和批量定制多个分割线(不过网格布局的分割线还没完成)。

#效果图

![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_1.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_2.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_3.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_4.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_5.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_6.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_7.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_8.png)

#功能及使用

	##网格分割线的使用
		DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(context,orientation,SPAN_COUNT);//SPAN_COUNT:列数,orientation:水平或竖直方向
		builder.setDivider(R.drawable.bg_divider_list)//设置分割线的颜色及宽高
		    .setShowOtherStyle(true)//另一种方式显示网格分割线
		    .removeHeaderDivider(false)//是否移除头部分割线
                .removeFooterDivider(false)//是否移除底部分割线
                .removeLeftDivider(false)//是否移除最左边分割线
                .removeRightDivider(false);//是否移除最右边分割线
		recyclerView.addItemDecoration(builder.build());

	##线性分割线的使用
		DividerLinearItemDecoration.Builder builder = new DividerLinearItemDecoration.Builder(context, orientation);//orientation:方向
		builder.setDivider(R.drawable.bg_divider_list)//设置分割线的颜色及宽高
                .removeHeaderDivider(false)//是否移除头部分割线，在竖直方向有效
                .removeFooterDivider(false)//是否移除底部分割线，在竖直方向有效
                .removeLeftDivider(false)//是否移除最左边分割线，在水平方向有效
                .removeRightDivider(false);//是否移除最右边分割线，在水平方向有效
		    .subDivider(1, 4);//分割线截取绘制，1：开始下标，4：结束下标
		    .setSubDividerHeight(24)//设置截取分割线的高度，在竖直方向有效
                .setSubDividerWidth(24)//设置截取分割线的宽度，在水平方向有效
                .setSubDividerDrawable(R.drawable.bg_divider_offset)//设置截取分割线的样式
		    .redrawDivider(2)//分割线定制的下标
                .redrawDividerHeight(30)//定制分割线的高度，在竖直方向有效
		    .redrawDividerWidth(30)//定制分割线的宽度，在水平方向有效
                .redrawDividerDrawable(R.drawable.bg_divider_redraw)//定制分割线的样式
		    .redrawHeaderDivider()//头部分割线的定制
                .redrawHeaderDividerHeight(40)//定制头部分割线的高度，在竖直方向有效
                .redrawHeaderDividerDrawable(R.drawable.bg_divider_offset);//定制头部分割线的样式
		    .redrawFooterDivider()//底部分割线的定制
                .redrawFooterDividerHeight(40)//定制底部分割线的高度，在竖直方向有效
                .redrawFooterDividerDrawable(R.drawable.bg_divider_offset)//定制底部分割线的样式
		    .redrawLeftDivider()//最左边分割线的定制
                .redrawLeftDividerWidth(40)//定制最左边分割线的宽，在水平方向有效
                .redrawLeftDividerDrawable(R.drawable.bg_divider_list)//定制最左边分割线的样式
		    .redrawRightDivider()//最右边分割线的定制
                .redrawRightDividerWidth(40)//定制最右边分割线的宽，在水平方向有效
                .redrawRightDividerDrawable(R.drawable.bg_divider_list);//定制最右边分割线的样式
		recyclerView.addItemDecoration(builder.build());
