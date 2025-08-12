package org.practice.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform