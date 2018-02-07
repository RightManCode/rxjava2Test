package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxDeferActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "defer"
    }

    override fun doSomething() {
        val observable = Observable.defer { Observable.just(1, 2, 3) }

        observable.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                rx_operators_text.append("defer onError:" + e.message + "\n")
            }

            override fun onNext(t: Int) {
                rx_operators_text.append("defer onNext:" + t + "\n")
            }

            override fun onComplete() {
                rx_operators_text.append("defer onComplete:" + "\n")
            }
        })
    }
}