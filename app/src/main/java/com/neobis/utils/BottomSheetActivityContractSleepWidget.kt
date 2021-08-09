package com.neobis.utils


import androidx.lifecycle.LiveData

interface BottomSheetActivityContractSleepWidget {


    fun onPassDataRequested(data: LiveData<String>?)
}