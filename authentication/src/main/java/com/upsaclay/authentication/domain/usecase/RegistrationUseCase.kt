package com.upsaclay.authentication.domain.usecase

import android.net.Uri
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.domain.repository.UserRepository
import com.upsaclay.common.domain.usecase.UpdateUserProfilePictureUseCase
import com.upsaclay.common.utils.e

class RegistrationUseCase(
    private val userRepository: UserRepository,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase
) {
    suspend operator fun invoke(user: User, profilePictureUri: Uri?): Result<Int> {
        return userRepository.createUser(user)
            .onSuccess { userId ->
                profilePictureUri?.let {
                    updateUserProfilePictureUseCase(userId, profilePictureUri, null)
                }
            }
            .onFailure { exception ->
                e("Error during registration", exception)
            }
    }
}