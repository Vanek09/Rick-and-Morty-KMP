package com.box.plantintest.platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit

class AndroidHttpClientFactory : PlatformHttpClientFactory, KoinComponent {
    override fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                // Configure timeout
                config {
                    connectTimeout(30, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                }
            }

            // Apply common configuration
            block()
        }
    }
}

/**
 * Android implementation of getPlatformHttpClientFactory
 */
actual fun getPlatformHttpClientFactory(): PlatformHttpClientFactory = AndroidHttpClientFactory()