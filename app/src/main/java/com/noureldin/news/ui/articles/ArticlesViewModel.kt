package com.noureldin.news.ui.articles

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.api.articlesModel.ArticlesResponse
import com.noureldin.news.api.sourcesModel.Source
import com.noureldin.news.api.sourcesModel.SourcesResponse
import com.noureldin.news.repository.articles.ArticlesRepository
import com.noureldin.news.repository.sources.SourcesRepository
import com.noureldin.news.util.ViewError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel  @Inject constructor(
    private val sourcesRepository: SourcesRepository,
    private val articlesRepository: ArticlesRepository
): ViewModel() {
    val shouldLoad = MutableLiveData<Boolean>(true)
    val articlesList = MutableLiveData<List<Article?>?>()
    val sourcesList = MutableLiveData<List<Source?>?>()
    val errorLiveData = MutableLiveData<ViewError>()
    val shouldDisplayNoArticlesFound = MutableLiveData<Boolean>(false)

    fun getArticles(source: String) {
        shouldLoad.postValue(true)
        viewModelScope.launch {
            try {
                val articles = articlesRepository.getArticles(source = source)
                articlesList.postValue(articles)
                Log.e("ViewModel", "Error fetching sources: $articles")
                if (articles?.isEmpty() == true)
                    shouldDisplayNoArticlesFound.postValue(true)
                else
                    shouldDisplayNoArticlesFound.postValue(false)
            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                Log.e("ViewModel", "Error fetching sources: $jsonString")
                val response = Gson().fromJson(jsonString, ArticlesResponse::class.java)
                errorLiveData.postValue(ViewError(
                    response.message
                ) {
                    getArticles(source)
                })
            } catch (e: Exception) {
                errorLiveData.postValue(ViewError(e.localizedMessage) {
                    getArticles(source)
                })
            } finally {
                shouldLoad.postValue(false)
            }

        }
//        ApiManager
//            .getApis().getArticles(source = source)
//            .enqueue(object : Callback<ArticlesResponse> {
//                override fun onResponse(
//                    call: Call<ArticlesResponse>,
//                    response: Response<ArticlesResponse>
//                ) {
//                    shouldLoad.postValue(false)
//                    if (response.isSuccessful) {
//                        articlesList.postValue(response.body()?.articles)
//
//                        if (response.body()?.articles?.isEmpty() == true)
//                            shouldDisplayNoArticlesFound.postValue(true)
//                        else
//                            shouldDisplayNoArticlesFound.postValue(false)
//
//                    } else {
//                        val jsonString = response.errorBody()?.string()
//                        val response = Gson().fromJson(jsonString, ArticlesResponse::class.java)
//                        errorLiveData.postValue(ViewError(
//                            response.message
//                        ) {
//                            getArticles(source)
//                        })
//                    }
//
//                }
//
//                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
//                    shouldLoad.postValue(false)
//                    errorLiveData.postValue(ViewError(t.localizedMessage) {
//                        getArticles(source)
//                    })
//
//                }
//
//            })
    }


    fun getSources(category: String,country: String) {
        shouldLoad.postValue(true)
        viewModelScope.launch {
            try {
                val sources = sourcesRepository.getSources(category = category, country = country )
                sourcesList.postValue(sources)
                Log.d("ViewModel", "Sources fetched successfully: $sources")
            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                Log.e("ViewModel", "Error fetching sources: $jsonString")
                val response = Gson().fromJson(jsonString, SourcesResponse::class.java)
                errorLiveData.postValue(ViewError(response.message) {
                    getSources(category,country)

                })
            } catch (e: Exception) {
                errorLiveData.postValue(ViewError(e.localizedMessage) {
                    getSources(category,country)
                })
            } finally {
                shouldLoad.postValue(false)

            }

        }

//        ApiManager.getApis().getSources(category = category, country = country)
//            .enqueue(object : Callback<SourcesResponse> {
//                override fun onResponse(
//                    call: Call<SourcesResponse>,
//                    response: Response<SourcesResponse>
//                ) {
//                    shouldLoad.postValue(false)
//                    if (response.isSuccessful) {
//                        sourcesList.postValue(response.body()?.sources)
//                    } else {
//                        val jsonString = response.errorBody()?.string()
//                        val response = Gson().fromJson(jsonString, SourcesResponse::class.java)
//                        errorLiveData.postValue(ViewError(response.message) {
//                            getSources(category,country)
//
//                        })
//
//                    }
//
//                }
//
//                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//                    shouldLoad.postValue(false)
//                    errorLiveData.postValue(ViewError(t.localizedMessage) {
//                        getSources(category,country)
//                    })
//                }
//
//            })
    }
}