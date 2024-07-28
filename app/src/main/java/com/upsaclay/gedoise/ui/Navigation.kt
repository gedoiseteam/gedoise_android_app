package com.upsaclay.gedoise.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upsaclay.authentication.domain.usecase.IsAuthenticatedUseCase
import com.upsaclay.authentication.ui.AuthenticationScreen
import com.upsaclay.authentication.ui.registration.FirstRegistrationScreen
import com.upsaclay.authentication.ui.registration.RegistrationViewModel
import com.upsaclay.authentication.ui.registration.SecondRegistrationScreen
import com.upsaclay.authentication.ui.registration.ThirdRegistrationScreen
import com.upsaclay.core.data.model.Screen
import com.upsaclay.gedoise.data.NavigationItem
import com.upsaclay.news.ui.NewsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin


@Composable
fun Navigation(
    mainViewModel: MainViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val navigationItems by mainViewModel.navigationItem.collectAsState()
    val isAuthenticatedUseCase: IsAuthenticatedUseCase = getKoin().get()
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        startDestination = if (isAuthenticatedUseCase.invoke()) {
            Screen.HOME.route
        } else {
            Screen.AUTHENTICATION.route
        }
    }

    val sharedRegistrationViewModel: RegistrationViewModel = koinViewModel()

    startDestination?.let { destination ->
        NavHost(
            navController = navController,
            startDestination = destination
        ) {
            composable(Screen.AUTHENTICATION.route) {
                AuthenticationScreen(navController = navController)
            }

            composable(Screen.FIRST_REGISTRATION_SCREEN.route) {
                FirstRegistrationScreen(navController = navController, sharedRegistrationViewModel)
            }

            composable(Screen.SECOND_REGISTRATION_SCREEN.route) {
                SecondRegistrationScreen(navController = navController, sharedRegistrationViewModel)
            }

            composable(Screen.THIRD_REGISTRATION_SCREEN.route) {
                ThirdRegistrationScreen(navController = navController, sharedRegistrationViewModel)
            }

            composable(Screen.HOME.route) {
                MainNavigationBars(
                    navController = navController,
                    navigationItems.values.toList()
                ) {
                    NewsScreen()
                }
            }

            composable(Screen.MESSAGE.route) {
                MainNavigationBars(
                    navController = navController,
                    navigationItems.values.toList()
                ) {
                    Text(text = "Message")
                }
            }

            composable(Screen.CALENDAR.route) {
                MainNavigationBars(
                    navController = navController,
                    navigationItems.values.toList()
                ) {
                    Text(text = "Calendar")
                }
            }

            composable(Screen.FORUM.route) {
                MainNavigationBars(
                    navController = navController,
                    navigationItems.values.toList()
                ) {
                    Text(text = "Forum")
                }
            }

            composable(Screen.PROFILE.route) {
                MainNavigationBars(
                    navController = navController,
                    navigationItems.values.toList()
                ) {
                    Text(text = "Profile")
                }
            }
        }
    }
}

@Composable
private fun MainNavigationBars(
    navController: NavController,
    navigationItems: List<NavigationItem>,
    content: @Composable () -> Unit
){
    Scaffold(
        topBar = { MainTopBar(navController = navController) },
        bottomBar = {
            MainBottomBar(
                navController = navController,
                navigationItems = navigationItems
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
