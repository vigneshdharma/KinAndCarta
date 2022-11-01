package com.kinandcarta.data.api

import com.kinandcarta.data.model.ArticlesEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface ArticlesApi {
    companion object {
        private const val CASE_STUDIES = "caseStudies.json"
    }

    @GET(CASE_STUDIES)
    fun articles(): Call<ArticlesEntity>
}
