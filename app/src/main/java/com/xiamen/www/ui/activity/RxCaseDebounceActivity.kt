package com.xiamen.www.ui.activity

import com.jakewharton.rxbinding2.view.RxView
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.xiamen.www.bean.FoodListBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/2/6.
 */
class RxCaseDebounceActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "减少频繁的网络请求"
    }

    override fun doSomething() {
        RxView.clicks(rx_operators_btn)
                .debounce(2, TimeUnit.SECONDS)
                .subscribe {
                    clickBtn()
                }
    }

    private fun clickBtn() {
        Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .addQueryParameter("rows", "" + 2)
                .build()
                .getObjectObservable(FoodListBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<FoodListBean> {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        rx_operators_text.append("onError: " + e.message + "\n")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: FoodListBean) {
                        rx_operators_text.append("onNext: " + t.toString() + "\n")
                    }

                })
    }
}