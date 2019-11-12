package com.handy.titlebar.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @title: MarqueeTextView
 * @projectName HandyTitlebar
 * @description: 带跑马灯功能的TextView
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-12 15:57
 */
class MarqueeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun isFocused(): Boolean {
        return true
    }
}