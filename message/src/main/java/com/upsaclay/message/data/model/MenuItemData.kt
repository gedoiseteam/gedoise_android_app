package com.upsaclay.message.data.model

import androidx.compose.runtime.Composable

data class MenuItemData(
    val text: @Composable () -> Unit,
    val icon: @Composable (() -> Unit),
    val onClick: (() -> Unit)? = null
)