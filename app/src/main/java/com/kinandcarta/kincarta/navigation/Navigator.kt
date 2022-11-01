package com.kinandcarta.kincarta.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.articledetail.ArticleDetailsActivity
import com.kinandcarta.kincarta.articles.ArticlesActivity
import com.kinandcarta.kincarta.authenticator.Authenticator
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    fun showArticles(context: Context) =
        context.startActivity(ArticlesActivity.callingIntent(context))

    fun showArticleDetails(
        activity: FragmentActivity,
        caseStudies: CaseStudies,
        navigationExtras: Extras
    ) {
        val intent = ArticleDetailsActivity.callingIntent(activity, caseStudies)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}


