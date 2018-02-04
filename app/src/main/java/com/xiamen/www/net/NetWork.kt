package com.xiamen.www.net

import com.xiamen.www.net.api.GrankApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by admin on 2018/2/1.
 */

object NetWork {
    private val okHttpClient = OkHttpClient()
    val grankApi: GrankApi by lazy {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://gank.io/api/")
                .build()
        retrofit.create(GrankApi::class.java)
    }
}
