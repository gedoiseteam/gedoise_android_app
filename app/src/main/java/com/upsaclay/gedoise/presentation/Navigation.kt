package com.upsaclay.gedoise.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.upsaclay.authentication.presentation.AuthenticationScreen
import com.upsaclay.authentication.presentation.registration.EmailVerificationScreen
import com.upsaclay.authentication.presentation.registration.FirstRegistrationScreen
import com.upsaclay.authentication.presentation.registration.SecondRegistrationScreen
import com.upsaclay.authentication.presentation.registration.RegistrationViewModel
import com.upsaclay.authentication.presentation.registration.ThirdRegistrationScreen
import com.upsaclay.authentication.presentation.registration.FourthRegistrationScreen
import com.upsaclay.common.domain.model.Screen
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.presentation.components.SmallTopBarBack
import com.upsaclay.gedoise.data.BottomNavigationItem
import com.upsaclay.gedoise.presentation.components.MainBottomBar
import com.upsaclay.gedoise.presentation.components.MainTopBar
import com.upsaclay.gedoise.presentation.components.SplashScreen
import com.upsaclay.gedoise.presentation.profile.ProfileScreen
import com.upsaclay.gedoise.presentation.account.AccountScreen
import com.upsaclay.message.presentation.screen.ConversationScreen
import com.upsaclay.message.presentation.screen.CreateConversationScreen
import com.upsaclay.message.presentation.screen.CreateGroupConversationScreen
import com.upsaclay.message.presentation.viewmodel.ConversationViewModel
import com.upsaclay.news.domain.usecase.ConvertAnnouncementToJsonUseCase
import com.upsaclay.news.presentation.screen.CreateAnnouncementScreen
import com.upsaclay.news.presentation.screen.EditAnnouncementScreen
import com.upsaclay.news.presentation.screen.NewsScreen
import com.upsaclay.news.presentation.screen.ReadAnnouncementScreen
import com.upsaclay.news.presentation.viewmodel.EditAnnouncementViewModel
import com.upsaclay.news.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Navigation(mainViewModel: MainViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val user = mainViewModel.user.collectAsState(null).value
    val isAuthenticated = mainViewModel.isAuthenticated.collectAsState(null).value

    val sharedRegistrationViewModel: RegistrationViewModel = koinViewModel()
    val sharedNewsViewModel: NewsViewModel = koinViewModel()
    val sharedConversationViewModel: ConversationViewModel = koinViewModel()

    val convertAnnouncementToJsonUseCase: ConvertAnnouncementToJsonUseCase by inject(
        ConvertAnnouncementToJsonUseCase::class.java
    )

    var startDestination by remember { mutableStateOf(Screen.SPLASH.route) }

    LaunchedEffect(key1 = isAuthenticated) {
        if(startDestination == Screen.SPLASH.route) {
            delay(1000)
            startDestination = if (isAuthenticated == true) {
                Screen.NEWS.route
            } else {
                Screen.AUTHENTICATION.route
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.SPLASH.route) {
            SplashScreen()
        }

        composable(Screen.AUTHENTICATION.route) {
            AuthenticationScreen(navController = navController)
        }

        composable(Screen.FIRST_REGISTRATION_SCREEN.route) {
            FirstRegistrationScreen(
                navController = navController,
                registrationViewModel = sharedRegistrationViewModel
            )
        }

        composable(Screen.SECOND_REGISTRATION_SCREEN.route) {
            SecondRegistrationScreen(
                navController = navController,
                registrationViewModel = sharedRegistrationViewModel
            )
        }

        composable(Screen.THIRD_REGISTRATION_SCREEN.route) {
            ThirdRegistrationScreen(
                navController = navController,
                registrationViewModel = sharedRegistrationViewModel
            )
        }

        composable(Screen.CHECK_EMAIL_VERIFIED_SCREEN.route) {
            EmailVerificationScreen(
                navController = navController,
                registrationViewModel = sharedRegistrationViewModel
            )
        }

        composable(Screen.FOURTH_REGISTRATION_SCREEN.route) {
            FourthRegistrationScreen(
                navController = navController,
                registrationViewModel = sharedRegistrationViewModel
            )
        }

        composable(Screen.NEWS.route) {
            user?.let {
                MainNavigationBars(
                    navController = navController,
                    bottomNavigationItems = mainViewModel.bottomNavigationItem.values.toList(),
                    user = user
                ) {
                    NewsScreen(
                        navController = navController,
                        newsViewModel = sharedNewsViewModel
                    )
                }
            }
        }

        composable(Screen.READ_ANNOUNCEMENT.route) {
            Scaffold(
                topBar = {
                    SmallTopBarBack(
                        onBackClick = { navController.popBackStack() },
                        title = stringResource(id = com.upsaclay.news.R.string.announcement)
                    )
                }
            ) { contentPadding ->
                ReadAnnouncementScreen(
                    modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                    navController = navController,
                    newsViewModel = sharedNewsViewModel
                )
            }
        }

        composable(Screen.CREATE_ANNOUNCEMENT.route) {
            user?.let {
                CreateAnnouncementScreen(navController = navController, user = user)
            }
        }

        composable(
            route = Screen.EDIT_ANNOUNCEMENT.route + "?editedAnnouncement={editedAnnouncement}",
            arguments = listOf(navArgument("editedAnnouncement") { type = StringType })
        ) { backStackEntry ->
            val jsonAnnouncement = backStackEntry.arguments?.getString("editedAnnouncement")

            jsonAnnouncement?.let {
                val announcement = convertAnnouncementToJsonUseCase.fromJson(jsonAnnouncement)
                val editAnnouncementViewModel: EditAnnouncementViewModel = koinViewModel(
                    parameters = { parametersOf(announcement) }
                )
                EditAnnouncementScreen(
                    navController = navController,
                    editAnnouncementViewModel = editAnnouncementViewModel
                )
            } ?: navController.popBackStack()
        }

        composable(Screen.CONVERSATIONS.route) {
            user?.let {
                MainNavigationBars(
                    navController = navController,
                    bottomNavigationItems = mainViewModel.bottomNavigationItem.values.toList(),
                    user = user
                ) {
                    ConversationScreen(
                        navController = navController,
                        conversationViewModel = sharedConversationViewModel
                    )
                }
            }
        }

        composable(Screen.CREATE_CONVERSATION.route) {
            Scaffold(
                topBar = {
                    SmallTopBarBack(
                        onBackClick = { navController.popBackStack() },
                        title = stringResource(id = com.upsaclay.message.R.string.new_conversation)
                    )
                }
            ) { innerPadding ->
                CreateConversationScreen(
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                    navController = navController,
                    conversationViewModel = sharedConversationViewModel
                )
            }
        }

        composable(Screen.CREATE_GROUP_CONVERSATION.route) {
            Scaffold(
                topBar = {
                    SmallTopBarBack(
                        onBackClick = { navController.popBackStack() },
                        title = stringResource(id = com.upsaclay.message.R.string.new_group)
                    )
                }
            ) { innerPadding ->
                CreateGroupConversationScreen(
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                    navController = navController,
                    conversationViewModel = sharedConversationViewModel
                )
            }
        }

        composable(Screen.CALENDAR.route) {
            user?.let {
                MainNavigationBars(
                    navController = navController,
                    bottomNavigationItems = mainViewModel.bottomNavigationItem.values.toList(),
                    user = user
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Calendar",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        composable(Screen.FORUM.route) {
            user?.let {
                MainNavigationBars(
                    navController = navController,
                    bottomNavigationItems = mainViewModel.bottomNavigationItem.values.toList(),
                    user = user
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Forum",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        composable(Screen.PROFILE.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.ACCOUNT.route) {
            AccountScreen(navController = navController)
        }
    }
}

@Composable
private fun MainNavigationBars(
    navController: NavController,
    bottomNavigationItems: List<BottomNavigationItem>,
    user: User,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        topBar = { MainTopBar(navController = navController, user = user) },
        bottomBar = {
            MainBottomBar(
                navController = navController,
                bottomNavigationItems = bottomNavigationItems
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            content()
        }
    }
}