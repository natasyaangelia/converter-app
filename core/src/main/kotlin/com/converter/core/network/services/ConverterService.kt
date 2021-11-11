package com.converter.core.network.services

import com.converter.core.model.Latest
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterService {

    @GET("currency/convert")
    fun getConvertCurrency(
        @Query("api_key") access_key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ) : Deferred<Latest.Response>
}
