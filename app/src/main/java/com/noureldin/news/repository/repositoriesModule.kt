package com.noureldin.news.repository


import com.noureldin.news.repository.articles.ArticlesRepository
import com.noureldin.news.repository.sources.SourcesRepository
import com.noureldin.news.dataRepos.repository.ArticlesRepositoryImp
import com.noureldin.news.dataRepos.repository.SourcesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class repositoriesModule {

    @Binds
    abstract fun bindSourcesRepository(sourcesRepositoryImpl: SourcesRepositoryImpl):
            SourcesRepository

    @Binds
    abstract fun bindArticlesRepository(articlesRepositoryImp: ArticlesRepositoryImp):
            ArticlesRepository
}