package com.upsaclay.message.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.upsaclay.common.ui.theme.GedoiseColor
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.message.R
import com.upsaclay.common.R.font


@Composable
fun GroupButton(modifier: Modifier = Modifier,

) {
    Button(onClick = { /*fonction créer un nouveau groupe */ },
        modifier = Modifier,
        colors = ButtonColors(GedoiseColor.Tertiary,Color.Unspecified,Color.Unspecified,Color.Unspecified),


    ) {
        Text(text = stringResource(id = R.string.group_creation),
            fontFamily = FontFamily(Font(font.roboto_regular))
        )
    }
}

@Preview
@Composable
fun GroupButtonPreview()
{
    GedoiseTheme {
        GroupButton()
    }
}
