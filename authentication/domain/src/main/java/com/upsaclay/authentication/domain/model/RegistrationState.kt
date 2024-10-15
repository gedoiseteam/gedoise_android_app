package com.upsaclay.authentication.domain.model

enum class RegistrationState {
    OK,
    ERROR,
    ERROR_NETWORK,
    LOADING,
    REGISTERED,
    NOT_REGISTERED,
    UNRECOGNIZED_ACCOUNT,
    USER_ALREADY_EXIST,
    USER_NOT_EXIST,
    EMAIL_VERIFIED,
    EMAIL_NOT_VERIFIED,
    INPUTS_EMPTY_ERROR,
    EMAIL_FORMAT_ERROR,
    PASSWORD_LENGTH_ERROR
}