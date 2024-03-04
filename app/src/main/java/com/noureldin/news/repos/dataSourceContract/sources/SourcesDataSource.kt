package com.noureldin.news.repos.dataSourceContract.sources

import com.noureldin.news.api.sourcesModel.Source


interface SourcesDataSource {
    suspend fun getSources(category: String): List<Source?>?
}