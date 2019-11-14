package com.handy.titlebar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.handy.titlebar.entity.StyleBuilder
import com.handy.titlebar.utils.HandyTitleBarUtils
import com.handy.titlebar.widget.MarqueeTextView


/**
 * @title: HandyTitleBar
 * @projectName HandyTitlebar
 * @description: 支持沉浸式，可以在xml布局中通过自定义属性配置标题栏样式
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-12 15:59
 */
@SuppressLint("CustomViewStyleable")
class HandyTitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    //============================================================
    //  私有配置
    //============================================================

    private var displayWidth: Int = HandyTitleBarUtils.getScreenWidth(context)

    private var parentPaddingTop: Int = 0
    private var parentPaddingLeft: Int = 0
    private var parentPaddingRight: Int = 0
    private var parentPaddingBottom: Int = 0
    private var parentWidth: Int = 0
    private var parentHeight: Int = 0

    private var statusBar: View = View(context)
    private var topLineView: View = View(context)
    private var titleBar: View = View(context)
    private var leftActionsLayout: LinearLayout = LinearLayout(context)
    private var mainTextView: MarqueeTextView = MarqueeTextView(context)
    private var subTextView: MarqueeTextView = MarqueeTextView(context)
    private var contentLayout: LinearLayout = LinearLayout(context)
    private var rightActionsLayout: LinearLayout = LinearLayout(context)
    private var bottomLineView: View = View(context)

    //============================================================
    //  公有配置
    //============================================================

    var styleBuilder: StyleBuilder = StyleBuilder(context, attrs, resources)

    //============================================================
    //  方法区
    //============================================================
    companion object {
        val VERTICAL = 1
        val HORIZONTAL = 0
    }

    init {
        // 状态栏
        statusBar.setBackgroundColor(styleBuilder.statusBarBackgroundColor)
        if (styleBuilder.isShowCustomStatusBar) {
            val activity = getActivity()
            if (activity != null) {
                showCustomStatusBar(activity)
            }
        }
        // 顶部分割线
        topLineView.setBackgroundColor(styleBuilder.topLineColor)
        // 标题栏
        titleBar.background = styleBuilder.titleBarBackground
        // 左侧按钮
        leftActionsLayout.orientation = LinearLayout.HORIZONTAL
        leftActionsLayout.setBackgroundColor(Color.TRANSPARENT)
        leftActionsLayout.gravity = Gravity.CENTER
        // 主标题
        mainTextView.text = styleBuilder.mainText
        mainTextView.setTextColor(styleBuilder.mainTextColor)
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.mainTextSize.toFloat())
        mainTextView.setBackgroundColor(styleBuilder.mainTextBackgroundColor)
        mainTextView.setSingleLine()
        mainTextView.gravity = Gravity.CENTER
        mainTextView.includeFontPadding = false
        mainTextView.ellipsize = TextUtils.TruncateAt.MARQUEE
        mainTextView.visibility =
            if (styleBuilder.mainText.isNullOrEmpty()) View.GONE else View.VISIBLE
        // 副标题
        subTextView.text = styleBuilder.subText
        subTextView.setTextColor(styleBuilder.subTextColor)
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.subTextSize.toFloat())
        subTextView.setBackgroundColor(styleBuilder.subTextBackgroundColor)
        subTextView.setSingleLine()
        subTextView.gravity = Gravity.CENTER
        subTextView.includeFontPadding = false
        subTextView.ellipsize = TextUtils.TruncateAt.MARQUEE
        subTextView.visibility =
            if (styleBuilder.subText.isNullOrEmpty()) View.GONE else View.VISIBLE
        // 标题文本容器
        contentLayout.orientation = styleBuilder.contentLayoutOrientation
        contentLayout.gravity = Gravity.CENTER
        contentLayout.setBackgroundColor(Color.TRANSPARENT)
        contentLayout.setPadding(
            styleBuilder.contentLayoutPadding.toInt(), 0,
            styleBuilder.contentLayoutPadding.toInt(), 0
        )
        contentLayout.addView(
            mainTextView,
            LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
        contentLayout.addView(
            subTextView,
            LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
        // 右侧按钮
        rightActionsLayout = LinearLayout(context)
        rightActionsLayout.orientation = LinearLayout.HORIZONTAL
        rightActionsLayout.setBackgroundColor(Color.TRANSPARENT)
        rightActionsLayout.gravity = Gravity.CENTER
        // 底部分割线
        bottomLineView = View(context)
        bottomLineView.setBackgroundColor(styleBuilder.bottomLineColor)

        addView(
            statusBar,
            LayoutParams(LayoutParams.MATCH_PARENT, styleBuilder.statusBarHeight.toInt())
        )
        addView(
            topLineView,
            LayoutParams(LayoutParams.MATCH_PARENT, styleBuilder.topLineHeight.toInt())
        )
        addView(
            titleBar,
            LayoutParams(LayoutParams.MATCH_PARENT, styleBuilder.titleBarHeight.toInt())
        )
        addView(
            leftActionsLayout,
            LayoutParams(LayoutParams.WRAP_CONTENT, styleBuilder.titleBarHeight.toInt())
        )
        addView(
            contentLayout,
            LayoutParams(LayoutParams.WRAP_CONTENT, styleBuilder.titleBarHeight.toInt())
        )
        addView(
            rightActionsLayout,
            LayoutParams(LayoutParams.WRAP_CONTENT, styleBuilder.titleBarHeight.toInt())
        )
        addView(
            bottomLineView,
            LayoutParams(LayoutParams.MATCH_PARENT, styleBuilder.bottomLineHeight.toInt())
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (subTextView.visibility == View.GONE) {
            mainTextView.setPadding(0, 0, 0, 0)
            subTextView.setPadding(0, 0, 0, 0)
        } else {
            if (contentLayout.orientation == VERTICAL) {
                mainTextView.setPadding(0, 0, 0, styleBuilder.textMarginV.toInt())
                subTextView.setPadding(0, styleBuilder.textMarginV.toInt(), 0, 0)
            } else if (contentLayout.orientation == HORIZONTAL) {
                mainTextView.setPadding(0, 0, styleBuilder.textMarginH.toInt(), 0)
                subTextView.setPadding(styleBuilder.textMarginH.toInt(), 0, 0, 0)
            }
        }

        parentPaddingTop =
            if (styleBuilder.titleBarPadding > 0) styleBuilder.titleBarPadding.toInt() else styleBuilder.titleBarPaddingTop.toInt()
        parentPaddingLeft =
            if (styleBuilder.titleBarPadding > 0) styleBuilder.titleBarPadding.toInt() else styleBuilder.titleBarPaddingLeft.toInt()
        parentPaddingRight =
            if (styleBuilder.titleBarPadding > 0) styleBuilder.titleBarPadding.toInt() else styleBuilder.titleBarPaddingRight.toInt()
        parentPaddingBottom =
            if (styleBuilder.titleBarPadding > 0) styleBuilder.titleBarPadding.toInt() else styleBuilder.titleBarPaddingBottom.toInt()

        parentWidth = if (widthMode != MeasureSpec.AT_MOST) widthSize else displayWidth
        parentHeight =
            if (heightMode != MeasureSpec.AT_MOST) heightSize else (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + styleBuilder.bottomLineHeight).toInt()

        measureChild(
            statusBar,
            MeasureSpec.makeMeasureSpec(displayWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(styleBuilder.statusBarHeight.toInt(), MeasureSpec.EXACTLY)
        )
        measureChild(
            titleBar,
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(styleBuilder.titleBarHeight.toInt(), MeasureSpec.EXACTLY)
        )
        measureChild(
            topLineView,
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(styleBuilder.topLineHeight.toInt(), MeasureSpec.EXACTLY)
        )
        measureChild(
            bottomLineView,
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(styleBuilder.bottomLineHeight.toInt(), MeasureSpec.EXACTLY)
        )
        measureChild(
            leftActionsLayout,
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(styleBuilder.titleBarHeight.toInt(), MeasureSpec.EXACTLY)
        )
        measureChild(
            rightActionsLayout,
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(styleBuilder.titleBarHeight.toInt(), MeasureSpec.EXACTLY)
        )

        if (leftActionsLayout.measuredWidth > rightActionsLayout.measuredWidth) {
            contentLayout.measure(
                MeasureSpec.makeMeasureSpec(
                    (parentWidth - 2 * leftActionsLayout.measuredWidth - 2 * styleBuilder.actionParentMargin).toInt(),
                    MeasureSpec.EXACTLY
                ),
                MeasureSpec.makeMeasureSpec(
                    styleBuilder.titleBarHeight.toInt(),
                    MeasureSpec.EXACTLY
                )
            )
        } else {
            contentLayout.measure(
                MeasureSpec.makeMeasureSpec(
                    (parentWidth - 2 * rightActionsLayout.measuredWidth - 2 * styleBuilder.actionParentMargin).toInt(),
                    MeasureSpec.EXACTLY
                ),
                MeasureSpec.makeMeasureSpec(
                    styleBuilder.titleBarHeight.toInt(),
                    MeasureSpec.EXACTLY
                )
            )
        }

        setMeasuredDimension(displayWidth, parentHeight + paddingTop + paddingBottom)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        statusBar.layout(0, 0, displayWidth, styleBuilder.statusBarHeight.toInt())

        topLineView.layout(
            0,
            styleBuilder.statusBarHeight.toInt(),
            parentWidth,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight).toInt()
        )

        titleBar.layout(
            paddingLeft,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
            parentWidth - paddingRight,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
        )

        if (leftActionsLayout.childCount > 0) {
            leftActionsLayout.layout(
                (paddingLeft + styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
                (paddingLeft + styleBuilder.actionParentMargin + leftActionsLayout.measuredWidth).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
            )
        }

        if (leftActionsLayout.childCount > 0 || rightActionsLayout.childCount > 0) {
            if (leftActionsLayout.measuredWidth > rightActionsLayout.measuredWidth) {
                contentLayout.layout(
                    (leftActionsLayout.measuredWidth + paddingLeft + styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
                    (parentWidth - leftActionsLayout.measuredWidth - paddingRight - styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
                )
            } else {
                contentLayout.layout(
                    (rightActionsLayout.measuredWidth + paddingLeft + styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
                    (parentWidth - rightActionsLayout.measuredWidth - paddingRight - styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
                )
            }
        } else {
            contentLayout.layout(
                paddingLeft,
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
                parentWidth - paddingRight,
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
            )
        }

        if (rightActionsLayout.childCount > 0) {
            rightActionsLayout.layout(
                (parentWidth - rightActionsLayout.measuredWidth - paddingRight - styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + paddingTop).toInt(),
                (parentWidth - paddingRight - styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt()
            )
        }

        bottomLineView.layout(
            paddingLeft,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + paddingTop).toInt(),
            parentWidth - paddingRight,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + styleBuilder.bottomLineHeight + paddingTop).toInt()
        )
    }

    /**
     * 设置系统状态栏是否可见，安卓系统版本大于等于19
     */
    fun showCustomStatusBar(activity: Activity): HandyTitleBar {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        }

        requestLayout()
        return this
    }

    /**
     * 从View内部获取到Activity的实例
     */
    private fun getActivity(): Activity? {
        var context: Context? = context
        for (i in 0..9) {
            if (context == null) {
                return null
            } else if (context is Activity) {
                return context
            } else {
                try {
                    context = (context as ContextWrapper).baseContext
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}