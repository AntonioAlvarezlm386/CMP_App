import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.practice.project.InternalStoragePractice.domain.SaveImageOnfileStorageUseCAse
import org.practice.project.camera.CameraScreenViewModel

val koincommonModule: Module = module {
    single<SaveImageOnfileStorageUseCAse>{
        SaveImageOnfileStorageUseCAse(
            interNalStorageProvider = get()
        )
    }

    viewModel<CameraScreenViewModel>{
        CameraScreenViewModel(
            saveImageOnfileStorageUseCAse = get()
        )
    }
}