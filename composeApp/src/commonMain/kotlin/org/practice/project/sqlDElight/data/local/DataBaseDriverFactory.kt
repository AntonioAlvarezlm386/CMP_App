package org.practice.project.sqlDElight.data.local

import app.cash.sqldelight.db.SqlDriver
import org.practice.org.AppDatabase
import org.practice.project.sqlDElight.domain.Post

interface DataBaseDriverFactory {
    fun createDriver(): SqlDriver
}


class LocalDatabase(
    databaseDriverFactory: DataBaseDriverFactory
) {
    private val database = AppDatabase(
        databaseDriverFactory.createDriver()
    )
    private val query = database.postTableQueriesQueries

    fun readAllPosts(): List<Post> {
        println("INFO: Reading the cached data from the local database...")
        return query.readAllPosts()
            .executeAsList()
            .map {
                Post(
                    userId = it.userId.toInt(),
                    id = it.id.toInt(),
                    thumbnail = it.title,
                    title = it.title,
                    body = it.body
                )
            }
    }

    fun insertAllPosts(posts: List<Post>) {
        println("INFO: Caching the data from the network...")
        query.transaction {
            posts.forEach { post ->
                query.insertPost(
                    userId = post.userId.toLong(),
                    id = post.id.toLong(),
                    title = post.title,
                    body = post.body
                )
            }
        }
    }

    fun removeAllPosts() {
        query.removeAllPosts()
    }
}