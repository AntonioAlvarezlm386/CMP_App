package org.practice.project

import android.app.Application
import org.koin.dsl.module

class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin{
            it.modules(
                module{
                    single { this@BaseApplication.applicationContext }
                }
            )
        }
    }
}