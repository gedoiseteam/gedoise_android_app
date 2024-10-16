package com.upsaclay.common.domain.usecase

import com.upsaclay.common.domain.repository.UserRepository

class IsUserExistUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> = userRepository.isUserExist(email)
}