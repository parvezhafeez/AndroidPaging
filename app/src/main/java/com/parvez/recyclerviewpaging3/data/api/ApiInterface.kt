package com.parvez.recyclerviewpaging3.data.api

import com.parvez.recyclerviewpaging3.data.api.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    fun callNewsApi(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Call<NewsResponse>

}