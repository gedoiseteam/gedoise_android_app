package com.upsaclay.message.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsaclay.common.ui.theme.GedoiseColor
import com.upsaclay.message.R
import com.upsaclay.common.R.font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighBar() {

    Row {

        TopAppBar(title = { Text(text = stringResource(id = R.string.message_title),
            fontFamily = FontFamily(Font(font.inter_medium))
        )},
            actions = { GroupButton() },
            colors = TopAppBarColors(
                containerColor = GedoiseColor.Background,
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.Unspecified,
                titleContentColor = GedoiseColor.Black,
                actionIconContentColor = Color.Unspecified
            )
        )
        GroupButton()
    }
}

@Preview
@Composable
fun HighBarPreview()
{
    HighBar()
}