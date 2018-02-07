package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxConcatActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "concat"
    }

    override fun doSomething() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe {
                    rx_operators_text.append("concat accept:" + it + "\n")
                }
    }
}