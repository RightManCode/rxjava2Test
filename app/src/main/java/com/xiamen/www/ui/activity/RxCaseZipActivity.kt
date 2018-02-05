package com.xiamen.www.ui.activity

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.xiamen.www.bean.CategoryResultBean
import com.xiamen.www.bean.MobileAddressBean
import com.xiamen.www.net.NetWork
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxCaseZipActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "zip 操作符使用场景"
    }

    override fun doSomething() {
        val observable1 = Rx2AndroidNetworking.get("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                .build().getObjectObservable(MobileAddressBean::class.java)

        val observable2 = NetWork.grankApi.getCategoryData("Android", 1, 1)

        Observable.zip(observable1, observable2, BiFunction<MobileAddressBean, CategoryResultBean, String> { t1, t2 ->
            return@BiFunction "合并后的数据为: 手机归属地" + t1.toString() + " 人名：" + t2.results[0].who
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onNext(t: String) {
                        rx_operators_text.append("onNext :" + t + "\n")
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        rx_operators_text.append("onError :" + e.message + "\n")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                })

    }
}