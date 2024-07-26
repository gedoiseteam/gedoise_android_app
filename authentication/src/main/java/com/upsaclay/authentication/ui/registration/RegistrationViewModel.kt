package com.upsaclay.authentication.ui.registration

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsaclay.authentication.data.model.RegistrationState
import com.upsaclay.authentication.domain.IsAccountExistUseCase
import com.upsaclay.authentication.domain.RegistrationUseCase
import com.upsaclay.core.data.model.User
import com.upsaclay.core.domain.GetDrawableUriUseCase
import com.upsaclay.core.domain.UploadImageOracleBucketUseCase
import com.upsaclay.core.utils.errorLog
import com.upsaclay.core.utils.formatProfilePictureFileName
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal const val MAX_STEP = 3

class RegistrationViewModel(
    private val isAccountExistUseCase: IsAccountExistUseCase,
    getDrawableUriUseCase: GetDrawableUriUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val uploadImageOracleBucketUseCase: UploadImageOracleBucketUseCase,
) : ViewModel() {
    private val _registrationState = MutableStateFlow(RegistrationState.NOT_REGISTERED)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    val schoolLevels = persistentListOf("GED 1", "GED 2", "GED 3", "GED 4")
    var currentSchoolLevel by mutableStateOf(schoolLevels[0])
        private set

    val defaultPictureUri =
        getDrawableUriUseCase(com.upsaclay.core.R.drawable.default_profile_picture)
    var profilePictureUri: Uri by mutableStateOf(defaultPictureUri)
        private set
    var fullName by mutableStateOf("")
        private set

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun updateSchoolLevel(value: String) {
        currentSchoolLevel = value
    }

    fun updateProfilePictureUri(uri: Uri?) {
        uri?.let { profilePictureUri = it }
    }

    fun resetEmail() {
        email = ""
    }

    fun resetPassword() {
        password = ""
    }

    fun resetRegistrationState() {
        _registrationState.value = RegistrationState.NOT_REGISTERED
    }

    fun resetProfilePictureUri() {
        profilePictureUri = defaultPictureUri
    }

    fun verifyAccount(email: String, password: String) {
        _registrationState.value = RegistrationState.LOADING

        if (email.isBlank() || password.isBlank()) {
            _registrationState.value = RegistrationState.ERROR_INPUT
            return
        }

        viewModelScope.launch {
            _registrationState.value = if (isAccountExistUseCase(email, password)) {
                val splitName = email.split(".")
                val splitMail = splitName[1].split("@")

                val firstname = splitName[0].replaceFirstChar { it.uppercase() }
                val lastname = splitMail[0].replaceFirstChar { it.uppercase() }

                this@RegistrationViewModel.fullName = String.format("%s %s", firstname, lastname)
                RegistrationState.RECOGNIZED_ACCOUNT
            } else {
                RegistrationState.UNRECOGNIZED_ACCOUNT
            }
        }
    }

    fun register() {
        _registrationState.value = RegistrationState.LOADING

        val user = User(
            id = -1,
            firstName = fullName.split(" ")[0],
            lastName = fullName.split(" ")[1],
            email = email,
            schoolLevel = currentSchoolLevel,
            isMember = false
        )

        viewModelScope.launch {
            val result = registrationUseCase(user)
            if (result.isSuccess) {
                val userId = result.getOrNull()
                userId?.let {
                    val fileName = formatProfilePictureFileName(it.toString())
                    uploadImageOracleBucketUseCase.uploadFromUri(fileName, profilePictureUri)
                    _registrationState.value = RegistrationState.REGISTERED
                } ?: {
                    _registrationState.value = RegistrationState.ERROR_REGISTRATION
                    errorLog("Error to upload profile picture : User id is null")
                }
            } else {
                _registrationState.value = RegistrationState.ERROR_REGISTRATION
                errorLog(result.exceptionOrNull().toString())
            }
        }
    }
}