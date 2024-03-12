package com.noureldin.news.dataRepos.dataSourceContract.sources

import com.noureldin.news.api.sourcesModel.Source


interface SourcesDataSource {
    suspend fun getSources(category: String, country:String): List<Source?>?
}