package com.noureldin.news.dataRepos.repository

import com.noureldin.news.dataRepos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.repository.sources.SourcesRepository
import com.noureldin.news.api.sourcesModel.Source
import javax.inject.Inject


class SourcesRepositoryImpl @Inject constructor(private val onlineDataSource: SourcesDataSource) :
    SourcesRepository {
    override suspend fun getSources(category: String, country: String): List<Source?>? {
        return onlineDataSource.getSources(category = category, country= country)
    }

}