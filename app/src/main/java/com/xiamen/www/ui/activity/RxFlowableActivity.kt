package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxFlowableActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "flowable"
    }

    //初始值为100
    override fun doSomething() {
        Observable.just(1, 2, 3)
                .reduce(100, { t1, t2 ->
                    t1 + t2
                }).subscribe(Consumer {
            rx_operators_text.append("flowable accept:" + it + "\n")
        })
    }
}
