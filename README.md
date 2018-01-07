# FlexItemDecoration
灵活的分割线，可绘制头部、底部、最左边、最右边分割线，还可以操作某一行的分割线高度(不过暂时没实现)。
#效果图
![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_1.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_2.png)![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_3.png)	![](https://github.com/ckrgithub/FlexItemDecoration/blob/master/screenshot/Screenshot_4.png)

#功能及使用

	##网格分割线的使用
		网格分割线只实现竖直方向的分割线的头部、底部、最左边、最右边的移除功能
		DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(context, 2);//2:列数
		builder.setDivider(R.drawable.bg_divider_list)//设置分割线的颜色及宽高
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
		recyclerView.addItemDecoration(builder.build());
