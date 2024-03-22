package com.noureldin.news.dataRepos.dataSource.sources

import com.noureldin.news.dataRepos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.api.WebServices
import com.noureldin.news.api.sourcesModel.Source
import com.noureldin.news.database.MyDataBase
import javax.inject.Inject


class SourcesOnlineDataSourceImpl @Inject constructor(private val webServices: WebServices, val dataBase: MyDataBase) :
    SourcesDataSource {
    override suspend fun getSources(category: String, country:String): List<Source?>? {
        return webServices.getSources(category = category, country = country ).sources
    }
    override suspend fun saveSources(sourceList: List<Source>){
        dataBase.getSourcesDao().addSources(sourceList)
    }
}