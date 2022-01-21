package com.example.dictionary.feature_dictionary.domain.repository

import com.example.dictionary.core.Resource
import com.example.dictionary.feature_dictionary.domain.models.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}