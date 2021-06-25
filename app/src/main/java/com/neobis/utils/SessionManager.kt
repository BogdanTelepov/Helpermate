package com.neobis.utils

import android.content.Context
import android.content.SharedPreferences
import com.neobis.R

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    companion object {
        const val IS_FINISHED = "isFinished"
    }

    fun onBoardingFinished() {
        val editor = prefs.edit()
        editor.putBoolean(IS_FINISHED, true)
        editor.apply()
    }

    fun checkOnBoarding(): Boolean {
        return prefs.getBoolean(IS_FINISHED, false)
    }
}