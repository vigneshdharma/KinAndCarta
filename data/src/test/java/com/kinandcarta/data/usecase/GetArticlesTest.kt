package com.kinandcarta.data.usecase

import com.kinandcarta.UnitTest
import com.kinandcarta.data.repository.ArticlesRepository
import com.kinandcarta.domain.interactor.Either
import com.kinandcarta.domain.interactor.UseCase
import com.kinandcarta.domain.model.Articles
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetArticlesTest: UnitTest() {

    private lateinit var getArticles: GetArticles

    @MockK
    private lateinit var articlesRepository: ArticlesRepository

    @Before
    fun setUp() {
        getArticles = GetArticles(articlesRepository)
        every { articlesRepository.articles() } returns Either.Right(Articles())
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getArticles.run(UseCase.None()) }

        verify(exactly = 1) { articlesRepository.articles() }
    }

}