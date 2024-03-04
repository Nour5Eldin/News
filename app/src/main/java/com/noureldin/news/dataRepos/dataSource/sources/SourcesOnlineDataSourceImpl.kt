package com.noureldin.news.dataRepos.dataSource.sources

import com.noureldin.news.dataRepos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.api.WebServices
import com.noureldin.news.api.sourcesModel.Source
import javax.inject.Inject


class SourcesOnlineDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    SourcesDataSource {
    override suspend fun getSources(category: String): List<Source?>? {
        return webServices.getSources(category = category).sources
    }
}