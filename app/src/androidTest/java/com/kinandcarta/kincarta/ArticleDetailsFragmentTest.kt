package com.kinandcarta.kincarta

import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.articledetail.ArticleDetailsActivity
import com.kinandcarta.kincarta.articledetail.ui.fragments.ArticleDetailsFragment
import com.kinandcarta.kincarta.navigation.RouteActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ArticleDetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<ArticleDetailsFragment>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        scenario.moveToState(newState = Lifecycle.State.STARTED)
    }

    @Test
    fun test_DisplayArticle_WhenClickFromArticleList() {
        //Given
        launchActivity<ArticleDetailsActivity>()
        scenario.onFragment{
            //When
            it.renderArticleDetails(article())
        }
        //Then
        onView(withId(R.id.articlePoster)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.articleTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    private fun article(): CaseStudies {
        return CaseStudies(2,"test","test","test",false,"test","test", listOf())
    }
}