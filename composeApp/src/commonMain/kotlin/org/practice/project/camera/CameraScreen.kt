package org.practice.project.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraScreen(
){
    val viewModel = koinViewModel<CameraScreenViewModel>()
    val uiState by viewModel.sharedImage.collectAsStateWithLifecycle()

    val cameraManager = rememberCameraManager { capturedImage ->
        viewModel.setImage(capturedImage)
    }

    LaunchedEffect(Unit) {
        viewModel.actionTakeImage.collect { action ->
            when(action) {
                NewsScreenActionTakeImage.Camera -> cameraManager.launch()
            }
        }
    }


    NewsScreen(
        onClickCamera = {
            cameraManager.launch()
        },
        sharedImage = uiState,
        onSave = viewModel::onSave
    )
}


@Composable
fun NewsScreen(
    onClickCamera: () -> Unit,
    sharedImage: SharedImage? = null,
    onSave: () -> Unit
) {
    Scaffold(
        bottomBar = {
        },
        snackbarHost = {},
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fotos Screen",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Subitulo",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    ImagePickerContent(
                        sharedImage = sharedImage,
                        onClickCamera = onClickCamera,
                        onSave = onSave
                    )
                }
            }
        }
    )
}


@Composable
fun ImagePickerContent(
    sharedImage: SharedImage?,
    onClickCamera: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(3f / 4f)
                .background(Color.Gray.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            sharedImage?.toImageBitMap()?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Image(
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onClickCamera, content = {
                Text(text = "tomar foto")
            })
            Button(onClick = onSave, content = {
                Text(text = "guardar")
            })
        }
    }
}