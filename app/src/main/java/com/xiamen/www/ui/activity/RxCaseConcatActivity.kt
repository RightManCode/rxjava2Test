package com.xiamen.www.ui.activity

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.xiamen.www.base.RxOperatorBaseActivity
import com.xiamen.www.bean.FoodListBean
import com.xiamen.www.utils.CacheManager
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxCaseConcatActivity : RxOperatorBaseActivity() {
    var isFromNet: Boolean = false
    override fun getSubTitle(): CharSequence? {
        return "先读取缓存再读取网络"
    }

    override fun doSomething() {
        val cache = Observable.create(ObservableOnSubscribe<FoodListBean> {
            val data = CacheManager.getInstance().foodListData
            if (data != null) {
                isFromNet = true
                runOnUiThread {
                    rx_operators_text.append("读取缓存数据" + "\n")
                }
                it.onNext(data)
            } else {
                isFromNet = true
                runOnUiThread {
                    rx_operators_text.append("读取网络数据" + "\n")
                }
                it.onComplete()
            }
        })

        val network = Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .addQueryParameter("rows", 10.toString() + "")
                .build()
                .getObjectObservable(FoodListBean::class.java)

        //两个observable的泛型应该保持一致
        Observable.concat(cache, network)
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
                        if (isFromNet) {
                            rx_operators_text.append("onNext: 网络获取数据，设置缓存\n")
                            CacheManager.getInstance().foodListData = t
                        }

                        rx_operators_text.append("onNext: 读取数据成功，" + t.toString() + "\n")
                    }
                })
    }

}