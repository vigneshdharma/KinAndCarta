package com.kinandcarta.kincarta.articles

import com.kinandcarta.data.usecase.GetArticles
import com.kinandcarta.domain.interactor.Either
import com.kinandcarta.domain.model.Articles
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.AndroidTest
import com.kinandcarta.kincarta.articles.ui.viewmodel.ArticlesViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ArticlesViewModelTest : AndroidTest() {

    private lateinit var articlesViewModel: ArticlesViewModel

    @MockK
    private lateinit var getArticles: GetArticles

    @Before
    fun setUp() {
        articlesViewModel = ArticlesViewModel(getArticles)
    }

    @Test
    fun `loading articles should update live data`() {
        val caseStudies = CaseStudies(
            0, "client", "teaser", "vertical",
            false, "Title", "url", mutableListOf()
        )
        val articles = arrayListOf(caseStudies)
        coEvery { getArticles.run(any()) } returns Either.Right(Articles(articles))

        articlesViewModel.articles.observeForever {
            it!!.size shouldBeEqualTo 2
            it[0].id?.shouldBeEqualTo(0)
            it[0].heroImage?.shouldBeEqualTo("url")
        }

        runBlocking { articlesViewModel.loadArticles() }
    }
}