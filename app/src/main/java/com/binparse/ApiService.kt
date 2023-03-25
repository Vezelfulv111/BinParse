package com.binparse

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getData(@Url url:String) : Call<Any>

//    @GET
//    fun getScheme(@Url url:String):Call<Scheme>
}