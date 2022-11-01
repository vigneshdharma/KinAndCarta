package com.kinandcarta.kincarta.navigation

import com.kinandcarta.kincarta.AndroidTest
import com.kinandcarta.kincarta.articles.ArticlesActivity
import com.kinandcarta.kincarta.shouldNavigateTo
import com.kinandcarta.kincarta.authenticator.Authenticator
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test


class NavigatorTest: AndroidTest() {

    private lateinit var navigator: Navigator

    @MockK
    private lateinit var authenticator: Authenticator

    @Before
    fun setup() {
        navigator = Navigator(authenticator)
    }

    @Test
    fun `should forward user to article screen`() {
        every { authenticator.userLoggedIn() } returns false

        navigator.showArticles(context())

        RouteActivity::class shouldNavigateTo ArticlesActivity::class
    }
}