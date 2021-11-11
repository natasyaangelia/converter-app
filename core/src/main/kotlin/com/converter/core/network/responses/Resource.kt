package com.converter.core.network.responses

/**
 * This is used for getting states of network call
 */

data class Resource <out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        EMPTY,
        LOADING
    }

    data class Resource<out T>(
        val status: Status,
        val data: T?,
        val message: String?,
        val throwable: Throwable?
    ) {
        companion object {
            fun <T> success(data: T?): Resource<T> {
                return Resource(Status.SUCCESS, data, null, null)
            }

            fun <T> error(msg: String?, data: T? = null, err: Throwable? = null): Resource<T> {
                return Resource(Status.ERROR, data, msg, err)
            }

            fun <T> loading(data: T? = null): Resource<T> {
                return Resource(Status.LOADING, data, null, null)
            }

            fun <T> empty(): Resource<T> = Resource(Status.EMPTY, null, null, null)
        }
    }
}
