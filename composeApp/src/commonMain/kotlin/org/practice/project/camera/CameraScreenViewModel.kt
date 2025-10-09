package org.practice.project.camera

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.practice.project.InternalStoragePractice.domain.SaveImageOnfileStorageUseCAse
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


sealed class NewsScreenActionTakeImage {
    data object Camera : NewsScreenActionTakeImage()
}

class CameraScreenViewModel(
    private val saveImageOnfileStorageUseCAse: SaveImageOnfileStorageUseCAse
) : ViewModel() {
    // Estado interno mutable
    private val _sharedImage = MutableStateFlow<SharedImage?>(null)
    // Estado expuesto a la UI
    val sharedImage = _sharedImage.asStateFlow()

    fun setImage(image:  SharedImage?){
        _sharedImage.value = image
    }


    // esto es paravalidar los permisos
    private val _actionTakeImage = MutableSharedFlow<NewsScreenActionTakeImage>()
    val actionTakeImage: SharedFlow<NewsScreenActionTakeImage> = _actionTakeImage

    fun openCamera() {
            viewModelScope.launch {
                _actionTakeImage.emit(NewsScreenActionTakeImage.Camera)
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onSave(){
        val bytes = _sharedImage.value?.toByteArray()
        if(bytes != null){
            viewModelScope.launch {
                saveImageOnfileStorageUseCAse(bytes, "nombreDePrueba-${Uuid.random()}").fold(
                    onSuccess = {

                    },
                    onFailure = {  }
                )
            }
        }
    }

}