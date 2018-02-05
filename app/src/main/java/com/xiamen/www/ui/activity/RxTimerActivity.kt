package com.xiamen.www.ui.activity

import com.xiamen.www.utils.TimeUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/4.
 */
class RxTimerActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "timer"
    }

    override fun doSomething() {
        rx_operators_text.append("timer start:" + TimeUtil.getNowStrTime() + "\n")
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("timer end:" + TimeUtil.getNowStrTime() + "\n")
                }
    }
}