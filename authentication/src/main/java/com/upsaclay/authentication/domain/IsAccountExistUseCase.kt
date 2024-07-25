package com.upsaclay.authentication.domain


class IsAccountExistUseCase(
    private val loginUseCase: LoginUseCase
) {
    suspend operator fun invoke(email: String, password: String): Boolean =
        loginUseCase(email, password).isSuccess
}