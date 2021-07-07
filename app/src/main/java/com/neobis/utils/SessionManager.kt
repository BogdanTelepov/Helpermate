package com.neobis.utils

import android.content.Context
import android.content.SharedPreferences
import com.neobis.R

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    companion object {
        const val IS_FINISHED = "isFinished"
        const val TYPE_OF_PERSON = "type_of_person"
    }

    fun onBoardingFinished() {
        val editor = prefs.edit()
        editor.putBoolean(IS_FINISHED, true)
        editor.apply()
    }

    fun checkOnBoarding(): Boolean {
        return prefs.getBoolean(IS_FINISHED, false)
    }

    fun saveTypeOfPerson(typeOfPerson: Int?) {
        val editor = prefs.edit()
        if (typeOfPerson != null) {
            editor.putInt(TYPE_OF_PERSON, typeOfPerson)
            editor.apply()
        }
    }

    fun getTypeOfPerson(): Int {
        return prefs.getInt(TYPE_OF_PERSON, 0)
    }

    fun deleteTypeOfPerson() {
        prefs.edit().remove(TYPE_OF_PERSON).apply()
    }
}