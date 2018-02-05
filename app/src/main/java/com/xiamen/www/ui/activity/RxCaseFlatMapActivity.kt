package com.xiamen.www.ui.activity

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.xiamen.www.bean.FoodDetailBean
import com.xiamen.www.bean.FoodListBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxCaseFlatMapActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "多个网络请求依次依赖"
    }

    override fun doSomething() {
        Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .build().getObjectObservable(FoodListBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    rx_operators_text.append("doOnNext: " + it.toString() + "\n")
                }
                .observeOn(Schedulers.io())
                .flatMap(Function<FoodListBean, ObservableSource<FoodDetailBean>> {
                    if (it.tngou.size > 0) {
                        Rx2AndroidNetworking.post("http://www.tngou.net/api/food/show")
                                .addBodyParameter("id", "" + it.tngou[0].id)
                    }
                    null
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<FoodDetailBean> {
                    override fun onError(e: Throwable) {
                        rx_operators_text.append("onError: " + e.message + "\n")
                    }

                    override fun onNext(t: FoodDetailBean) {
                        rx_operators_text.append("onNext: " + t.toString() + "\n")
                    }

                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }


                })

    }
}