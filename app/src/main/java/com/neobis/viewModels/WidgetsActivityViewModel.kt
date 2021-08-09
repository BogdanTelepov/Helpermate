package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.neobis.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WidgetsActivityViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


}