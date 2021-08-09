package com.neobis.utils

import androidx.lifecycle.LiveData

interface BottomSheetActivityContractSugarWidget {

    fun onPassDataFromSugarWidget(data: LiveData<String>?)
}