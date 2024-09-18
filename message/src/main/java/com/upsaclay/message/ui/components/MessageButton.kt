package com.upsaclay.message.ui.components

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.message.ui.MessageViewModel

@Composable
fun MessageButton(modifier: Modifier = Modifier

)
{
    Button(onClick = { /*créer un groupe*/ },
    modifier = modifier
    ) {

    }
}

@Preview
@Composable
fun MessageButtonPreview()
{
    GedoiseTheme {
        MessageButton()
    }
}