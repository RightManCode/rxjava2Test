package com.xiamen.www.ui.activity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxJustActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "just"
    }

    override fun doSomething() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("just accept:" + it + "\n")
                }
    }
}