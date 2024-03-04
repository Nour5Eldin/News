package com.noureldin.news.repos.dataSourceContract

import com.noureldin.news.repos.dataSourceContract.sources.SourcesDataSource
import com.noureldin.news.repos.dataSource.articles.ArticlesOnlineDataSourceImpl
import com.noureldin.news.repos.dataSource.sources.SourcesOnlineDataSourceImpl
import com.noureldin.news.repos.dataSourceContract.articles.ArticlesDataSource
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