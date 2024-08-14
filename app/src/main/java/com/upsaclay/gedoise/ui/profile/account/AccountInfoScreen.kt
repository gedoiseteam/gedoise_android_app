package com.upsaclay.gedoise.ui.profile.account

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.compose.AsyncImage
import com.upsaclay.common.data.model.MenuItemData
import com.upsaclay.common.ui.components.ImageWithIcon
import com.upsaclay.common.ui.components.MenuItem
import com.upsaclay.common.ui.components.OverlayLoadingScreen
import com.upsaclay.common.ui.components.SimpleDialog
import com.upsaclay.common.ui.theme.GedoiseColor
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.common.ui.theme.spacing
import com.upsaclay.common.utils.userFixture
import com.upsaclay.gedoise.R
import com.upsaclay.gedoise.data.profile.AccountInfoItemData
import com.upsaclay.gedoise.data.profile.AccountInfoScreenState
import com.upsaclay.gedoise.ui.BackSmallTopBar
import com.upsaclay.gedoise.ui.EditTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoScreen(
    navController: NavController,
    accountInfoViewModel: AccountInfoViewModel = koinViewModel()
) {
    val currentUser = accountInfoViewModel.currentUser.collectAsState(initial = null)
    val accountScreenState = accountInfoViewModel.accountScreenState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isUserHasDefaultProfilePicture = accountInfoViewModel.isUserHasDefaultProfilePicture.collectAsState(true)
    val context = LocalContext.current
    var showDeleteProfilePictureDialog by remember { mutableStateOf(false) }

    val hideBottomSheet: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let{
                accountInfoViewModel.updateProfilePictureUri(it)
                accountInfoViewModel.updateAccountScreenState(AccountInfoScreenState.EDIT)
            }
        }
    )

    currentUser.value?.let { user ->
        val accountMenuItems: ImmutableList<AccountInfoItemData> = persistentListOf(
            AccountInfoItemData(
                stringResource(id = com.upsaclay.common.R.string.last_name),
                user.lastName
            ),
            AccountInfoItemData(
                stringResource(id = com.upsaclay.common.R.string.first_name),
                user.firstName
            ),
            AccountInfoItemData(
                stringResource(id = com.upsaclay.common.R.string.email),
                user.email
            ),
            AccountInfoItemData(
                stringResource(id = com.upsaclay.common.R.string.school_level),
                user.schoolLevel
            )
        )

        Scaffold(
            topBar = {
                if (accountScreenState.value == AccountInfoScreenState.EDIT) {
                    EditTopAppBar(
                        title = stringResource(id = R.string.account_informations),
                        onCancelClick = {
                            accountInfoViewModel.resetProfilePictureUri()
                            accountInfoViewModel.updateAccountScreenState(AccountInfoScreenState.READ)
                        },
                        onSaveClick = { accountInfoViewModel.updateUserProfilePicture() }
                    )
                } else {
                    BackSmallTopBar(
                        navController = navController,
                        title = stringResource(id = R.string.account_informations)
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding() + MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PictureSection(
                        isEdited = accountScreenState.value == AccountInfoScreenState.EDIT,
                        profilePictureUri = accountInfoViewModel.profilePictureUri,
                        profilePictureUrl = user.profilePictureUrl,
                        onClick = {
                            if (accountScreenState.value == AccountInfoScreenState.EDIT) {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            } else {
                                showBottomSheet = true
                            }
                        },
                    )

                    accountMenuItems.forEach { accountMenuItemData ->
                        AccountInfoItem(
                            modifier = Modifier.fillMaxWidth(),
                            accountInfoItemData = accountMenuItemData
                        )
                    }

                    if (showBottomSheet) {
                        AccountInfoModelBottomSheet(
                            onDismissRequest = { showBottomSheet = false },
                            onNewProfilePictureClick = {
                                hideBottomSheet()
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            showDeleteProfilePicture = isUserHasDefaultProfilePicture.value,
                            onDeleteProfilePictureClick = {
                                hideBottomSheet()
                                showDeleteProfilePictureDialog = true
                            }
                        )
                    }
                }

                when(accountScreenState.value) {
                    AccountInfoScreenState.LOADING -> {
                        OverlayLoadingScreen()
                    }
                    AccountInfoScreenState.ERROR_UPDATING_PROFILE_PICTURE -> {
                        Toast.makeText(context, R.string.error_uploading_profile_picture, Toast.LENGTH_SHORT).show()
                        accountInfoViewModel.updateAccountScreenState(AccountInfoScreenState.READ)
                    }
                    AccountInfoScreenState.PROFILE_PICTURE_UPDATED -> {
                        Coil.imageLoader(context).shutdown()
                        Toast.makeText(context, R.string.profile_picture_uploaded, Toast.LENGTH_SHORT).show()
                        accountInfoViewModel.updateAccountScreenState(AccountInfoScreenState.READ)
                    }
                    else -> {}
                }

                if(showDeleteProfilePictureDialog) {
                    SimpleDialog(
                        title = stringResource(id = R.string.delete_current_profile_picture),
                        text = stringResource(id = R.string.delete_profil_picture_dialog_text),
                        onConfirm = {
                            showDeleteProfilePictureDialog = false
                            accountInfoViewModel.deleteUserProfilePicture()
                        },
                        onCancel = { showDeleteProfilePictureDialog = false },
                        onDismiss = { showDeleteProfilePictureDialog = false },
                        confirmText = stringResource(id = com.upsaclay.common.R.string.delete),
                    )
                }
            }
        }
    }
}

@Composable
private fun PictureSection(
    isEdited: Boolean,
    profilePictureUri: Uri?,
    profilePictureUrl: String?,
    onClick: () -> Unit,
) {
    val scaleImage = 1.8f

    profilePictureUri?.let { uri ->
        if (!isEdited) {
            ImageWithIcon(
                imageUri = uri,
                iconVector = Icons.Default.Edit,
                contentDescription = "",
                scale = scaleImage,
                onClick = onClick
            )
        } else {
            AsyncImage(
                model = uri,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp * scaleImage)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable(onClick = onClick)
            )
        }
    } ?: run {
        profilePictureUrl?.let {
            ImageWithIcon(
                imageUrl = it,
                iconVector = Icons.Default.Edit,
                contentDescription = "",
                scale = scaleImage,
                onClick = onClick
            )
        } ?: run {
            ImageWithIcon(
                drawableRes = com.upsaclay.common.R.drawable.default_profile_picture,
                iconVector = Icons.Default.Edit,
                contentDescription = "",
                scale = scaleImage,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountInfoModelBottomSheet(
   onDismissRequest: () -> Unit,
   onNewProfilePictureClick: () -> Unit,
   showDeleteProfilePicture: Boolean = false,
   onDeleteProfilePictureClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        MenuItem(
            menuItemData = MenuItemData(
                text = { Text(text = stringResource(id = R.string.new_profile_picture)) },
                icon = {
                    Icon(
                        painter = painterResource(id = com.upsaclay.common.R.drawable.ic_picture),
                        contentDescription = null
                    )
                },
                onClick = onNewProfilePictureClick
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (showDeleteProfilePicture) {
            MenuItem(
                menuItemData = MenuItemData(
                    text = {
                        Text(
                            text = stringResource(id = R.string.delete_current_profile_picture),
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
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    }
}

@Composable
fun AccountInfoItem(
    modifier: Modifier = Modifier,
    accountInfoItemData: AccountInfoItemData
) {
    Column(
        modifier = modifier.padding(vertical = MaterialTheme.spacing.smallMedium),
    ) {
        Text(
            text = accountInfoItemData.label,
            color = GedoiseColor.DarkGrey,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = accountInfoItemData.value,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AccountScreenPreview() {
    val scaleImage = 1.8f
    val sizeImage = 100.dp * scaleImage
    val pictureChanged = false
    val isEdited = false

    val accountMenuItems: ImmutableList<AccountInfoItemData> = persistentListOf(
        AccountInfoItemData(
            stringResource(id = com.upsaclay.common.R.string.last_name),
            userFixture.lastName
        ),
        AccountInfoItemData(
            stringResource(id = com.upsaclay.common.R.string.first_name),
            userFixture.firstName
        ),
        AccountInfoItemData(
            stringResource(id = com.upsaclay.common.R.string.email),
            userFixture.email
        ),
        AccountInfoItemData(
            stringResource(id = com.upsaclay.common.R.string.school_level),
            userFixture.schoolLevel
        )
    )

    GedoiseTheme {
        Scaffold(
            topBar = {
                if (isEdited) {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.account_informations)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            TextButton(
                                onClick = {}
                            ) {
                                Text(
                                    text = stringResource(id = com.upsaclay.common.R.string.save),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )
                } else {
                    BackSmallTopBar(
                        navController = rememberNavController(),
                        title = stringResource(id = R.string.account_informations)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = it.calculateTopPadding() + MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(sizeImage)) {
                    Image(
                        painter = painterResource(id = com.upsaclay.common.R.drawable.default_profile_picture),
                        contentDescription = "",
                        modifier = Modifier
                            .border(1.dp, Color.LightGray, CircleShape)
                            .fillMaxSize()
                    )
                    if(!pictureChanged) {
                        ImageWithIcon(
                            imageUrl = "",
                            contentDescription = "",
                            scale = scaleImage,
                            iconVector = Icons.Default.Edit,
                            onClick = {}
                        )
                    }
                }

                accountMenuItems.forEach { accountMenuItemData ->
                    AccountInfoItem(
                        modifier = Modifier.fillMaxWidth(),
                        accountInfoItemData = accountMenuItemData
                    )
                }
            }
        }
    }
}
