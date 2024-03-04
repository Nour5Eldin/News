package com.noureldin.news.repository.articles

import com.noureldin.news.api.articlesModel.Article


interface ArticlesRepository {
    suspend fun getArticles(source: String): List<Article?>?
    suspend fun getArticlesThatHas(searchKeyWord: String): List<Article?>?

}