package com.upsaclay.gedoise.presentation.profile.support

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import com.upsaclay.common.domain.model.User
import java.lang.reflect.Modifier
import java.util.Properties
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SupportViewModel : ViewModel() {

    @Composable
    fun SendEmail(user : User?, subject: String, body: String) {
        var showDialog by remember { mutableStateOf(false) }
        val to : String = "application.ged@gmail.com"
        val properties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(this.defaultUserName,)// trouver comment mettre identifiant et mot de passe user
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(this)) // trouver l'adresse mail de user
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                setSubject(subject)
                setText(body)
            }

            Transport.send(message)
            println("Email envoyé avec succès !")
        } catch (e: Exception) {
            ShowMessageDialog(showDialog = showDialog, onDismiss = { showDialog = false })
        }
    }

    @Composable
    fun ShowMessageDialog(showDialog: Boolean, onDismiss: () -> Unit) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { onDismiss() },  // Action lorsqu'on clique en dehors de la boîte de dialogue
                title = { Text(text = "Titre du message") },
                text = { Text("Ceci est le contenu du message.") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismiss()  // Action lorsque l'utilisateur confirme
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onDismiss()  // Action lorsque l'utilisateur annule ou ferme la boîte de dialogue
                        }
                    ) {
                        Text("Annuler")
                    }
                }
            )
        }
    }

}