package org.practice.project.sqlDElight.data

import com.russhwolf.settings.Settings
import org.practice.project.sqlDElight.data.local.LocalDatabase
import org.practice.project.sqlDElight.data.remote.PostApi
import org.practice.project.sqlDElight.domain.Post
import org.practice.project.sqlDElight.domain.RequestState
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

const val FRESH_DATA_KEY = "freshDataTimestamp"

class PostSDK(
    private val api: PostApi,
    private val database: LocalDatabase,
    private val settings: Settings
) {
    @OptIn(ExperimentalTime::class)
    @Throws(Exception::class)
    suspend fun getAllPosts(): RequestState<List<Post>> {
        return try {
            val cachedPosts = database.readAllPosts()
            if (cachedPosts.isEmpty()) {
                settings.putLong(
                    FRESH_DATA_KEY,
                    Clock.System.now().toEpochMilliseconds()
                )
                RequestState.Success(
                    api.fetchAllPosts().also {
                        database.removeAllPosts()
                        database.insertAllPosts(it)
                    }
                )
            } else {
                if (isDataStale()) {
                    settings.putLong(
                        FRESH_DATA_KEY,
                        Clock.System.now().toEpochMilliseconds()
                    )
                    RequestState.Success(
                        api.fetchAllPosts().also {
                            database.removeAllPosts()
                            database.insertAllPosts(it)
                        }
                    )
                } else RequestState.Success(cachedPosts)
            }
        } catch (e: Exception) {
            RequestState.Error(e.message.toString())
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun isDataStale(): Boolean {
        val savedTimestamp = Instant.fromEpochMilliseconds(
            settings.getLong(FRESH_DATA_KEY, defaultValue = 0L)
        )
        val currentTimestamp = Clock.System.now()
        val difference =
            if (savedTimestamp > currentTimestamp) savedTimestamp - currentTimestamp
            else currentTimestamp - savedTimestamp
        return difference >= 24.hours
    }
}