package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxMapActivity : RxOperatorBaseActivity() {

    override fun getSubTitle(): CharSequence? {
        return "map"
    }

    override fun doSomething() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }.map {
            "this is result:" + it
        }.subscribe {
            rx_operators_text.append("accept:" + it + "\n")
        }
    }
}