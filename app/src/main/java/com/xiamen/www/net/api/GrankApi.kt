package com.xiamen.www.net.api

import com.xiamen.www.bean.CategoryResultBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by admin on 2018/2/1.
 */

interface GrankApi {
    @GET("data/{category}/{count}/{page}")
    fun getCategoryData(@Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Observable<CategoryResultBean>

}
