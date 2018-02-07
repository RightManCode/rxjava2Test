package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/4.
 */
class RxFlatMapActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "flatMap"
    }

    override fun doSomething() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }.flatMap({
            val list = mutableListOf(0 to "iam value" + it, 1 to "iam value" + it, 2 to "iam value" + it)
//            val list = (0..2).map { "i am value" + it}
            val delayTime = (1 + Math.random() * 10).toInt()
            Observable.fromIterable(list).delay(delayTime.toLong(), TimeUnit.MILLISECONDS)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("flapMap accept:" + it.second + "\n")
                }
    }
}
