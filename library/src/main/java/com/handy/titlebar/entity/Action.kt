package com.handy.titlebar.entity

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.handy.titlebar.utils.HandyTitlebarUtils

/**
 * @title: Action
 * @projectName HandyTitlebar
 * @description: 标题栏按钮对象
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-12 14:39
 */
abstract class Action {
    //============================================================
    //  布局相关
    //============================================================
    /**
     * 按钮图片与文字的间距
     */
    private var insideSpacing: Float = 0f
    /**
     * 按钮控件
     */
    var view: View? = null
    /**
     * 按钮图片控件
     */
    var imageView: ImageView? = null
    /**
     * 按钮描述控件
     */
    var textView: TextView? = null

    //============================================================
    //  文字相关
    //============================================================
    /**
     * 按钮描述
     */
    private var actionText: String = ""
    /**
     * 按钮描述文字大小
     */
    private var actionTextSize: Float = 0f
    /**
     * 按钮描述样式
     */
    private var textPressType: Int = 0
    /**
     * 按钮描述默认文字颜色
     */
    @ColorRes
    private var nTextColorId: Int = 0
    /**
     * 按钮描述点击时文字颜色
     */
    @ColorRes
    private var pTextColorId: Int = 0

    /**
     * 设置描述内容
     */
    fun setText(text: String): Action {
        this.textPressType = 0
        this.actionText = text
        return this
    }

    /**
     * 设置描述内容和默认颜色
     */
    fun setText(text: String, @ColorRes nColorId: Int): Action {
        this.textPressType = 1
        this.actionText = text
        this.nTextColorId = nColorId
        return this
    }

    /**
     * 设置描述内容和点击效果的颜色（默认颜色，点击时颜色）
     */
    fun setText(text: String, @ColorRes nColorId: Int, @ColorRes pColorId: Int): Action {
        this.textPressType = 2
        this.actionText = text
        this.nTextColorId = nColorId
        this.pTextColorId = pColorId
        return this
    }

    /**
     * 设置描述字体大小
     */
    fun setTextSize(resources: Resources, @DimenRes resId: Int): Action {
        try {
            this.actionTextSize = HandyTitlebarUtils.spTopx(resources.getDimension(resId))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun getActionText(): String {
        return this.actionText
    }

    fun getActionTextSize(): Float {
        return this.actionTextSize
    }

    fun getTextPressType(): Int {
        return this.textPressType
    }

    fun getNTextColorId(): Int {
        return this.nTextColorId
    }

    fun getPTextColorId(): Int {
        return this.pTextColorId
    }

    //============================================================
    //  图片相关
    //============================================================
    /**
     * 按钮图片
     */
    @DrawableRes
    private var actionImageSrc: Int = 0
    /**
     * 按钮图片大小
     */
    private var actionImageSize: Float = 0f
    /**
     * 按钮图片样式
     */
    private var imagePressType: Int = 0
    /**
     * 按钮图片默认图片
     */
    @DrawableRes
    private var nImageResId: Int = 0
    /**
     * 按钮图片点击时图片
     */
    @DrawableRes
    private var pImageResId: Int = 0
    /**
     * 按钮图片默认图片颜色
     */
    @ColorRes
    private var nImageColorId: Int = 0
    /**
     * 按钮图片点击时图片颜色
     */
    @ColorRes
    private var pImageColorId: Int = 0

    /**
     * 设置图片
     */
    fun setImageSrc(@DrawableRes imageResId: Int): Action {
        this.imagePressType = 0
        this.actionImageSrc = imageResId
        return this
    }

    /**
     * 设置图片和默认颜色
     */
    fun setImageSrc(@DrawableRes nImageResId: Int, @DrawableRes pImageResId: Int): Action {
        this.imagePressType = 1
        this.actionImageSrc = nImageResId
        this.nImageResId = nImageResId
        this.pImageResId = pImageResId
        return this
    }

    /**
     * 设置图片和点击效果的颜色（默认颜色，点击时颜色）
     */
    fun setImageSrc(@DrawableRes imageResId: Int, @ColorRes nColorId: Int, @ColorRes pColorId: Int): Action {
        this.imagePressType = 2
        this.actionImageSrc = imageResId
        this.nImageColorId = nColorId
        this.pImageColorId = pColorId
        return this
    }

    /**
     * 设置图片大小
     */
    fun setImageSize(resources: Resources, @DimenRes resId: Int): Action {
        try {
            this.actionImageSize = HandyTitlebarUtils.dpTopx(resources.getDimension(resId))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun getActionImageSrc(): Int {
        return this.actionImageSrc
    }

    fun getActionImageSize(): Float {
        return this.actionImageSize
    }

    fun getImagePressType(): Int {
        return this.imagePressType
    }

    fun getNImageResId(): Int {
        return this.nImageResId
    }

    fun getPImageResId(): Int {
        return this.pImageResId
    }

    fun getNImageColorId(): Int {
        return this.nImageColorId
    }

    fun getPImageColorId(): Int {
        return this.pImageColorId
    }

    //============================================================
    //  其他方法
    //============================================================
    /**
     * 设置按钮内部文字和图片的距离
     */
    fun setInsideSpacing(resources: Resources, @DimenRes resId: Int): Action {
        try {
            this.insideSpacing = HandyTitlebarUtils.dpTopx(resources.getDimension(resId))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun getInsideSpacing(): Float {
        return insideSpacing
    }

    /**
     * 统一配置按钮图片和文字的样式
     */
    fun syncTextImage(@ColorRes nColorId: Int, @ColorRes pColorId: Int): Action {
        if (this.actionText.isNotEmpty()) {
            this.textPressType = 2
            this.nTextColorId = nColorId
            this.pTextColorId = pColorId
        }
        if (this.actionImageSrc != 0) {
            this.imagePressType = 2
            this.nImageColorId = nColorId
            this.pImageColorId = pColorId
        }
        return this
    }

    abstract fun onClick()
}