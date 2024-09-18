package com.upsaclay.message.ui

import FloatinButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.message.ui.components.GroupButton
import com.upsaclay.message.ui.components.HighBar



@Composable
fun MessageScreen()
{
    Column{
        HighBar()
        FloatinButton()
    }
}


@Preview
@Composable
fun MessageScreenPreview(

)
{
    GedoiseTheme {
        MessageScreen()
    }
}