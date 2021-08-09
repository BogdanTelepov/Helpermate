package com.neobis.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neobis.R
import java.lang.reflect.Type

class ConfigManager(context: Context) {
    private val config: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    companion object {
        const val DIASTOLIC = "diastolic"
        const val SYSTOLIC = "systolic"
        const val END_TIME = "endTime"
        const val START_TIME = "startTime"
        const val SUGAR_VALUE = "sugarValue"
        const val WIDGETS_IDS = "widgetsIds"

    }

    fun saveDiastolic(diastolicValue: Int?) {
        val editor = config.edit()
        if (diastolicValue != null) {
            editor.putInt(DIASTOLIC, diastolicValue)
        }
        editor.apply()
    }

    fun getDiastolic(): Int {
        return config.getInt(DIASTOLIC, 0)
    }

    fun saveSystolic(systolicValue: Int?) {
        val editor = config.edit()
        if (systolicValue != null) {
            editor.putInt(SYSTOLIC, systolicValue)
        }
        editor.apply()
    }

    fun getSystolic(): Int {
        return config.getInt(SYSTOLIC, 0)
    }

    fun saveEndTime(endTimeValue: String?) {
        val editor = config.edit()
        if (endTimeValue != null) {
            editor.putString(END_TIME, endTimeValue)
        }
        editor.apply()
    }

    fun getEndTime(): String? {
        return config.getString(END_TIME, "")
    }

    fun saveStartTime(startTimeValue: String?) {
        val editor = config.edit()
        if (startTimeValue != null) {
            editor.putString(START_TIME, startTimeValue)
        }
        editor.apply()
    }

    fun getStartTime(): String? {
        return config.getString(START_TIME, "")
    }


    // https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences

    fun saveWidgetsIds(widgetIds: List<Int>?) {
        val editor = config.edit()
        val gson = Gson()
        val json: String = gson.toJson(widgetIds)
        editor.putString(WIDGETS_IDS, json)
        editor.apply()

    }

    fun getWidgetIds(): List<Int>? {
        val gson = Gson()
        val json: String? = config.getString(WIDGETS_IDS, "")
        val type: Type = object : TypeToken<List<Int>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveSugarValue(sugarValue: String?) {
        val editor = config.edit()
        if (sugarValue != null) {
            editor.putString(SUGAR_VALUE, sugarValue)
        }
        editor.apply()
    }

    fun getSugarValue(): String? {
        return config.getString(SUGAR_VALUE, "")
    }

    fun clearData() {
        config.edit().clear().apply()
    }


}