package com.upsaclay.news.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.upsaclay.common.presentation.components.LoadingDialog
import com.upsaclay.common.presentation.components.SmallTopBarEdit
import com.upsaclay.common.presentation.components.TransparentFocusedTextField
import com.upsaclay.common.presentation.components.TransparentTextField
import com.upsaclay.common.presentation.theme.GedoiseTheme
import com.upsaclay.common.presentation.theme.spacing
import com.upsaclay.common.utils.showToast
import com.upsaclay.news.R
import com.upsaclay.news.announcementFixture
import com.upsaclay.news.domain.model.AnnouncementState
import com.upsaclay.news.presentation.viewmodel.EditAnnouncementViewModel
import java.time.LocalDateTime

@Composable
fun EditAnnouncementScreen(
    navController: NavController,
    editAnnouncementViewModel: EditAnnouncementViewModel
) {
    val context = LocalContext.current
    val state = editAnnouncementViewModel.announcementState.collectAsState(AnnouncementState.DEFAULT).value
    val title: String = editAnnouncementViewModel.title
    val content: String = editAnnouncementViewModel.content
    val isAnnouncementModified = editAnnouncementViewModel.isAnnouncementModified.collectAsState(initial = false).value
    var showLoadingDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state) {
        when (state) {
            AnnouncementState.ANNOUNCEMENT_UPDATE_ERROR -> {
                showLoadingDialog = false
                showToast(context, R.string.announcement_update_error)
            }

            AnnouncementState.ANNOUNCEMENT_UPDATED -> {
                showLoadingDialog = false
                focusManager.clearFocus()
                keyboardController?.hide()
                navController.popBackStack()
            }

            AnnouncementState.LOADING -> showLoadingDialog = true

            else -> {}
        }
    }

    if (showLoadingDialog) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            SmallTopBarEdit(
                onCancelClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    navController.popBackStack()
                },
                onSaveClick = {
                    editAnnouncementViewModel.updateAnnouncement(
                        editAnnouncementViewModel.editedAnnouncement.copy(
                            title = title,
                            content = content,
                            date = LocalDateTime.now()
                        )
                    )
                },
                isButtonEnable = content.isNotBlank() && isAnnouncementModified
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.medium
                )
                .fillMaxSize()
        ) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                TransparentFocusedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    defaultValue = title,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title_field_entry),
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    onValueChange = { editAnnouncementViewModel.updateTitle(it) },
                    textStyle = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                TransparentTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = content,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.content_field_entry),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onValueChange = { editAnnouncementViewModel.updateContent(it) },
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/*
 =====================================================================
                                Preview
 =====================================================================
 */

@Preview
@Composable
private fun EditAnnouncementScreenPreview() {
    var title by remember { mutableStateOf(announcementFixture.title) }
    var content by remember { mutableStateOf(announcementFixture.content) }

    GedoiseTheme {
        Scaffold(
            topBar = {
                SmallTopBarEdit(
                    onCancelClick = { },
                    onSaveClick = { }
                )
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium
                    )
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                TransparentFocusedTextField(
                    defaultValue = title ?: "",
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title_field_entry),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                TransparentTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.content_field_entry),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
