package com.upsaclay.gedoise.presentation.profile


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.MailTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsaclay.common.domain.usecase.GetCurrentUserFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authenticationRepository: com.upsaclay.authentication.domain.repository.AuthenticationRepository,
    getCurrentUserFlowUseCase: GetCurrentUserFlowUseCase
) : ViewModel() {
    val user: Flow<com.upsaclay.common.domain.model.User?> = getCurrentUserFlowUseCase()

    fun logout() {
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }


}