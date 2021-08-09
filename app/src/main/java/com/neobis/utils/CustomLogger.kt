package com.neobis.utils

interface CustomLogger {
    val TAG: String

    fun showLog(message: String)
}