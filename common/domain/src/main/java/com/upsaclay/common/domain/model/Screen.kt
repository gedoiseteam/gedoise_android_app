package com.upsaclay.common.domain.model

enum class Screen(val route: String) {
    AUTHENTICATION("authentication_screen"),
    FIRST_REGISTRATION_SCREEN("first_registration_screen"),
    SECOND_REGISTRATION_SCREEN("second_registration_screen"),
    THIRD_REGISTRATION_SCREEN("third_registration_screen"),
    NEWS("news_screen"),
    READ_ANNOUNCEMENT("read_announcement_screen"),
    EDIT_ANNOUNCEMENT("edit_announcement_screen"),
    CREATE_ANNOUNCEMENT("create_announcement_screen"),
    CHAT("chat_screen"),
    CONVERSATIONS("conversations_screen"),
    CREATE_CONVERSATION("create_conversations_screen"),
    CREATE_GROUP_CONVERSATION("create_group_conversations_screen"),
    CALENDAR("calendar_screen"),
    FORUM("forum_screen"),
    PROFILE("profile_screen"),
    ACCOUNT("account_screen"),
    SUPPORT("support_screen")
}