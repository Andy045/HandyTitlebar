# 效果

![](http://cos.handy045.com/blog/2019-11-21-titlebar_demo.png)

# 布局

## 布局元素

首先说下HandyTitleBar的布局元素：
1. Statusbar：状态栏（使用沉浸式功能时，将失去系统的状态栏。此时我们需要写一个状态栏来占位）
2. TopLine：顶部分割线（分割状态栏和标题栏，可借此实现两栏分离，或者给顶部分割线设置透明颜色时可实现标题栏下移的效果）
3. TitleBar：标题栏（主要用来填充标题栏背景）
    1. LeftActionsLayout：左侧按钮容器
        1. Action：动作按钮
    2. ContentLayout：标题栏内容容器
        1. MainTextView：主标题
        2. SubTextView：副标题
    3. RightActionsLayout：右侧按钮容器
        1. Action：动作按钮
4. BottomLine：底部分割线（给标题栏画界）

## 布局设计

![](http://cos.handy045.com/blog/2019-11-19-HandyTitlebar_Layout.png)

# 设计思路

从自上而下从左到右的顺序考虑：
    
1. 实现沉浸式时，系统的状态栏会消失，此时布局的左上角将在整个手机屏幕的左上角位置。为此我们需要一个View进行实现"状态栏"并支持背景颜色和高度的修改。
2. 然后是状态栏和标题栏之间的分割线。某些情况状态栏和标题栏都将会是透明的（比如扫描或拍照界面），此时我们需要顶部分割线来标记出状态栏和标题栏的位置。
3. 标题栏是一个水平的线性布局，其中有三个元素：
    1. 左侧动作按钮水平线性布局容器，用来动态添加点击动作按钮（例如：返回操作）
    2. 标题栏内容，用来呈现标题名称的垂直的线性布局，包含了两个带跑马灯功能的TextView。
    3. 右侧动作按钮水平线性布局容器，用来动态添加点击动作按钮（例如：菜单、拍照、刷新等操作）
4. 最后就是用来标记标题栏边界的底部分割线。
5. 使用[HandyBasicUI](https://handy045.com/Android/UI/HandyFrame/HandyBasicUI/)实现UI色彩规范和尺寸的屏幕适配。

# 样式属性

属性描述|属性名|类型|默认值
---|---|---|---
是否使用沉浸式（自定义状态栏）|hd_isTranslucentStatus|boolean|false
状态栏高度|hd_statusbarHeight|dimension|system(60px)
状态栏背景颜色|hd_statusbarBackgroundColor|color|google_lightbluea700
|||
顶部分割线高度|hd_topLineHeight|dimension|hd_dp0
顶部分割线背景颜色|hd_topLineColor|color|google_grey500
|||
标题栏外边距|hd_titlebarMargin|dimension|hd_dp0
标题栏外边距-上|hd_titlebarMarginTop|dimension|hd_dp0
标题栏外边距-左|hd_titlebarMarginLeft|dimension|hd_dp0
标题栏外边距-右|hd_titlebarMarginRight|dimension|hd_dp0
标题栏外边距-下|hd_titlebarMarginBottom|dimension|hd_dp0
标题栏高度|hd_titlebarHeight|dimension|hd_dp48
标题栏背景|hd_titlebarBackground|reference/color|google_lightbluea700
|||
文本容器内边距|hd_contentLayoutPadding|dimension|hd_dp8
文本容器布局方向|hd_contentLayoutOrientation|enum（1:vertical，2:horizontal）|1（vertical）
文本容器布局水平布局时，主、副标题的距离|hd_textMarginH|dimension|hd_dp2
文本容器布局垂直布局时，主、副标题的距离|hd_textMarginV|dimension|hd_dp1
主标题内容|hd_mainText|string|""
主标题字体大小|hd_mainTextSize|dimension|hd_sp16
主标题字体颜色|hd_mainTextColor|color|web_white
主标题背景颜色|hd_mainTextBackgroundColor|color|web_transparent
副标题内容|hd_subText|string|""
副标题字体大小|hd_subTextSize|dimension|hd_sp12
副标题字体颜色|hd_subTextColor|color|web_white
副标题背景颜色|hd_subTextBackgroundColor|color|web_transparent
|||
底部分割线高度|hd_bottomLineHeight|dimension|hd_dp1
底部分割线背景颜色|hd_bottomLineColor|color|google_grey500
|||
两侧按钮距离标题栏左右两边的距离|hd_actionParentMargin|dimension|hd_dp9
按钮内边距|hd_actionViewPadding|dimension|hd_dp4
按钮文字与图片的距离|hd_actionSpacing|dimension|hd_dp4
按钮文字字体大小|hd_actionTextSize|dimension|hd_sp12
按钮文字颜色|hd_actionTextColor|color|web_white
按钮图片大小|hd_actionImageSize|dimension|hd_dp16

# 公开方法

属性描述|设置方法名
---|---
Titlebar|
状态栏高度|setStatusbarHeight
状态栏背景颜色|setStatusbarBackgroundColor
顶部分割线高度|setTopLineHeight
顶部分割线背景颜色|setTopLineColor
标题栏外边距|setTitlebarMargin
标题栏外边距-上|setTitlebarMarginTop
标题栏外边距-左|setTitlebarMarginLeft
标题栏外边距-右|setTitlebarMarginRight
标题栏外边距-下|setTitlebarMarginBottom
标题栏高度|setTitlebarHeight
标题栏背景|setTitlebarBackground
主标题字体大小|setMainTextSize
主标题字体颜色|setMainTextColor
主标题背景颜色|setMainTextBackgroundColor
副标题字体大小|setSubTextSize
副标题字体颜色|setSubTextColor
副标题背景颜色|setSubTextBackgroundColor
标题内容|setTitleText
文本容器布局方向|setContentLayoutOrientation
底部分割线高度|setBottomLineHeight
底部分割线背景颜色|setBottomLineColor
按钮文字字体大小|setActionTextSize
按钮文字颜色|setActionTextColor
按钮图片大小|setActionImageSize
添加一个左侧按钮|addLeftAction
添加一个右侧按钮|addRightAction
移除所有的左侧按钮|removeLeftActions
移除指定的左侧按钮|removeLeftAction
移除所有的右侧按钮|removeRightActions
移除指定的右侧按钮|removeRightAction
移除所有按钮|removeAllActions
|
Action|
设置按钮文字|setText
设置按钮文字字体大小|setTextSize
设置按钮图片|setImageSrc
设置按钮图片大小|setImageSize
设置按钮图片与文字的间距|setInsideSpacing
统一设置按钮图片和文字的点击效果（颜色变化）|syncTextImage

# 接入方式

1、 在工程的build.gradle增加maven地址

```xml
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2、 在library的build.gradle增加依赖

```xml
dependencies {
        implementation 'com.github.Handy045:HandyTitleBar:最新版本'
}
```

最新版本：[![](https://jitpack.io/v/Handy045/HandyTitleBar.svg)](https://jitpack.io/#Handy045/HandyTitleBar)

# 依赖说明

· [HandyBasicUI - 通用UI适配](https://handy045.com/Android/UI/HandyFrame/HandyBasicUI/)
