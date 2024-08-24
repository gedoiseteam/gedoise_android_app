package com.upsaclay.gedoise.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upsaclay.common.data.model.MenuItemData
import com.upsaclay.common.data.model.Screen
import com.upsaclay.common.ui.components.CircularProgressBar
import com.upsaclay.common.ui.components.ClickableMenuItem
import com.upsaclay.common.ui.components.ProfilePicture
import com.upsaclay.common.ui.theme.GedoiseColor
import com.upsaclay.common.ui.theme.GedoiseTheme
import com.upsaclay.common.ui.theme.spacing
import com.upsaclay.common.utils.userFixture
import com.upsaclay.gedoise.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val user = profileViewModel.user.collectAsState(initial = null).value
    val menuItemData: ImmutableList<MenuItemData> = buildMenuItemData(navController, profileViewModel)

    Scaffold(
        topBar = { ProfileTopBar(navController = navController) }
    ) {
        Box(Modifier.padding(top = it.calculateTopPadding())) {
            Column {
                user?.let { user ->
                    TopSection(
                        profilePictureUrl = user.profilePictureUrl,
                        userFullName = user.fullName
                    )

                    HorizontalDivider()

                    menuItemData.forEach { menuItem ->
                        ClickableMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = menuItem.text,
                            icon = menuItem.icon,
                            onClick = menuItem.onClick!!
                        )
                    }
                } ?: CircularProgressBar()
            }
        }
    }
}

@Composable
private fun TopSection(
    profilePictureUrl: String?,
    userFullName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePicture(
            imageUrl = profilePictureUrl,
            scaleImage = 0.7f
        )

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

        Text(
            text = userFullName,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    navController: NavController
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.profile), textAlign = TextAlign.Center) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = com.upsaclay.common.R.string.arrow_back_icon_description)
                )
            }
        }
    )
}

private fun buildMenuItemData(
    navController: NavController,
    profileViewModel: ProfileViewModel
): ImmutableList<MenuItemData> {
    return persistentListOf(
        MenuItemData(
            text = { Text(text = stringResource(id = R.string.account_informations)) },
            icon = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = com.upsaclay.common.R.drawable.ic_person),
                    contentDescription = stringResource(id = R.string.account_icon_description),
                )
            },
            onClick = { navController.navigate(Screen.ACCOUNT_INFOS.route) }
        ),
//        MenuItemData(
//            text = {
//                Text(text = stringResource(id = R.string.settings))
//            },
//            icon = {
//                Icon(
//                    modifier = Modifier.size(28.dp),
//                    painter = painterResource(id = com.upsaclay.common.R.drawable.ic_settings),
//                    contentDescription = stringResource(id = R.string.settings_icon_description)
//                )
//            }
//        ),
//        MenuItemData(
//            text = {
//                Text(text = stringResource(id = R.string.support))
//            },
//            icon = {
//                Icon(
//                    painter = painterResource(id = com.upsaclay.common.R.drawable.ic_support),
//                    contentDescription = stringResource(id = R.string.support_icon_description)
//                )
//            }
//        ),
        MenuItemData(
            text = {
                Text(
                    text = stringResource(id = R.string.logout),
                    color = GedoiseColor.Red
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = com.upsaclay.common.R.drawable.ic_logout),
                    contentDescription = stringResource(id = R.string.logout_icon_description),
                    tint = GedoiseColor.Red
                )
            },
            onClick = { profileViewModel.logout() }
        )
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    GedoiseTheme {
        Scaffold(
            topBar = { ProfileTopBar(navController = rememberNavController()) }
        ) {
            Column(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.medium
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = com.upsaclay.common.R.drawable.default_profile_picture),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .border(1.dp, Color.LightGray, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

                    Text(
                        text = userFixture.firstName + " " + userFixture.lastName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                HorizontalDivider()

                menuItemsFixtureData.forEach { menuItem ->
                    ClickableMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = menuItem.text,
                        icon = menuItem.icon,
                        onClick = menuItem.onClick!!
                    )
                }
            }
        }
    }
}

private val menuItemsFixtureData: ImmutableList<MenuItemData> = persistentListOf(
    MenuItemData(
        text = { Text(text = stringResource(id = R.string.account_informations)) },
        icon = {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = com.upsaclay.common.R.drawable.ic_person),
                contentDescription = stringResource(id = R.string.account_icon_description),
            )
        },
    ),
//    MenuItemData(
//        text = {
//            Text(text = stringResource(id = R.string.settings))
//        },
//        icon = {
//            Icon(
//                modifier = Modifier.size(28.dp),
//                painter = painterResource(id = com.upsaclay.common.R.drawable.ic_settings),
//                contentDescription = stringResource(id = R.string.settings_icon_description)
//            )
//        }
//    ),
//    MenuItemData(
//        text = {
//            Text(text = stringResource(id = R.string.support))
//        },
//        icon = {
//            Icon(
//                painter = painterResource(id = com.upsaclay.common.R.drawable.ic_support),
//                contentDescription = stringResource(id = R.string.support_icon_description)
//            )
//        }
//    ),
    MenuItemData(
        text = {
            Text(
                text = stringResource(id = R.string.logout),
                color = GedoiseColor.Red
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = com.upsaclay.common.R.drawable.ic_logout),
                contentDescription = stringResource(id = R.string.logout_icon_description),
                tint = GedoiseColor.Red
            )
        }
    )
)