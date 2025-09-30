package org.practice.project.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


sealed class NewsScreenActionTakeImage {
    data object Camera : NewsScreenActionTakeImage()
}

class CameraScreenViewModel(
) : ViewModel() {
    private val _actionTakeImage = MutableSharedFlow<NewsScreenActionTakeImage>()
    val actionTakeImage: SharedFlow<NewsScreenActionTakeImage> = _actionTakeImage

    fun openCamera() {
            viewModelScope.launch {
                _actionTakeImage.emit(NewsScreenActionTakeImage.Camera)
            }
    }

}