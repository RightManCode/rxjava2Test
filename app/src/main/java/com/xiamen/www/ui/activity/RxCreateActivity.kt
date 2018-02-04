package com.xiamen.www.ui.activity

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 */
class RxCreateActivity : RxOperatorBaseActivity() {
    private val TAG: String = "RxCreateActivity"
    override fun getSubTitle(): CharSequence? {
        return "create"
    }

    override fun doSomething() {
        Observable.create(ObservableOnSubscribe<Int> {
            //发射器
            rx_operators_text.append("Observable it emit 1" + "\n")
            Log.e(TAG, "Observable it emit 1" + "\n")
            it.onNext(1)

            rx_operators_text.append("Observable it emit 2" + "\n")
            Log.e(TAG, "Observable it emit 2" + "\n")
            it.onNext(2)

            rx_operators_text.append("Observable it emit 3" + "\n")
            Log.e(TAG, "Observable it emit 3" + "\n")
            it.onNext(3)
            it.onComplete()

            rx_operators_text.append("Observable it emit 4" + "\n")
            Log.e(TAG, "Observable it emit 4" + "\n")
            it.onNext(4)
        }).subscribe(object : Observer<Int> {
            //接收器
            private var i: Int = 0
            private var mDisposable: Disposable? = null
            override fun onComplete() {
            }

            override fun onNext(t: Int) {
                i++
                if (i == 2) {
                    mDisposable!!.dispose()
                }
            }

            override fun onSubscribe(d: Disposable) {
                rx_operators_text.append("onSubscribe:" + d.isDisposed + "\n")
                Log.e(TAG, "onSubscribe:" + d.isDisposed + "\n")
                mDisposable = d
            }

            override fun onError(e: Throwable) {

            }

        })
    }
}