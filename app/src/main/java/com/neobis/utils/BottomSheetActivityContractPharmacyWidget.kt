package com.neobis.utils

import androidx.lifecycle.LiveData

interface BottomSheetActivityContractPharmacyWidget {

    fun onPassDataFromPharmacyWidget(data: LiveData<String>?)
}