package com.xiamen.www.ui.activity

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxSkipActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "skip"
    }

    override fun doSomething() {
        Observable.just(10, 4, -1, 45, 25)
                .skip(2)
                .subscribe {
                    rx_operators_text.append("skip:" + it + "\n")
                }
    }
}