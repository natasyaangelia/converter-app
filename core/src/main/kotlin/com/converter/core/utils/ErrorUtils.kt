package com.converter.core.utils

import com.google.gson.JsonParser
import retrofit2.HttpException

object ErrorUtils {

    fun getMsgFromError(error: Throwable): String {
        if (error is HttpException) {
            try {
                val errorJsonString = error.response()?.errorBody()?.string()
                return JsonParser().parse(errorJsonString)
                    .asJsonObject["error"]
                    .asJsonObject["message"]
                    .asString
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return "Please try again later"
    }
}
