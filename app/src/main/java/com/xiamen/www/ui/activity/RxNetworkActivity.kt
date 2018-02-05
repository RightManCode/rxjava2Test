package com.xiamen.www.ui.activity

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.xiamen.www.bean.MobileAddressBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/5.
 */
class RxNetworkActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "使用Rx2-NetWorking"
    }

    override fun doSomething() {
        Rx2AndroidNetworking.get("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                .build()
                .getObjectObservable(MobileAddressBean::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    rx_operators_text.append("rx2AndroidNetworking:" + it.toString() + "\n")
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MobileAddressBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: MobileAddressBean) {
                        rx_operators_text.append("\nonNext:" + t.toString() + "\n")
                    }

                    override fun onError(e: Throwable) {
                        rx_operators_text.append("\nonError:" + e.message + "\n")
                    }

                    override fun onComplete() {
                    }

                })

    }
}