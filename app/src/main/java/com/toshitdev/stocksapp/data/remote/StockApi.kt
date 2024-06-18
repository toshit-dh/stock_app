package com.toshitdev.stocksapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getList(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object{
        const val API_KEY = "8XNU2X58Y0RRPCRL"
        const val BASE_URL = "https://alphavantage.co/"
    }
}