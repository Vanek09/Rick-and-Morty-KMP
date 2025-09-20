package com.box.plantintest.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.io.IOException


fun Throwable.toUserMessage(): String = when (this) {
    is ConnectTimeoutException, is SocketTimeoutException, is IOException -> "Немає з'єднання. Перевірте інтернет."
    is HttpRequestTimeoutException -> "Перевищено час очікування. Спробуйте ще раз."
    is ClientRequestException -> {
        val code = response.status.value
        if (code in 400..499) "Помилка запиту. Перевірте введені дані." else "Помилка клієнта."
    }
    is ServerResponseException -> "Помилка сервера. Спробуйте пізніше."
    else -> message ?: "Непередбачена помилка."
}
