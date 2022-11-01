package com.kinandcarta.data.api

import com.kinandcarta.data.model.ArticlesEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesService
@Inject constructor(retrofit: Retrofit) : ArticlesApi {
    private val articlesApi by lazy { retrofit.create(ArticlesApi::class.java) }
    override fun articles(): Call<ArticlesEntity> = articlesApi.articles()
}
