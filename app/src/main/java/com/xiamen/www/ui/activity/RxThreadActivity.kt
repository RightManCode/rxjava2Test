package com.xiamen.www.ui.activity

import android.util.Log
import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by admin on 2018/2/6.
 */
class RxThreadActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "线程调度"
    }

    override fun doSomething() {
        Observable.create<Int> {
            it.onNext(1)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.e("cww", "After observeOn(mainThread)，Current thread is " + Thread.currentThread().name)
                }
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.e("cww", "After observeOn(io)，Current thread is " + Thread.currentThread().name)
                }
    }
}