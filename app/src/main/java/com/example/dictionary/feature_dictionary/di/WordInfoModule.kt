package com.example.dictionary.feature_dictionary.di

import com.example.dictionary.feature_dictionary.data.local.WordInfoDao
import com.example.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionary.feature_dictionary.data.repository.WordInfoReposirotyImpl
import com.example.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionary.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        dao: WordInfoDao,
        api: DictionaryApi
    ): WordInfoRepository {
        return WordInfoReposirotyImpl(dao, api)
    }

    @Provides
    @Singleton
    fun
}