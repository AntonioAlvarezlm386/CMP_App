package org.practice.project.backgroundTasks

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.practice.project.BackGroundTasks.SyncManager


class SyncWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {


        val synmanager = SyncManager()
        // Indicate whether the work finished successfully with the Result
        return try{
            synmanager.syncData()
            Result.success()
        } catch (e: Exception){
            println("Ocurruio un errrrrroorrr -------------------------------")
            Result.failure()
        }
    }
}