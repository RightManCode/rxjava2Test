package com.xiamen.www.ui.activity

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxReduceActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "reduce"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3, 4, 5)
                .reduce({ t1, t2 ->
                    t1 + t2
                }).subscribe { rx_operators_text.append("reduce accept:" + it + "\n") }
    }
}