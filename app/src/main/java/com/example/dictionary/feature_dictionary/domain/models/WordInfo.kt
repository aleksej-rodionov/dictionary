package com.example.dictionary.feature_dictionary.domain.models

import com.example.dictionary.feature_dictionary.data.remote.dto.MeaningDto
import com.example.dictionary.feature_dictionary.data.remote.dto.PhoneticDto

data class WordInfo(
    val meanings: List<Meaning>?,
    val origin: String?,
    val phonetic: String?,
    val word: String
)
