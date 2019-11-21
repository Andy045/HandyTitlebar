package com.handy.titlebar.app

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.handy.titlebar.entity.Action
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTitlebar1()
        initTitlebar2()
        initTitlebar3()
        initTitlebar4()
        initTitlebar5()
    }

    fun initTitlebar1() {
        titlebar1.addLeftAction(object : Action() {
            init {
                this.setText("返回")
                this.setImageSrc(R.drawable.ic_keyboard_arrow_left_48px)
                this.syncTextImage(R.color.web_white, R.color.google_grey200)
            }

            override fun onClick() {
                finish()
            }
        })
        titlebar1.addRightAction(object : Action() {
            init {
                this.setImageSrc(
                    R.drawable.ic_settings_48px,
                    R.color.web_white,
                    R.color.google_grey200
                )
            }

            override fun onClick() {
            }
        })
    }

    fun initTitlebar2() {
        titlebar2.addLeftAction(object : Action() {
            init {
                this.setText("返回", R.color.web_white, R.color.google_grey200)
                this.setImageSrc(
                    R.drawable.ic_keyboard_arrow_left_48px,
                    R.color.web_white,
                    R.color.google_grey200
                )
            }

            override fun onClick() {
            }
        })
    }

    fun initTitlebar3() {
        titlebar3.addLeftAction(object : Action() {
            init {
                this.setText("返回")
                this.setImageSrc(R.drawable.ic_keyboard_arrow_left_48px)
                this.syncTextImage(R.color.web_white, R.color.google_grey200)
            }

            override fun onClick() {
            }
        })
        titlebar3.addRightAction(object : Action() {
            init {
                this.setText("设置")
                this.setImageSrc(R.drawable.ic_settings_48px)
                this.syncTextImage(R.color.web_white, R.color.google_grey200)
            }

            override fun onClick() {
            }
        })
    }

    fun initTitlebar4() {
        titlebar4.contentLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        titlebar4.addLeftAction(object : Action() {
            init {
                this.setImageSrc(
                    R.drawable.ic_menu_48px0,
                    R.color.web_white,
                    R.color.google_grey200
                )
                this.setImageSize(resources, R.dimen.hd_dp14)
            }

            override fun onClick() {
            }
        })

    }

    fun initTitlebar5() {
        titlebar5.contentLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        titlebar5.addLeftAction(object : Action() {
            init {
                this.setImageSrc(
                    R.drawable.ic_keyboard_arrow_left_48px,
                    R.color.google_grey800,
                    R.color.google_lightbluea700
                )
                this.setImageSize(resources, R.dimen.hd_dp14)
            }

            override fun onClick() {
            }
        })
        titlebar5.addRightAction(object : Action() {
            init {
                this.setImageSrc(
                    R.drawable.ic_search_48px,
                    R.color.google_grey800,
                    R.color.google_lightbluea700
                )
                this.setImageSize(resources, R.dimen.hd_dp14)
            }

            override fun onClick() {
            }
        })
    }
}