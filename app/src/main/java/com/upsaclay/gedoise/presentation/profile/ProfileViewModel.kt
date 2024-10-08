package com.upsaclay.gedoise.presentation.profile


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.MailTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsaclay.common.domain.usecase.GetCurrentUserFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authenticationRepository: com.upsaclay.authentication.domain.repository.AuthenticationRepository,
    getCurrentUserFlowUseCase: GetCurrentUserFlowUseCase
) : ViewModel() {
    val user: Flow<com.upsaclay.common.domain.model.User?> = getCurrentUserFlowUseCase()

    fun logout() {
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    fun contactSupport(context: Context, message: String, objet: String) {
        // Encodage des paramètres pour éviter les erreurs dans l'URI
        val encodedSubject = Uri.encode(objet)
        val encodedBody = Uri.encode(message)

        // Correction de l'URI
        val uri = Uri.parse("mailto:application.ged@gmail.com?subject=$encodedSubject&body=$encodedBody")

        // Création de l'Intent
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = uri // Directement utiliser l'URI formée
        }

        // Vérifier si une application peut gérer l'intention
        if (emailIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(emailIntent) // Utilisation du contexte passé
        } else {
            // Gérer le cas où aucune application n'est disponible pour envoyer des e-mails
            Toast.makeText(context, "Aucune application d'e-mail disponible", Toast.LENGTH_SHORT).show()
        }
    }

}