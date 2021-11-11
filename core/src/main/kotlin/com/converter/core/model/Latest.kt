package com.converter.core.model

object Latest {

    data class Response(
        val base_currency_code: String?,
        val base_currency_name: String?,
        val amount: String?,
        val updated_date: String?,
        val status: String?,
        val rates: Map<String, Rates>?
    ) {
        data class Rates(
            val currency_name: String?,
            val rate: String?,
            val rate_for_amount: Double?
        )
    }
}
