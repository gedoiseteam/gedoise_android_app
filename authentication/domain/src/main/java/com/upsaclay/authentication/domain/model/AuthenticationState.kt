package com.upsaclay.authentication.domain.model

enum class AuthenticationState {
    UNAUTHENTICATED,
    AUTHENTICATED,
    LOADING,
    EMAIL_NOT_VERIFIED,
    AUTHENTICATION_ERROR,
    INPUTS_EMPTY_ERROR,
    UNKNOWN_ERROR,
    NETWORK_ERROR,
    AUTHENTICATED_USER_NOT_FOUND
}