package org.practice.project.navigation.main.camera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.practice.project.navigation.main.camera.domain.model.PhotoData
import org.practice.project.navigation.main.camera.domain.useCases.DeletePhotoUseCase
import org.practice.project.navigation.main.camera.domain.useCases.GetPhotosUseCase
import org.practice.project.navigation.main.camera.domain.useCases.TakePhotoUseCase

class CameraViewModel(
    private val takePhotoUseCase: TakePhotoUseCase,
    private val getPhotosUseCase: GetPhotosUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    init {
        loadPhotos()
    }

    fun takePhoto() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            takePhotoUseCase().fold(
                onSuccess = { photoData ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            photos = currentState.photos + photoData,
                            message = "Foto guardada exitosamente"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getPhotosUseCase().fold(
                onSuccess = { photos ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            photos = photos
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }

    fun deletePhoto(photoId: String) {
        viewModelScope.launch {
            deletePhotoUseCase(photoId).fold(
                onSuccess = {
                    _uiState.update { currentState ->
                        currentState.copy(
                            photos = currentState.photos.filter { it.id != photoId },
                            message = "Foto eliminada"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(error = error.message)
                    }
                }
            )
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null, error = null) }
    }
}

data class CameraUiState(
    val isLoading: Boolean = false,
    val photos: List<PhotoData> = emptyList(),
    val error: String? = null,
    val message: String? = null
)