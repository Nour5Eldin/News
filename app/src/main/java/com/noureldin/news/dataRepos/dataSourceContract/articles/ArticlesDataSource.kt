package com.noureldin.news.dataRepos.dataSourceContract.articles

import com.noureldin.news.api.articlesModel.Article


interface ArticlesDataSource {

    suspend fun getArticles(source: String): List<Article?>?
    suspend fun getArticlesThatHas(searchKeyWord: String): List<Article?>?

}