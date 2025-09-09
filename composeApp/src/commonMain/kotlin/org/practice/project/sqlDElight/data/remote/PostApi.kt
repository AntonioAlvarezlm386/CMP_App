package org.practice.project.sqlDElight.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.practice.project.sqlDElight.domain.Post

const val ALL_POSTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts?_limit=10"

class PostApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun fetchAllPosts(): List<Post> {
        println("INFO: Fetching new Posts from the network...")
        return httpClient.get(urlString = ALL_POSTS_ENDPOINT)
            .body()
    }
}