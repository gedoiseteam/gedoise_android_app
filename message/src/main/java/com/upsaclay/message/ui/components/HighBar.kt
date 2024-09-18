package com.upsaclay.message.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsaclay.message.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighBar() {

    Row {

        TopAppBar(title = { Text(text = stringResource(id = R.string.message_title))})
        Spacer(modifier = Modifier.padding(3.dp))
        GroupButton()
    }
}

@Preview
@Composable
fun HighBarPreview()
{
    HighBar()
}