package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxFilterActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "filter"
    }

    override fun doSomething() {
        Observable.just(40, 16, 18, 20, 24, -21, 30, 25, 10, 16)
                .filter {
                    it > 24
                }.subscribe {
            rx_operators_text.append("filter:" + it + "\n")
        }
    }
}