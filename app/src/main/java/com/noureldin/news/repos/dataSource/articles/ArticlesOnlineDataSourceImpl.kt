package com.noureldin.news.repos.dataSource.articles



import com.noureldin.news.api.WebServices
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.repos.dataSourceContract.articles.ArticlesDataSource
import javax.inject.Inject


class ArticlesOnlineDataSourceImpl@Inject constructor(private val webServices: WebServices) :
    ArticlesDataSource {
    override suspend fun getArticles(source: String): List<Article?>? {
        return webServices.getArticles(source = source).articles
    }

    override suspend fun getArticlesThatHas(searcgKeyWord: String): List<Article?>? {
        return webServices.getArticles(searchKeyWord = searcgKeyWord).articles
    }


}