package com.xiamen.www.ui.activity

import com.google.gson.Gson
import com.xiamen.www.bean.MobileAddressBean

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by admin on 2018/2/5.
 */

class RxNetSingleActivity1 : RxOperatorBaseActivity() {
    override fun getSubTitle(): CharSequence? {
        return "单一网络请求"
    }

    override fun doSomething() {
        Observable.create(ObservableOnSubscribe<Response> { emitter ->
            val builder = Request.Builder()
                    .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                    .get()
            val request = builder.build()
            val response = OkHttpClient().newCall(request).execute()
            emitter.onNext(response)
        }).map(Function<Response, MobileAddressBean> { response ->
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return@Function Gson().fromJson(body.string(), MobileAddressBean::class.java)
                }
            }
            null
        }).doOnNext {

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MobileAddressBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(mobileAddressBean: MobileAddressBean) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}
