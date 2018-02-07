package com.xiamen.www.ui.activity

import com.xiamen.www.base.RxOperatorBaseActivity
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.*

/**
 * Created by admin on 2018/2/5.
 */
class RxSingleActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "single"
    }

    override fun doSomething() {
        Single.just(Random().nextInt())
                .subscribe(object : SingleObserver<Int> {
                    override fun onError(e: Throwable) {
                        rx_operators_text.append("single onError:" + e.message + "\n")
                    }

                    override fun onSuccess(t: Int) {
                        rx_operators_text.append("single onSuccess:" + t + "\n")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }
}