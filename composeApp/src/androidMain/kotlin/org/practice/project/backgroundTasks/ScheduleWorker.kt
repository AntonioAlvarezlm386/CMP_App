package org.practice.project.backgroundTasks

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

class ScheduleWorker {

    fun scheduleSync(context: Context){
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, java.util.concurrent.TimeUnit.MINUTES
        ).build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncWordler45",
            ExistingPeriodicWorkPolicy.KEEP ,
            workRequest
        )
    }
}