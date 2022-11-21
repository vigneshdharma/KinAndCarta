package com.kinandcarta.kincarta

import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.articledetail.ArticleDetailsActivity
import com.kinandcarta.kincarta.articles.ArticlesActivity
import com.kinandcarta.kincarta.articles.ui.fragments.ArticlesFragment
import com.kinandcarta.kincarta.articles.ui.viewmodel.ArticlesViewModel
import com.kinandcarta.kincarta.navigation.RouteActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ArticlesFragmentTest {

    private lateinit var scenario: FragmentScenario<ArticlesFragment>

    @BindValue
    @JvmField
    val articleViewModel = mockk<ArticlesViewModel>(relaxed = true)

    private val articles = MutableLiveData<List<CaseStudies>>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        every { articleViewModel.articles } returns articles
        scenario.moveToState(newState = Lifecycle.State.STARTED)
    }

    @Test
    fun test_DisplayEmptyView_WhenArticlesLiveData_Is_Empty() {
        //Given
        launchActivity<ArticlesActivity>()
        scenario.onFragment{
            articleViewModel.loadArticles()
            it.renderArticlesList(listOf())
        }
        //When
        articles.postValue(listOf())
        //Then
        onView(withId(R.id.emptyView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.emptyViewMsg)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.articleList)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun test_DisplayArticleList_WhenArticlesLiveData_Is_NotEmpty() {
        //Given
        launchActivity<ArticlesActivity>()
        scenario.onFragment{
            articleViewModel.loadArticles()
            it.renderArticlesList(articles())
        }
        //When
        articles.postValue(articles())
        //Then
        onView(withId(R.id.articleList)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.emptyView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.emptyViewMsg)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    private fun articles(): List<CaseStudies> {
        return listOf(CaseStudies(2,"test","test","test",false,"test","test", listOf()))
    }
}