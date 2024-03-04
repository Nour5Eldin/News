package com.noureldin.news.repository.sources

import com.noureldin.news.api.sourcesModel.Source


interface SourcesRepository {
    suspend fun getSources(category: String): List<Source?>?
}