package com.box.plantintest.platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Factory interface for creating platform-specific HTTP clients
 */
interface PlatformHttpClientFactory {
    /**
     * Creates a platform-specific HTTP client with the provided configuration block
     */
    fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient
}

/**
 * Expect/actual declaration for getting platform-specific HTTP client factory
 */
expect fun getPlatformHttpClientFactory(): PlatformHttpClientFactory