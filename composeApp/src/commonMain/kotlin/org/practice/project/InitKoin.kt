package org.practice.project

import koincommonModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin


fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        koinApplication?.invoke(this)
        modules(
            koinPlatormModule, koincommonModule
        )
    }
}