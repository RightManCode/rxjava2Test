package com.xiamen.www.ui.activity

import com.google.gson.Gson
import com.xiamen.www.base.RxOperatorBaseActivity
import com.xiamen.www.bean.MobileAddressBean
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_operator_base.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Created by admin on 2018/2/5.
 */
class RxNetSingleActivity : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "单一网络请求"
    }

    override fun doSomething() {
        Observable.create<Response> {
            val request = Request.Builder().url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                    .get().build()
            val response = OkHttpClient().newCall(request).execute()
            it.onNext(response)
        }.map(Function<Response, MobileAddressBean> { response ->
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return@Function Gson().fromJson(body.string(), MobileAddressBean::class.java)
                }
            }
            null
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext {
            rx_operators_text.append("doOnNext:" + it.toString() + "保存成功！" + "\n")
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<MobileAddressBean> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: MobileAddressBean) {
                        rx_operators_text.append("\nonNext:" + t.toString() + "获取数据成功！\n")
                    }

                    override fun onError(e: Throwable) {
                        rx_operators_text.append("\nonError:" + e.message + "获取数据出错！\n")
                    }
                })
    }
}