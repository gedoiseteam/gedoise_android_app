package com.upsaclay.message.presentation.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.gson.Gson
import com.upsaclay.common.domain.model.Screen
import com.upsaclay.message.presentation.components.UserItem
import com.upsaclay.message.presentation.viewmodel.ConversationViewModel

@Composable
fun CreateGroupConversationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    conversationViewModel: ConversationViewModel
) {
    val users by conversationViewModel.users.collectAsState(emptyList())
    val gson = Gson()

    LazyColumn(modifier = modifier) {
        items(users) { user ->
            UserItem(
                user = user,
                onClick = {
                    val userJson = gson.toJson(user)
                    navController.navigate(com.upsaclay.common.domain.model.Screen.CHAT.route + "?user=$userJson")
                }
            )
        }
    }
}