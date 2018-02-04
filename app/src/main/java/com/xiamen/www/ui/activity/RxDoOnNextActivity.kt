package com.xiamen.www.ui.activity

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxDoOnNextActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "doOnNext"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3, 4)
                .doOnNext {
                    rx_operators_text.append("doOnNext: 保存" + it + "成功！" + "\n")
                }.subscribe {
            rx_operators_text.append("doOnNext之后" + it + "\n")
        }
    }
}