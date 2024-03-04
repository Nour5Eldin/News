package com.noureldin.news.dataRepos.dataSourceContract

import com.noureldin.news.dataRepos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.dataRepos.dataSource.articles.ArticlesOnlineDataSourceImpl
import com.noureldin.news.dataRepos.dataSource.sources.SourcesOnlineDataSourceImpl
import com.noureldin.news.dataRepos.dataSourceContract.articles.ArticlesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindSourcesDataSource(sourcesOnlineDataSourceImpl: SourcesOnlineDataSourceImpl):
            SourcesDataSource

    @Binds
    abstract fun bindArticlesDataSource(articlesOnlineDataSourceImpl: ArticlesOnlineDataSourceImpl):
            ArticlesDataSource
}