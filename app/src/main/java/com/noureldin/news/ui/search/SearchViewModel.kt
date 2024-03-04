package com.noureldin.news.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.noureldin.news.api.ApiManager
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.api.articlesModel.ArticlesResponse
import com.noureldin.news.util.ViewError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    val articlesLiveData = MutableLiveData<List<Article?>?>()
    val errorLiveData = MutableLiveData<ViewError>()

    fun searchArticles(searchQuery: String) {
        ApiManager.getApis().getArticles(searchKeyWord = searchQuery).enqueue(object :
            Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    articlesLiveData.postValue(response.body()?.articles)

                } else {
                    val jsonString = response.errorBody()?.string()
                    val response = Gson().fromJson(jsonString, ArticlesResponse::class.java)

                    errorLiveData.postValue(ViewError(
                        response.message
                    ) {
                        searchArticles(searchQuery)
                    })

                }

            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {

                errorLiveData.postValue(ViewError(
                    t.localizedMessage
                ) {
                    searchArticles(searchQuery)
                })

            }

        })
    }

}