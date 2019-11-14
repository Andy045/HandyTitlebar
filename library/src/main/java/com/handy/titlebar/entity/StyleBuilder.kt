package com.handy.titlebar.entity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.handy.titlebar.R

/**
 * @title: StyleBuilder
 * @projectName HandyTitlebar
 * @description: 标题栏属性对象
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-13 09:22
 */
class StyleBuilder constructor(context: Context, attrs: AttributeSet?, resources: Resources) {

    //============================================================
    //  状态栏
    //============================================================

    var isShowCustomStatusBar: Boolean = false
    var statusBarHeight: Float = 0f
    @ColorInt
    var statusBarBackgroundColor: Int = 0

    //============================================================
    //  顶部分割线
    //============================================================

    var topLineHeight: Float = 0f
    @ColorInt
    var topLineColor: Int = 0

    //============================================================
    //  标题栏
    //============================================================

    var titleBarPadding: Float = 0f
    var titleBarPaddingTop: Float = 0f
    var titleBarPaddingLeft: Float = 0f
    var titleBarPaddingRight: Float = 0f
    var titleBarPaddingBottom: Float = 0f
    var titleBarHeight: Float = 0f
    var titleBarBackground: Drawable? = null

    var contentLayoutPadding: Float = 0f
    var contentLayoutOrientation: Int = 1
    var textMarginH: Float = 0f
    var textMarginV: Float = 0f
    var mainText: String? = null
    var mainTextSize: Int = 0
    @ColorInt
    var mainTextColor: Int = 0
    @ColorInt
    var mainTextBackgroundColor: Int = 0
    var subText: String? = null
    var subTextSize: Int = 0
    @ColorInt
    var subTextColor: Int = 0
    @ColorInt
    var subTextBackgroundColor: Int = 0

    //============================================================
    //  底部分割线
    //============================================================

    var bottomLineHeight: Float = 0f
    @ColorInt
    var bottomLineColor: Int = 0

    //============================================================
    //  按钮
    //============================================================

    var actionParentMargin: Float = 0f
    var actionViewPadding: Float = 0f
    var actionSpacing: Float = 0f
    var actionTextSize: Int = 0
    @ColorInt
    var actionTextColor: Int = 0
    var actionImageSize: Float = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HandyTitleBarStyleable)

        // 状态栏
        this.isShowCustomStatusBar = typedArray.getBoolean(
            R.styleable.HandyTitleBarStyleable_handy_isShowCustomStatusBar,
            false
        )
        this.statusBarHeight = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_statusBarHeight,
            if (isShowCustomStatusBar) getStatusBarHeight(context) else 0f
        )
        this.statusBarBackgroundColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_statusBarBackgroundColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_statusBarBackgroundColor)
        )

        // 顶部分割线
        this.topLineHeight =
            typedArray.getDimension(
                R.styleable.HandyTitleBarStyleable_handy_topLineHeight,
                resources.getDimension(R.dimen.handyTitleBar_topLineHeight)
            )
        this.topLineColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_topLineColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_topLineColor)
        )

        // 标题栏
        this.titleBarPadding = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarPadding,
            resources.getDimension(R.dimen.hdb_dp0)
        )
        this.titleBarPaddingTop = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingTop,
            resources.getDimension(R.dimen.hdb_dp0)
        )
        this.titleBarPaddingLeft = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingLeft,
            resources.getDimension(R.dimen.hdb_dp0)
        )
        this.titleBarPaddingRight = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingRight,
            resources.getDimension(R.dimen.hdb_dp0)
        )
        this.titleBarPaddingBottom = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingBottom,
            resources.getDimension(R.dimen.hdb_dp0)
        )
        this.titleBarHeight = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_titleBarHeight,
            resources.getDimension(R.dimen.hdb_dp48)
        )
        this.titleBarBackground =
            typedArray.getDrawable(R.styleable.HandyTitleBarStyleable_handy_titleBarBackground)
        if (this.titleBarBackground == null) {
            this.titleBarBackground = ColorDrawable(
                ContextCompat.getColor(context, R.color.handyTitleBar_titleBarBackground)
            )
        }
        this.contentLayoutPadding = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_contentLayoutPadding,
            resources.getDimension(R.dimen.hdb_dp8)
        )
        this.contentLayoutOrientation = typedArray.getInt(
            R.styleable.HandyTitleBarStyleable_handy_contentLayoutOrientation,
            1
        )
        this.textMarginH = resources.getDimension(R.dimen.hdb_dp2)
        this.textMarginV = resources.getDimension(R.dimen.hdb_dp1)
        this.mainText = typedArray.getString(R.styleable.HandyTitleBarStyleable_handy_mainText)
        this.mainTextSize = typedArray.getDimensionPixelSize(
            R.styleable.HandyTitleBarStyleable_handy_mainTextSize,
            resources.getDimension(R.dimen.handyTitleBar_mainTextSize).toInt()
        )
        this.mainTextColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_mainTextColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_mainTextColor)
        )
        this.mainTextBackgroundColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_mainTextBackgroundColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_mainTextBackgroundColor)
        )
        this.subText = typedArray.getString(R.styleable.HandyTitleBarStyleable_handy_subText)
        this.subTextSize = typedArray.getDimensionPixelSize(
            R.styleable.HandyTitleBarStyleable_handy_subTextSize,
            resources.getDimension(R.dimen.handyTitleBar_subTextSize).toInt()
        )
        this.subTextColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_subTextColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_subTextColor)
        )
        this.subTextBackgroundColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_subTextBackgroundColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_subTextBackgroundColor)
        )

        // 底部分割线
        this.bottomLineHeight = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_bottomLineHeight,
            resources.getDimension(R.dimen.handyTitleBar_bottomLineHeight)
        )
        this.bottomLineColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_bottomLineColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_bottomLineColor)
        )

        // 按钮
        this.actionParentMargin = resources.getDimension(R.dimen.hdb_dp9)
        this.actionViewPadding = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_actionViewPadding,
            resources.getDimension(R.dimen.handyTitleBar_actionViewPadding)
        )
        this.actionSpacing = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_actionSpacing,
            resources.getDimension(R.dimen.handyTitleBar_actionSpacing)
        )
        this.actionTextSize = typedArray.getDimensionPixelSize(
            R.styleable.HandyTitleBarStyleable_handy_actionTextSize,
            resources.getDimension(R.dimen.handyTitleBar_actionTextSize).toInt()
        )
        this.actionTextColor = typedArray.getColor(
            R.styleable.HandyTitleBarStyleable_handy_actionTextColor,
            ContextCompat.getColor(context, R.color.handyTitleBar_actionTextColor)
        )
        this.actionImageSize = typedArray.getDimension(
            R.styleable.HandyTitleBarStyleable_handy_actionImageSize,
            resources.getDimension(R.dimen.handyTitleBar_actionImageSize)
        )

        typedArray.recycle()
    }

    @SuppressLint("PrivateApi")
    private fun getStatusBarHeight(context: Context): Float {
        try {
            val obj = Class.forName("com.android.internal.R\$dimen").newInstance()
            val field = Class.forName("com.android.internal.R\$dimen").getField("status_bar_height")
            return context.resources.getDimensionPixelSize(Integer.parseInt(field.get(obj)!!.toString()))
                .toFloat()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
}