package com.upsaclay.gedoise.ui

import androidx.lifecycle.ViewModel
import com.upsaclay.authentication.domain.usecase.IsUserAuthenticatedUseCase
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.domain.usecase.GetUserUseCase
import com.upsaclay.gedoise.data.BottomNavigationItem
import com.upsaclay.gedoise.data.BottomNavigationItemType
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    val bottomNavigationItem: Map<BottomNavigationItemType, BottomNavigationItem> = mapOf(
        BottomNavigationItemType.HOME to BottomNavigationItem.Home(),
        BottomNavigationItemType.MESSAGE to BottomNavigationItem.Message(),
        BottomNavigationItemType.CALENDAR to BottomNavigationItem.Calendar(),
        BottomNavigationItemType.FORUM to BottomNavigationItem.Forum()
    )
    val isAuthenticated: Flow<Boolean> = isUserAuthenticatedUseCase()
    val user: Flow<User?> = getUserUseCase()
}