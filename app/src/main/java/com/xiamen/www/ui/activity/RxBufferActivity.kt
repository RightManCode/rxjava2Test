package com.xiamen.www.ui.activity

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxBufferActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "buffer"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe { rx_operators_text.append("buffer accept:" + it + "\n") }
    }
}