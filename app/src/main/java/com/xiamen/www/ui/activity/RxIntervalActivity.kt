package com.xiamen.www.ui.activity

import com.xiamen.www.utils.TimeUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/4.
 */
class RxIntervalActivity : RxOperatorBaseActivity() {
    private var mDisposable: Disposable? = null

    override fun getSubTitle(): CharSequence? {
        return "interval"
    }

    override fun doSomething() {
        mDisposable = Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("interval :" + it + " at " + TimeUtil.getNowStrTime())
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable!!.dispose()
    }
}