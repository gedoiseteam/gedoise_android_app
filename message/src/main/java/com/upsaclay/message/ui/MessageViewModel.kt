package com.upsaclay.message.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel



class MessageViewModel(

) : ViewModel() {
    fun Discussion()
    {

    }
    @Composable
    fun CreateAGroup()
    {

    }
    fun ANewDiscussion()
    {

    }
    /*
    * OpenDiscussion : ouvrir la discussion
    * CreateAGrup : créer un groupe
    * CreateANewDiscussion : créer une nouvelle discussion avec une nouvelle personne
    * */

    data class UiState(
        val unreadenMessageViewModel: Int,
        val isbolded : Boolean
    )

}