package org.practice.project

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin


fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        koinApplication?.invoke(this)
        modules(
        )
    }
}