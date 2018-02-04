package com.xiamen.www.utils

import io.reactivex.observers.DisposableObserver

/**
 * Created by admin on 2018/1/31.
 */
abstract class BaseDisposableObserver : DisposableObserver<Long>() {


    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }

}