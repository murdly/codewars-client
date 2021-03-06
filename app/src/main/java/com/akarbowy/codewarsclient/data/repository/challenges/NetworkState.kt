package com.akarbowy.codewarsclient.data.repository.challenges

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val msg: String? = null) {

    companion object {
        val LOADING = NetworkState(Status.RUNNING)
        val LOADED = NetworkState(Status.SUCCESS)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg = msg)
    }
}