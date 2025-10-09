package org.practice.project

import org.koin.core.module.Module
import org.koin.dsl.module
import org.practice.project.InternalStoragePractice.domain.InterNalStorageProvider
import org.practice.project.internlStorage.AndroiInternalStorageProvider

actual val koinPlatormModule: Module = module {
    single<InterNalStorageProvider>{
        AndroiInternalStorageProvider(
            context = get()
        )
    }
}