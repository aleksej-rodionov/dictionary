package com.example.dictionary.feature_dictionary.presentation

import com.example.dictionary.feature_dictionary.domain.models.WordInfo

data class WordInfoState(
    val wordInfoItams: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)