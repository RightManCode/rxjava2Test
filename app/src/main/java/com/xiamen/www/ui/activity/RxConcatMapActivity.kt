package com.xiamen.www.ui.activity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * 作用和flatMap差不多，唯一的区别是它保证事件的顺序
 * Created by admin on 2018/2/4.
 */
class RxConcatMapActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "contactMap"
    }

    override fun doSomething() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }.concatMap {
            val list = mutableListOf(0 to "i am value" + it, 1 to "i am value" + it, 2 to "i am value" + it)
            val delayTime = (1 + Math.random() * 10).toInt()
            Observable.fromIterable(list).delay(delayTime.toLong(), TimeUnit.MILLISECONDS)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("contactMap accept:" + it.second + "\n")
                }
    }
}