package com.xiamen.www.ui.activity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/5.
 */
class RxDebounceActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "debounce"
    }

    override fun doSomething() {
        Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(400)

            it.onNext(2)
            Thread.sleep(505)

            it.onNext(3)
            Thread.sleep(100)

            it.onNext(4)
            Thread.sleep(605)

            it.onNext(5)
            Thread.sleep(510)

            it.onComplete()
        }.debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("debounce accept:" + it + "\n")
                }
    }
}