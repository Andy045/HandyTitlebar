<h1>可扩展标题栏控件</h1>

# 最新版本

[![](https://jitpack.io/v/Handy045/HandyTitleBar.svg)](https://jitpack.io/#Handy045/HandyTitleBar)

# Javadoc文档查看

[最新版本-Javadoc文档](https://javadoc.jitpack.io/com/github/Handy045/HandyTitleBar/latest/javadoc/)

# 项目引用

## Step 1.添加maven地址到Project的build.gradle配置文件中

```javascript
buildscript {
  repositories {
    ......
    maven { url 'https://jitpack.io' }
  }
  ......
}

allprojects {
  repositories {
    ......
    maven { url 'https://jitpack.io' }
  }
}
```

## Step 2.添加api引用到Module的build.gradle配置文件中

```javascript
dependencies {
  ...
  api 'com.github.Handy045:HandyTitleBar:最新版本号'
}
```

# 自定义标题栏控件

[使用说明](https://handy045.com/2018/05/22/Android/Handy/HandyTitleBar/)

# 布局样式和组织结构

HandyTitleBar的布局元素：

1. Statusbar：状态栏（使用沉浸式功能时，将失去系统的状态栏。此时我们需要写一个状态栏来占位）
2. TopLine：顶部分割线（分割状态栏和标题栏，可借此实现两栏分离，或者给顶部分割线设置透明颜色时可实现标题栏下移的效果）
3. TitleBar：标题栏（主要用来填充标题栏背景）
4. LeftActionsLayout：左侧按钮容器
    1. Action：动作按钮
5. ContentLayout：标题栏内容容器
    1.MainTextView：主标题
    2.SubTextView：副标题
6. RightActionsLayout：右侧按钮容器
    1. Action：动作按钮
7. BottomLine：底部分割线（给标题栏画界）

![](https://github.com/Handy045/HandyTitleBar/blob/master/document/绘图1.jpeg?raw=true)

![](https://github.com/Handy045/HandyTitleBar/blob/master/document/绘图2.jpeg?raw=true)

# 自定义属性说明

|属性名称|中文描述|类型|默认值
|:---|:---|:---|:---|
|handy_statusBarHeight|状态栏高度|dimension|0
|handy_statusBarBackgroundColor|状态栏背景颜色|color|0xFF0091ea
|handy_isShowCustomStatusBar|是否使用自定义状态栏（是否启用沉浸式状态栏）|boolean|false
|handy_topLineHeight|顶部分割线高度|dimension|0
|handy_topLineColor|顶部分割线颜色|color|Color.GRAY
|handy_titleBarPadding|标题栏内边距|dimension|0
|handy_titleBarPaddingTop|标题栏顶部内边距|dimension|0
|handy_titleBarPaddingLeft|标题栏左侧内边距|dimension|0
|handy_titleBarPaddingRight|标题栏右侧内边距|dimension|0
|handy_titleBarPaddingBottom|标题栏底部内边距|dimension|0
|handy_titleBarHeight|标题栏高度|dimension|48dp
|handy_titleBarBackground|标题栏背景|reference或color|0xFFFFFFFF
|handy_mainText|主标题内容|string|null
|handy_mainTextSize|主标题字体大小|dimension|17sp
|handy_mainTextColor|主标题字体颜色|color|Color.BLACK
|handy_mainTextBackgroundColor|主标题背景颜色|color|Color.TRANSPARENT
|handy_subText|副标题内容|string|null
|handy_subTextSize|副标题字体大小|dimension|11sp
|handy_subTextColor|副标题字体颜色|color|Color.BLACK
|handy_subTextBackgroundColor|副标题背景颜色|color|Color.TRANSPARENT
|handy_bottomLineHeight|底部分割线高度|dimension|0
|handy_bottomLineColor|底部分割线颜色|color|Color.GRAY
|handy_actionViewPadding|动作按钮容器左右侧内边距|dimension|8dp
|handy_actionLayoutPadding|动作按钮左右侧内边距|dimension|4dp
|handy_actionTextSize|动作按钮字体大小|dimension|13sp
|handy_actionTextColor|动作按钮字体颜色|color|Color.BLACK
|handy_actionImageSize|动作按钮图片大小|dimension|18dp
|handy_actionImageSrc|动作按钮图片资源|reference|0

# 常用方法说明

## 布局控件元素获取

开放布局元素获取方法，支撑更多的改变

|获取方法|返回对象|返回类型
|:---|:---|:---
|getStatusBar()|statusBar|View
|getTopLineView()|topLineView|View
|getTitleBar()|titleBar|View
|getLeftActionsLayout()|leftActionsLayout|LinearLayout
|getContentLayout()|contentLayout|LinearLayout
|getMainTextView()|mainTextView|HandyTitleBar.MarqueeTextView
|getSubTextView()|subTextView|HandyTitleBar.MarqueeTextView
|getRightActionsLayout()|rightActionsLayout|LinearLayout
|getBottomLineView()|bottomLineView|View

## 属性或样式修改方法

|影响元素|方法名称|方法描述
|:---|:---|:---
|statusBar|setStatusBarHeight(int statusBarHeight)|状态栏高度
|statusBar|setStatusBarBackgroundColor(@ColorInt int statusBarBackgroundColor)|状态栏背景颜色
|statusBar|setShowCustomStatusBar(Activity activity, boolean showCustomStatusBar)|是否使用自定义的状态栏（开启沉浸式）
|topLineView|setTopLineHeight(int topLineHeight)|顶部分割线高度
|topLineView|setTopLineColor(@ColorInt int topLineColor)|顶部分割线背景颜色
|titleBar|setTitleBarPadding(int titleBarPadding)|标题栏内边距
|titleBar|setTitleBarPaddingTop(int titleBarPaddingTop)|标题栏顶部内边距
|titleBar|setTitleBarPaddingLeft(int titleBarPaddingLeft)|标题栏左侧内边距
|titleBar|setTitleBarPaddingRight(int titleBarPaddingRight)|标题栏右侧内边距
|titleBar|setTitleBarPaddingBottom(int titleBarPaddingBottom)|标题栏底部内边距
|titleBar|setTitleBarHeight(int titleBarHeight)|标题栏高度
|titleBar|setTitleBarBackground(int titleBarBackground)|标题栏背景（颜色ID或图片ID）
|titleBar|setTitleBarBackground(Drawable titleBarBackground)|标题栏背景（图片Drawable）
|mainTextView&subTextView|setTitleBarContent(CharSequence title)|设置标题栏内容（\n垂直布局，\t水平布局）
|mainTextView|setMainText(String mainText)|主标题内容
|mainTextView|setMainTextSize(float mainTextSize)|主标题字体大小
|mainTextView|setMainTextColor(@ColorInt int mainTextColor)|主标题字体颜色
|mainTextView|setMainTextBackgroundColor(@ColorInt int mainTextBackgroundColor)|主标题背景颜色
|subTextView|setSubText(String subText)|副标题内容
|subTextView|setSubTextSize(float subTextSize)|副标题字体大小
|subTextView|setSubTextColor(@ColorInt int subTextColor)|副标题字体颜色
|subTextView|setSubTextBackgroundColor(@ColorInt int subTextBackgroundColor)|副标题背景颜色
|bottomLineView|setBottomLineHeight(int bottomLineHeight)|底部分割线高度
|bottomLineView|setBottomLineColor(int bottomLineColor)|底部分割线背景颜色

# 部分效果展示

![](https://github.com/Handy045/HandyTitleBar/blob/master/document/2018-05-23_22-18-55.png?raw=true)