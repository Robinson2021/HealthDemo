package com.techeniac.healthsample.data.remote

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Robinson on 18 Nov 2021
 */
interface ApiHelper {

    @POST("apiTest")
    fun step(@Body req:String): Observable<Any>
}