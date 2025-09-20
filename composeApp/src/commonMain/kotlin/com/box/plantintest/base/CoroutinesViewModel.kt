package com.box.plantintest.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class CoroutinesViewModel : ViewModel(), CoroutineScope {
    private val errorHandler =
        CoroutineExceptionHandler { _, error ->
            error.printStackTrace()
            launch { _errorEvents.emit(error) }
        }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + errorHandler

    private val _errorEvents: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorEvents: SharedFlow<Throwable>
        get() = _errorEvents
}
