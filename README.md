# FlexItemDecoration
FlexItemDecoration, can customize the head, bottom, leftmost, rightmost dividing line, but also customize any one of the dividing lines and batch custom multiple dividing lines [中文文档](README-ZH.md).

## Effect
| vertical-grid                    | horizontal-grid                  | vertical-grid-2                  |
| -------------------------------- | -------------------------------- | -------------------------------- |
| ![](screenshot/Screenshot_1.png) | ![](screenshot/Screenshot_2.png) | ![](screenshot/Screenshot_3.png) |

| vertical-linear                  | horizontal-linear                | custom-line-linear               |
| -------------------------------- | -------------------------------- | -------------------------------- |
| ![](screenshot/Screenshot_4.png) | ![](screenshot/Screenshot_5.png) | ![](screenshot/Screenshot_6.png) | 

| custom-line-grid                 | no-draw-linear                   | no-draw-grid                     |
| -------------------------------- | -------------------------------- | -------------------------------- |
| ![](screenshot/Screenshot_9.png) | ![](screenshot/Screenshot_8.png) | ![](screenshot/Screenshot_7.png) |

## Demo
[Download APK](apk/app-debug.apk)

## Dependencies
#### add dependencies：
```
	dependencies {
		implementation 'ckrjfrog.FlexItemDecoration:Decoration:1.1.1'//gradle plugin 3.0(inclusive) above used
		//compile 'ckrjfrog.FlexItemDecoration:Decoration:1.1.1'//gradle plugin 3.0 below used
	}
```

## Function And Use
#### 1.DividerGridItemDecoration
```
		DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(context,orientation,SPAN_COUNT);
		builder.setDivider(R.drawable.bg_divider_list)//set the drawable of the dividing line
		       .setShowOtherStyle(false)
		       .removeHeaderDivider(false)
                       .removeFooterDivider(false)
                       .removeLeftDivider(false)
                       .removeRightDivider(false)
		       .subDivider(1, 4)//custom multiple lines
                       .setSubDividerHeight(24)//valid in vertical direction
                       .setSubDividerWidth(24)//valid in horizontal direction
                       .setSubDividerDrawable(R.drawable.bg_divider_offset_grid)//set the drawable of the multiple dividing lines
		       .redrawDivider(2)//custom any one of the dividing lines(exclude the head,bottom,leftmost,rightmost dividing line)
                       .redrawDividerHeight(30)//valid in vertical direction
		       .redrawDividerWidth(30)//valid in horizontal direction
                       .redrawDividerDrawable(R.drawable.bg_divider_redraw_grid)//set the drawable of the dividing line
		       .redrawHeaderDivider()//custom the head dividing line，valid in vertical direction
                       .redrawHeaderDividerHeight(40)
                       .redrawHeaderDividerDrawable(R.drawable.bg_divider_offset_grid);
		       .redrawFooterDivider()//custom the bottom dividing line，valid in vertical direction
                       .redrawFooterDividerHeight(40)
                       .redrawFooterDividerDrawable(R.drawable.bg_divider_offset_grid)
		       .redrawLeftDivider()//custom the leftmost dividing line，valid in horizontal direction
                       .redrawLeftDividerWidth(40)
                       .redrawLeftDividerDrawable(R.drawable.bg_divider_list)
		       .redrawRightDivider()//custom the rightmost dividing line，valid in horizontal direction
                       .redrawRightDividerWidth(40)
                       .redrawRightDividerDrawable(R.drawable.bg_divider_list);
		recyclerView.addItemDecoration(builder.build());
```

#### 2.DividerLinearItemDecoration
```
		DividerLinearItemDecoration.Builder builder = new DividerLinearItemDecoration.Builder(context, orientation);//orientation:方向
		builder.setDivider(R.drawable.bg_divider_list)//set the drawable of the dividing line
                       .removeHeaderDivider(false)//valid in vertical direction
                       .removeFooterDivider(false)//valid in vertical direction
                       .removeLeftDivider(false)//valid in horizontal direction
                       .removeRightDivider(false)//valid in horizontal direction
		       .subDivider(1, 4);//custom multiple lines
		       .setSubDividerHeight(24)//valid in vertical direction
                       .setSubDividerWidth(24)//valid in horizontal direction
                       .setSubDividerDrawable(R.drawable.bg_divider_offset)
		       .redrawDivider(2)//custom any one of the dividing lines(exclude the head,bottom,leftmost,rightmost dividing line)
                       .redrawDividerHeight(30)//valid in vertical direction
		       .redrawDividerWidth(30)//valid in horizontal direction
                       .redrawDividerDrawable(R.drawable.bg_divider_redraw)
		       .redrawHeaderDivider()//custom the head dividing line，valid in vertical direction
                       .redrawHeaderDividerHeight(40)
                       .redrawHeaderDividerDrawable(R.drawable.bg_divider_offset);
		       .redrawFooterDivider()//custom the bottom dividing line，valid in vertical direction
                       .redrawFooterDividerHeight(40)
                       .redrawFooterDividerDrawable(R.drawable.bg_divider_offset)
		       .redrawLeftDivider()//custom the leftmost dividing line，valid in horizontal direction
                       .redrawLeftDividerWidth(40)
                       .redrawLeftDividerDrawable(R.drawable.bg_divider_list)
		       .redrawRightDivider()custom the rightmost dividing line，valid in horizontal direction
                       .redrawRightDividerWidth(40)
                       .redrawRightDividerDrawable(R.drawable.bg_divider_list);
		recyclerView.addItemDecoration(builder.build());
```
## My Other Project
[PageRecyclerView](https://github.com/ckrgithub/PageRecyclerView)

[CollapsingRefresh](https://github.com/ckrgithub/CollapsingRefresh)

License
-------

    Copyright 2018 ckrgithub

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
