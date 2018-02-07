package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxScanActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "scan"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3)
                .scan({ t1, t2 ->
                    t1 + t2
                }).subscribe {
            rx_operators_text.append("scan accept:" + it + "\n")
        }
    }
}