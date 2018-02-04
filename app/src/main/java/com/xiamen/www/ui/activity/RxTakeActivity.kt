package com.xiamen.www.ui.activity

import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxTakeActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "take"
    }

    override fun doSomething() {
        Flowable.fromArray(10, 25, 3, -4, 4)
                .take(2)
                .subscribe {
                    rx_operators_text.append("take:" + it + "\n")
                }
    }
}