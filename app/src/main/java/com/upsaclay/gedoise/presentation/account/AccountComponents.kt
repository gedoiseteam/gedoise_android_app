package com.upsaclay.gedoise.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.upsaclay.common.presentation.components.SimpleClickableItem
import com.upsaclay.common.presentation.components.SmallTopBarBack
import com.upsaclay.common.presentation.components.SmallTopBarEdit
import com.upsaclay.common.presentation.theme.GedoiseColor
import com.upsaclay.common.presentation.theme.spacing
import com.upsaclay.gedoise.R
import com.upsaclay.gedoise.data.account.AccountInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountModelBottomSheet(
    onDismissRequest: () -> Unit,
    onNewProfilePictureClick: () -> Unit,
    showDeleteProfilePicture: Boolean = false,
    onDeleteProfilePictureClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        SimpleClickableItem(
            modifier = Modifier.fillMaxWidth(),
            text = { Text(text = stringResource(id = R.string.new_profile_picture)) },
            icon = {
                Icon(
                    painter = painterResource(id = com.upsaclay.common.R.drawable.ic_picture),
                    contentDescription = null
                )
            },
            onClick = onNewProfilePictureClick
        )

        if (showDeleteProfilePicture) {
            SimpleClickableItem(
                modifier = Modifier.fillMaxWidth(),
                text = {
                    Text(
                        text = stringResource(id = R.string.delete_profile_picture),
                        color = GedoiseColor.Red
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = GedoiseColor.Red
                    )
                },
                onClick = onDeleteProfilePictureClick
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    }
}

@Composable
internal fun AccountInfoItem(modifier: Modifier = Modifier, accountInfo: AccountInfo) {
    Column(
        modifier = modifier.padding(vertical = MaterialTheme.spacing.smallMedium)
    ) {
        Text(
            text = accountInfo.label,
            color = GedoiseColor.DarkGray,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = accountInfo.value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
internal fun AccountTopBar(isEdited: Boolean, onSaveClick: () -> Unit, onCancelClick: () -> Unit, onBackClick: () -> Unit) {
    if (isEdited) {
        SmallTopBarEdit(
            title = stringResource(id = R.string.account_informations),
            onCancelClick = onCancelClick,
            onSaveClick = onSaveClick
        )
    } else {
        SmallTopBarBack(
            title = stringResource(id = R.string.account_informations),
            onBackClick = onBackClick
        )
    }
}