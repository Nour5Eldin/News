package com.noureldin.news.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.api.articlesModel.ArticlesResponse
import com.noureldin.news.repository.articles.ArticlesRepository
import com.noureldin.news.util.ViewError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val articlesRepository: ArticlesRepository): ViewModel() {
    val articlesLiveData = MutableLiveData<List<Article?>?>()
    val errorLiveData = MutableLiveData<ViewError>()

    fun searchArticles(searchQuery: String) {
        viewModelScope.launch {
            try {
                val articles = articlesRepository.getArticlesThatHas(searchKeyWord = searchQuery)
                articlesLiveData.postValue(articles)
            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(jsonString, ArticlesResponse::class.java)

                errorLiveData.postValue(ViewError(
                    response.message
                ) {
                    searchArticles(searchQuery)
                })
            } catch (e: Exception) {
                errorLiveData.postValue(ViewError(
                    e.localizedMessage
                ) {
                    searchArticles(searchQuery)
                })
            }


        }
//        ApiManager.getApis().getArticles(searchKeyWord = searchQuery).enqueue(object :
//            Callback<ArticlesResponse> {
//            override fun onResponse(
//                call: Call<ArticlesResponse>,
//                response: Response<ArticlesResponse>
//            ) {
//                if (response.isSuccessful) {
//                    articlesLiveData.postValue(response.body()?.articles)
//
//                } else {
//                    val jsonString = response.errorBody()?.string()
//                    val response = Gson().fromJson(jsonString, ArticlesResponse::class.java)
//
//                    errorLiveData.postValue(ViewError(
//                        response.message
//                    ) {
//                        searchArticles(searchQuery)
//                    })
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
//
//                errorLiveData.postValue(ViewError(
//                    t.localizedMessage
//                ) {
//                    searchArticles(searchQuery)
//                })
//
//            }
//
//        })
    }

}