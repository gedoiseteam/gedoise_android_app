package com.upsaclay.authentication.ui.registration

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upsaclay.authentication.R
import com.upsaclay.authentication.data.model.RegistrationState
import com.upsaclay.authentication.ui.components.OutlinedEmailInput
import com.upsaclay.authentication.ui.components.OutlinedPasswordInput
import com.upsaclay.authentication.ui.components.RegistrationTopBar
import com.upsaclay.core.data.model.Screen
import com.upsaclay.core.ui.components.PrimaryLargeButton
import com.upsaclay.core.ui.theme.GedoiseTheme
import com.upsaclay.core.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun SecondRegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = koinViewModel()
){
    val registrationState by registrationViewModel.registrationState.collectAsState()

    if(registrationState == RegistrationState.RECOGNIZED_ACCOUNT){
        navController.navigate(Screen.THIRD_REGISTRATION_SCREEN.route)
    }

    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    isError = registrationState == RegistrationState.UNRECOGNIZED_ACCOUNT ||
            registrationState == RegistrationState.ERROR_INPUT

    errorMessage = when (registrationState) {
        RegistrationState.UNRECOGNIZED_ACCOUNT ->
            stringResource(id = R.string.unrecognized_account)
        RegistrationState.ERROR_INPUT ->
            stringResource(id = com.upsaclay.core.R.string.error_empty_fields)
        else -> ""
    }

    Scaffold(
        topBar = {
            RegistrationTopBar(
                navController = navController,
                currentStep = 2,
                maxStep = MAX_STEP
            )
        }
    ) {
        Box(
            Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                )
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)

            ) {
                Text(
                    text = stringResource(id = R.string.enter_school_credentials),
                    style = MaterialTheme.typography.titleMedium,
                )
                OutlinedEmailInput(
                    text = registrationViewModel.email,
                    onValueChange = { mailText ->
                        registrationViewModel.updateEmail(mailText)
                    },
                    isError = isError
                )
                Spacer(Modifier.height(MaterialTheme.spacing.small))

                OutlinedPasswordInput(
                    text = registrationViewModel.password,
                    isError = isError,
                    onValueChange = { passwordText ->
                        registrationViewModel.updatePassword(passwordText)
                    }
                )

                if (isError) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }

            PrimaryLargeButton(
                text = stringResource(id = com.upsaclay.core.R.string.next),
                onClick = {
                    registrationViewModel.verifyAccount(
                        registrationViewModel.email,
                        registrationViewModel.password
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun SecondRegistrationStepPreview(){
    val mail = "pierre.dupont@universite-paris-saclay.fr"
    val password = "password"
    GedoiseTheme {
        Scaffold(
            topBar = {
                RegistrationTopBar(
                    navController = rememberNavController(),
                    currentStep = 2,
                    maxStep = MAX_STEP
                )
            }
        ) {
            Box(
                Modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_school_credentials),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    OutlinedEmailInput(
                        text = mail,
                        onValueChange = {},
                    )
                    Spacer(Modifier.height(MaterialTheme.spacing.small))

                    OutlinedPasswordInput(
                        text = password,
                        onValueChange = {},
                    )
                }

                PrimaryLargeButton(
                    text = stringResource(id = com.upsaclay.core.R.string.next),
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    }
}
