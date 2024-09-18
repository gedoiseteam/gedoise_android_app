package com.upsaclay.message.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.message.ui.components.HighBar



@Composable
fun MessageScreen()
{
    Column{
        HighBar()
        LazyColumn {
            item {
               //trouver comment récupérer la fonction pivé RecentAnnouncementSection()
            }
        }
    FloatingActionButton(onClick = { /*TODO*/ })
    // trouver comment placer le floatingActionButton à la bonne place
        {

        }
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