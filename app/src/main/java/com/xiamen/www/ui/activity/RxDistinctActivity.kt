package com.xiamen.www.ui.activity

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxDistinctActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "distinct"
    }

    override fun doSomething() {
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct().subscribe { rx_operators_text.append("distinct accept:" + it + "\n") }
    }
}