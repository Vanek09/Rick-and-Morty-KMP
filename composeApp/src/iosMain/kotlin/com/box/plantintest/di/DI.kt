package com.box.plantintest.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        printLogger()
        modules(
            appModules
        )
    }
}