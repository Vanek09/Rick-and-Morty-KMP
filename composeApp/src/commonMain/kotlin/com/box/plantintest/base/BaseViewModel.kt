package com.box.plantintest.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> :
    CoroutinesViewModel() {
    private val initialState: State by lazy { createInitialState() }

    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    private val events = _events.asSharedFlow()

    private val _effects: Channel<Effect> = Channel(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun setEvent(event: Event) {
        launch {
            _events.emit(event)
        }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        _effects.trySend(effectValue)
    }

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        launch {
            events.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)
}
