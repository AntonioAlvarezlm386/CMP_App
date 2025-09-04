package org.practice.project.paging

import io.ktor.client.engine.HttpClientEngine

expect class HttpClientEngineFactory() { // dedbe ser un cnstructor vacio
    fun create(): HttpClientEngine
}