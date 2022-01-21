package com.example.dictionary.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.core.Resource
import com.example.dictionary.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _searchQuery = mutableStateOf<String>("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L) // если втечение этих 0,5сек введен новый символ, то onSearch запускается заново и текущий searchJob кенселится. И запускается заново уже с новым query
            getWordInfo(query).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            wordInfoItams = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            wordInfoItams = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            wordInfoItams = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }
                }
            }.launchIn(this) // this относится к viewModelScope, в котором onEach этот завернут
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val msg: String): UIEvent()
    }
}