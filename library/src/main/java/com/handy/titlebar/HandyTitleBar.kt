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
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
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

    private var parentWidth: Int = 0
    private var parentHeight: Int = 0
    private var parentMarginTop: Int = 0
    private var parentMarginLeft: Int = 0
    private var parentMarginRight: Int = 0
    private var parentMarginBottom: Int = 0

    private var statusBar: View = View(context)
    private var topLineView: View = View(context)
    private var titleBar: View = View(context)
    private var leftActionsLayout: LinearLayout = LinearLayout(context)
    private var mainTextView: MarqueeTextView = MarqueeTextView(context)
    private var subTextView: MarqueeTextView = MarqueeTextView(context)
    private var contentLayout: LinearLayout = LinearLayout(context)
    private var rightActionsLayout: LinearLayout = LinearLayout(context)
    private var bottomLineView: View = View(context)

    private var styleBuilder: StyleBuilder = StyleBuilder(context, attrs, resources)

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
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.mainTextSize)
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
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.subTextSize)
        subTextView.setBackgroundColor(styleBuilder.subTextBackgroundColor)
        subTextView.setSingleLine()
        subTextView.gravity = Gravity.CENTER
        subTextView.includeFontPadding = false
        subTextView.ellipsize = TextUtils.TruncateAt.MARQUEE
        subTextView.visibility =
            if (styleBuilder.subText.isNullOrEmpty()) View.GONE else View.VISIBLE

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
        rightActionsLayout.orientation = LinearLayout.HORIZONTAL
        rightActionsLayout.setBackgroundColor(Color.TRANSPARENT)
        rightActionsLayout.gravity = Gravity.CENTER
        // 底部分割线
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

        parentWidth = if (widthMode != MeasureSpec.AT_MOST) widthSize else displayWidth
        parentHeight =
            if (heightMode != MeasureSpec.AT_MOST) heightSize else (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + styleBuilder.bottomLineHeight).toInt()
        parentMarginTop =
            if (styleBuilder.titleBarMargin > 0) styleBuilder.titleBarMargin.toInt() else styleBuilder.titleBarMarginTop.toInt()
        parentMarginLeft =
            if (styleBuilder.titleBarMargin > 0) styleBuilder.titleBarMargin.toInt() else styleBuilder.titleBarMarginLeft.toInt()
        parentMarginRight =
            if (styleBuilder.titleBarMargin > 0) styleBuilder.titleBarMargin.toInt() else styleBuilder.titleBarMarginRight.toInt()
        parentMarginBottom =
            if (styleBuilder.titleBarMargin > 0) styleBuilder.titleBarMargin.toInt() else styleBuilder.titleBarMarginBottom.toInt()

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

        setMeasuredDimension(displayWidth, parentHeight + parentMarginTop + parentMarginBottom)
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
            parentMarginLeft,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
            parentWidth - parentMarginRight,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
        )

        if (leftActionsLayout.childCount > 0) {
            leftActionsLayout.layout(
                (parentMarginLeft + styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
                (parentMarginLeft + styleBuilder.actionParentMargin + leftActionsLayout.measuredWidth).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
            )
        }

        if (leftActionsLayout.childCount > 0 || rightActionsLayout.childCount > 0) {
            if (leftActionsLayout.measuredWidth > rightActionsLayout.measuredWidth) {
                contentLayout.layout(
                    (leftActionsLayout.measuredWidth + parentMarginLeft + styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
                    (parentWidth - leftActionsLayout.measuredWidth - parentMarginRight - styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
                )
            } else {
                contentLayout.layout(
                    (rightActionsLayout.measuredWidth + parentMarginLeft + styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
                    (parentWidth - rightActionsLayout.measuredWidth - parentMarginRight - styleBuilder.actionParentMargin).toInt(),
                    (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
                )
            }
        } else {
            contentLayout.layout(
                parentMarginLeft,
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
                parentWidth - parentMarginRight,
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
            )
        }

        if (rightActionsLayout.childCount > 0) {
            rightActionsLayout.layout(
                (parentWidth - rightActionsLayout.measuredWidth - parentMarginRight - styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + parentMarginTop).toInt(),
                (parentWidth - parentMarginRight - styleBuilder.actionParentMargin).toInt(),
                (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt()
            )
        }

        bottomLineView.layout(
            parentMarginLeft,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + parentMarginTop).toInt(),
            parentWidth - parentMarginRight,
            (styleBuilder.statusBarHeight + styleBuilder.topLineHeight + styleBuilder.titleBarHeight + styleBuilder.bottomLineHeight + parentMarginTop).toInt()
        )
    }

    /**
     * 设置系统状态栏是否可见，安卓系统版本大于等于19
     */
    private fun showCustomStatusBar(activity: Activity): HandyTitleBar {
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

    //============================================================
    //  属性修改
    //============================================================

    fun setStatusBarHeight(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.statusBarHeight = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setStatusBarBackgroundColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.statusBarBackgroundColor = ContextCompat.getColor(context, resId)
            statusBar.setBackgroundColor(styleBuilder.statusBarBackgroundColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setTopLineHeight(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.topLineHeight = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setTopLineColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.topLineColor = ContextCompat.getColor(context, resId)
            topLineView.setBackgroundColor(styleBuilder.topLineColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setTitleBarMargin(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarMargin = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarMarginTop(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarMarginTop = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarMarginLeft(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarMarginLeft = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarMarginRight(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarMarginRight = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarMarginBottom(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarMarginBottom = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarHeight(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarHeight = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setTitleBarBackground(@DrawableRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.titleBarBackground = ContextCompat.getDrawable(context, resId)
            titleBar.background = styleBuilder.titleBarBackground
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setMainTextSize(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.mainTextSize = resources.getDimension(resId)
            mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.mainTextSize)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setMainTextColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.mainTextColor = ContextCompat.getColor(context, resId)
            mainTextView.setTextColor(styleBuilder.mainTextColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setMainTextBackgroundColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.mainTextBackgroundColor = ContextCompat.getColor(context, resId)
            mainTextView.setBackgroundColor(styleBuilder.mainTextBackgroundColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setSubTextSize(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.subTextSize = resources.getDimension(resId)
            subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, styleBuilder.subTextSize)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setSubTextColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.subTextColor = ContextCompat.getColor(context, resId)
            subTextView.setTextColor(styleBuilder.subTextColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setSubTextBackgroundColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.subTextBackgroundColor = ContextCompat.getColor(context, resId)
            subTextView.setBackgroundColor(styleBuilder.subTextBackgroundColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setBottomLineHeight(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.bottomLineHeight = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    fun setBottomLineColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.bottomLineColor = ContextCompat.getColor(context, resId)
            bottomLineView.setBackgroundColor(styleBuilder.bottomLineColor)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setActionTextSize(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.actionTextSize = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setActionTextColor(@ColorRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.actionTextColor = ContextCompat.getColor(context, resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun setActionImageSize(@DimenRes resId: Int): HandyTitleBar {
        try {
            styleBuilder.actionImageSize = resources.getDimension(resId)
            requestLayout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }
}