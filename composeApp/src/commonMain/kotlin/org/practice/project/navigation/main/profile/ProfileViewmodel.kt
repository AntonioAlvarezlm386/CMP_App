package org.practice.project.navigation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.practice.project.navigation.Event
import org.practice.project.navigation.EventBus.sendEvent

class ProfileViewmodel(): ViewModel() {
    fun onDateTimeClick(event: Event){
        when(event){
            Event.NavigateToHomeScreen -> TODO()
            is Event.Toast -> {
                viewModelScope.launch {
                    sendEvent(event)
                }
            }
        }
    }
}