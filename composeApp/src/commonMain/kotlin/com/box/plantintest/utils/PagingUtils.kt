package com.box.plantintest.utils

fun extractNextPage(nextUrl: String?): Int? {
    if (nextUrl.isNullOrBlank()) return null
    // Fast path: find "?" then split params
    val query = nextUrl.substringAfter('?', missingDelimiterValue = "")
    if (query.isEmpty()) return null
    // Parse params safely
    val pairs = query.split('&')
    for (pair in pairs) {
        val key = pair.substringBefore('=')
        if (key == "page") {
            val value = pair.substringAfter('=', missingDelimiterValue = "")
            return value.toIntOrNull()
        }
    }
    return null
}

