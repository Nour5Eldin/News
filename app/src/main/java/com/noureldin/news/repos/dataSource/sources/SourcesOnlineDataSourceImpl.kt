package com.noureldin.news.repos.dataSource.sources

import com.noureldin.news.repos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.api.WebServices
import com.noureldin.news.api.sourcesModel.Source
import javax.inject.Inject


class SourcesOnlineDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    SourcesDataSource {
    override suspend fun getSources(category: String): List<Source?>? {
        return webServices.getSources(category = category).sources
    }
}