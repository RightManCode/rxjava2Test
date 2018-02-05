package com.xiamen.www.ui.activity

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxLastActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "last"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3)
                .last(4)
                .subscribe(Consumer {
                    rx_operators_text.append("last accept:" + it + "\n")
                })


    }
}