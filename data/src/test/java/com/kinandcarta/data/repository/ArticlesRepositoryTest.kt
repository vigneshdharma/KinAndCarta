package com.kinandcarta.data.repository


import com.kinandcarta.UnitTest
import com.kinandcarta.data.api.ArticlesService
import com.kinandcarta.data.api.NetworkHandler
import com.kinandcarta.data.model.ArticlesEntity
import com.kinandcarta.data.model.CaseStudiesEntity
import com.kinandcarta.domain.exception.Failure
import com.kinandcarta.domain.interactor.Either
import com.kinandcarta.domain.model.Articles
import com.kinandcarta.domain.model.CaseStudies
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ArticlesRepositoryTest : UnitTest() {

    private lateinit var networkRepository: ArticlesRepository.Network

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: ArticlesService

    @MockK
    private lateinit var articleCall: Call<ArticlesEntity>
    @MockK
    private lateinit var articleResponse: Response<ArticlesEntity>

    @Before
    fun setUp() {
        networkRepository = ArticlesRepository.Network(networkHandler, service)
    }

    @Test
    fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { articleResponse.body() } returns null
        every { articleResponse.isSuccessful } returns true
        every { articleCall.execute() } returns articleResponse
        every { service.articles() } returns articleCall

        val articles = networkRepository.articles()

        articles shouldBeEqualTo Either.Right(Articles(arrayListOf()))
        verify(exactly = 1) { service.articles() }
    }

    @Test
    fun `should get articles`() {

        val caseStudiesEntity = CaseStudiesEntity(
            0, "client", "teaser", "vertical",
            false, "Title", "url", mutableListOf()
        )
        val articlesEntity = ArticlesEntity(arrayListOf(caseStudiesEntity))
        every { networkHandler.isNetworkAvailable() } returns true
        every { articleResponse.body() } returns articlesEntity
        every { articleResponse.isSuccessful } returns true
        every { articleCall.execute() } returns articleResponse
        every { service.articles() } returns articleCall

        val articles = networkRepository.articles()

        val caseStudies = CaseStudies(
            0, "client", "teaser", "vertical",
            false, "Title", "url", mutableListOf()
        )
        val list = arrayListOf(caseStudies)

        articles shouldBeEqualTo Either.Right(Articles(list))
        verify(exactly = 1) { service.articles() }
    }


    @Test fun `articles service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val articles = networkRepository.articles()

        articles shouldBeInstanceOf Either::class.java
        articles.isLeft shouldBeEqualTo true
        articles.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verify { service wasNot Called }
    }

    @Test fun `articles service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { articleResponse.isSuccessful } returns false
        every { articleCall.execute() } returns articleResponse
        every { service.articles() } returns articleCall

        val articles = networkRepository.articles()

        articles shouldBeInstanceOf Either::class.java
        articles.isLeft shouldBeEqualTo true
        articles.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test fun `articles request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { articleCall.execute() } returns articleResponse
        every { service.articles() } returns articleCall

        val articles = networkRepository.articles()

        articles shouldBeInstanceOf Either::class.java
        articles.isLeft shouldBeEqualTo true
        articles.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

}