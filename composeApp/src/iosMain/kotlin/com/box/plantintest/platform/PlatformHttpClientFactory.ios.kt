package com.box.plantintest.platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS implementation of the PlatformHttpClientFactory
 */
class IosHttpClientFactory : PlatformHttpClientFactory {
    override fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(Darwin) {
            engine {
                // Configure Darwin-specific settings if needed
            }
            
            // Apply common configuration
            block()
        }
    }
}

/**
 * iOS implementation of getPlatformHttpClientFactory
 */
actual fun getPlatformHttpClientFactory(): PlatformHttpClientFactory = IosHttpClientFactory()