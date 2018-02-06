package com.xiamen.www.ui.activity

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/6.
 */
class RxCaseIntervalActivity : RxOperatorBaseActivity() {
    private var mDisposable: Disposable? = null
    override fun getSubTitle(): CharSequence? {
        return "间隔任务实现心跳"
    }

    override fun doSomething() {
        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { rx_operators_text.append("\ndoOnNext:" + it + "\n") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rx_operators_text.append("accept: 设置文本" + it + "\n")
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable?.dispose()
    }
}