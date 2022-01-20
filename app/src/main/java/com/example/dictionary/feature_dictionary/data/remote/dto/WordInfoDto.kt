package com.example.dictionary.feature_dictionary.data.remote.dto

import com.example.dictionary.feature_dictionary.domain.models.WordInfo

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toInfoInfo() : WordInfo {
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}