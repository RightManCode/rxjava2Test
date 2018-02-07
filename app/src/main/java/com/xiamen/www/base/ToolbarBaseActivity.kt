package com.xiamen.www.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import com.xiamen.www.utils.ScreenUtil
import com.xiamen.www.utils.StatusBarUtl
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by admin on 2018/2/4.
 */
abstract class ToolbarBaseActivity : AppCompatActivity() {

    abstract fun getContentViewLayoutID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtl.fullScreen(this)
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID())
            initToolbar()
            initDoSomething()
        }
    }




    private fun initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            toolbar.layoutParams.height = ScreenUtil.dip2px(this, 70F)


            val lp = LinearLayout.LayoutParams(title_text.layoutParams)
            lp.setMargins(0, ScreenUtil.getStatusBarHeight(this)/3, 0, 0)
            title_text.layoutParams = lp

            val lp2 = LinearLayout.LayoutParams(iv_back.layoutParams)
            lp2.setMargins(0, ScreenUtil.getStatusBarHeight(this) / 3, 0, 0)
            iv_back.layoutParams = lp2
        }
        title_text.text = getSubTitle()
        if (isShowBack()) {
            back()
        }
    }

    private fun back() {
        ll_back.visibility = View.VISIBLE
        ll_back.setOnClickListener { finish() }
    }

    open fun isShowBack(): Boolean {
        return true
    }

    abstract fun getSubTitle(): CharSequence?
    abstract fun initDoSomething()

}