package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxMergeActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "merge"
    }

    override fun doSomething() {
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe {
                    rx_operators_text.append("merge accept:" + it + "\n")
                }
    }
}