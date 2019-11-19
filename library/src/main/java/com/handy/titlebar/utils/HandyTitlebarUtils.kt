package com.handy.titlebar.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * 相关工具类
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/3/29 10:57 AM
 * @modified By liujie
 */
class HandyTitlebarUtils private constructor() {
    companion object {
        /**
         * dp转px
         */
        fun dpTopx(dpValue: Float): Float {
            val scale = Resources.getSystem().displayMetrics.density
            return dpValue * scale + 0.5f
        }

        /**
         * sp转px
         */
        fun spTopx(spValue: Float): Float {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return spValue * fontScale + 0.5f
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Context): Int {
            val service = context.getSystemService(Context.WINDOW_SERVICE)
            if (service == null) {
                return context.resources.displayMetrics.widthPixels
            } else {
                val point = Point()
                val wm = service as WindowManager
                wm.defaultDisplay.getRealSize(point)
                return point.x
            }
        }

        /**
         * 通过代码改变图片颜色
         *
         * @param context    上下文
         * @param idDrawable 原图片
         * @param tintColor  目标颜色 (16进制)
         * @return 改色后的Drawable
         */
        fun tintDrawable(context: Context, @DrawableRes idDrawable: Int, @ColorRes tintColor: Int): Drawable? {
            if (idDrawable == 0) {
                return null
            }
            val drawable = ContextCompat.getDrawable(context, idDrawable)?.mutate() ?: return null
            val inBitmap = drawable2Bitmap(drawable)
            val outBitmap =
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    inBitmap.config
                )
            val canvas = Canvas(outBitmap)
            val paint = Paint()
            paint.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(context, tintColor),
                    PorterDuff.Mode.SRC_IN
                )
            canvas.drawBitmap(inBitmap, 0f, 0f, paint)
            return BitmapDrawable(null, outBitmap)
        }

        private fun drawable2Bitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                if (drawable.bitmap != null) {
                    return drawable.bitmap
                }
            }
            val bitmap: Bitmap
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(
                    1,
                    1,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
            } else {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * 通过代码设置Selector效果
         *
         * @param context   上下文
         * @param idNormal  默认样式（或者图片和颜色的资源ID）
         * @param idPressed 点击样式（或者图片和颜色的资源ID）
         * @param idFocused 焦点样式（或者图片和颜色的资源ID）
         * @return Selector样式
         */
        fun getStateDrawable(context: Context, @DrawableRes idNormal: Int, @DrawableRes idPressed: Int, @DrawableRes idFocused: Int): StateListDrawable {
            val normal = if (idNormal == 0) null else ContextCompat.getDrawable(context, idNormal)
            val pressed =
                if (idPressed == 0) null else ContextCompat.getDrawable(context, idPressed)
            val focus = if (idFocused == 0) null else ContextCompat.getDrawable(context, idFocused)
            return getStateDrawable(normal, pressed, focus)
        }

        /**
         * 通过代码设置Selector效果
         *
         * @param context   上下文
         * @param idNormal  默认样式（或者图片和颜色的资源ID）
         * @param idPressed 点击样式（或者图片和颜色的资源ID）
         * @param idFocused 焦点样式（或者图片和颜色的资源ID）
         * @return Selector样式
         */
        fun getStateDrawable(
            normal: Drawable?,
            pressed: Drawable?,
            focus: Drawable?
        ): StateListDrawable {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(
                intArrayOf(
                    android.R.attr.state_enabled,
                    android.R.attr.state_focused
                ), focus
            )
            stateListDrawable.addState(
                intArrayOf(
                    android.R.attr.state_pressed,
                    android.R.attr.state_enabled
                ), pressed
            )
            stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), focus)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressed)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), normal)
            stateListDrawable.addState(intArrayOf(), normal)
            return stateListDrawable
        }

        /**
         * 通过代码设置Selector效果
         *
         * @param context   上下文
         * @param idNormal  默认样式（或者图片和颜色的资源ID）
         * @param idPressed 点击样式（或者图片和颜色的资源ID）
         * @param idFocused 焦点样式（或者图片和颜色的资源ID）
         * @return Selector样式
         */
        fun getStateColor(context: Context, @ColorRes idNormal: Int, @ColorRes idPressed: Int, @ColorRes idFocused: Int): ColorStateList {
            val normal = ContextCompat.getColor(context, idNormal)
            val pressed = ContextCompat.getColor(context, idPressed)
            val focus = ContextCompat.getColor(context, idFocused)
            return getStateColor(normal, pressed, focus)
        }

        /**
         * 通过代码设置Selector效果
         *
         * @return Selector样式
         */
        fun getStateColor(@ColorInt normal: Int, @ColorInt pressed: Int, @ColorInt focus: Int): ColorStateList {
            val colors = intArrayOf(focus, pressed, focus, pressed, normal, normal)
            val states = arrayOfNulls<IntArray>(6)
            states[0] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
            states[1] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
            states[2] = intArrayOf(android.R.attr.state_focused)
            states[3] = intArrayOf(android.R.attr.state_pressed)
            states[4] = intArrayOf(android.R.attr.state_enabled)
            states[5] = intArrayOf()
            return ColorStateList(states, colors)
        }
    }
}
