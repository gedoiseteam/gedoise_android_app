package com.upsaclay.gedoise.presentation.profile.support

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upsaclay.authentication.domain.repository.AuthenticationRepository
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.domain.usecase.GetCurrentUserFlowUseCase
import com.upsaclay.common.presentation.theme.GedoiseTheme
import com.upsaclay.common.presentation.theme.spacing
import com.upsaclay.gedoise.R
import com.upsaclay.gedoise.presentation.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SupportScreen(modifier: Modifier = Modifier,user: User)
{
    Column{
        var objet by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }
        val viewModel : SupportViewModel = SupportViewModel()
        Scaffold (topBar = { TopAppBar(navController = rememberNavController()) }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ){
                Text(text = stringResource(id = R.string.`object`))
                TextField(modifier = Modifier.padding(vertical = 7.5.dp), value = objet, onValueChange = { newObject -> objet = newObject })
                Spacer(modifier = Modifier.heightIn(MaterialTheme.spacing.large))
                Text(modifier = Modifier.padding(vertical = 7.5.dp),text = stringResource(id = R.string.content_message))
                TextField(modifier = Modifier.
                size(500.dp,500.dp),
                    value = body,
                    onValueChange = { newBody -> body = newBody })
                Spacer(modifier = Modifier.heightIn(MaterialTheme.spacing.large))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { viewModel.contactSupport(body,objet) },
                    content = { Text(text = stringResource(id = R.string.send_message_support)) })
            }
        }

    }
        TODO("résoudre le problème de l'objet context")
    }


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBar(navController: NavController)
{
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.support),
                textAlign = TextAlign.Center
            )
        },
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


@Preview
@Composable
fun SupportScreenPreview()
{
    GedoiseTheme {
        Column{
            var objet by remember { mutableStateOf("") }
            var body by remember { mutableStateOf("") }
            Scaffold (topBar = { TopAppBar(navController = rememberNavController()) }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                ){
                    Text(text = stringResource(id = R.string.`object`))
                    TextField(modifier = Modifier.padding(vertical = 7.5.dp), value = objet, onValueChange = { newObject -> objet = newObject })
                    Spacer(modifier = Modifier.heightIn(MaterialTheme.spacing.large))
                    Text(modifier = Modifier.padding(vertical = 7.5.dp),text = stringResource(id = R.string.content_message))
                    TextField(modifier = Modifier.
                                        size(500.dp,500.dp),
                        value = body,
                        onValueChange = { newBody -> body = newBody })
                    Spacer(modifier = Modifier.heightIn(MaterialTheme.spacing.large))
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { },
                        content = { Text(text = stringResource(id = R.string.send_message_support)) })
                }
            }

        }
    }
}