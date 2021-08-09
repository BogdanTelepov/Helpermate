package com.neobis.repository

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    authorizationRepository: AuthorizationRepository,
    widgetsRepository: WidgetsRepository,
    userRepository: UserRepository
) {

    val authRepository = authorizationRepository
    val widgetRepository = widgetsRepository
    val userRep = userRepository
}