package com.noureldin.news.repos.repository

import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.repos.dataSourceContract.articles.ArticlesDataSource
import com.noureldin.news.repository.articles.ArticlesRepository
import javax.inject.Inject


class ArticlesRepositoryImp @Inject constructor(private val dataSource: ArticlesDataSource) :
    ArticlesRepository {
    override suspend fun getArticles(source: String): List<Article?>? {
        return dataSource.getArticles(source = source)
    }

    override suspend fun getArticlesThatHas(searchKeyWord: String): List<Article?>? {
        return dataSource.getArticlesThatHas(searchKeyWord = searchKeyWord)
    }

}