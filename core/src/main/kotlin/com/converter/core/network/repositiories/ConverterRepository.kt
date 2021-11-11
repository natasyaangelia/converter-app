package com.converter.core.network.repositiories

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.converter.core.model.Latest
import com.converter.core.network.services.ConverterService

class ConverterRepository(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val service: ConverterService
) {

    suspend fun getConvertedRateAsync(access_key: String, from: String, to: String, amount: Double): Latest.Response =
        service.getConvertCurrency(access_key, from, to, amount).await()
}
