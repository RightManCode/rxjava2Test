package com.xiamen.www.base

import com.xiamen.www.R
import com.xiamen.www.base.ToolbarBaseActivity
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
abstract class RxOperatorBaseActivity : ToolbarBaseActivity() {

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_rx_operator_base
    }

    override fun initDoSomething() {
        rx_operators_btn?.setOnClickListener {
            rx_operators_text.append("\n")
            doSomething()
        }
    }

    abstract fun doSomething()

}