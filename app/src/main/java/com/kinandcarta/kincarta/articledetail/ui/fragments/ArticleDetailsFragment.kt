package com.kinandcarta.kincarta.articledetail.ui.fragments

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.R
import com.kinandcarta.kincarta.articledetail.ui.util.ArticleDetailsAnimator
import com.kinandcarta.kincarta.base.BaseFragment
import com.kinandcarta.kincarta.base.extension.loadFromUrl
import com.kinandcarta.kincarta.base.extension.loadUrlAndPostponeEnterTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


@AndroidEntryPoint
class ArticleDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_ARTICLE = "param_article"

        fun forArticle(caseStudies: CaseStudies?) = ArticleDetailsFragment().apply {
            arguments = bundleOf(PARAM_ARTICLE to caseStudies)
        }
    }

    @Inject
    lateinit var articleDetailsAnimator: ArticleDetailsAnimator

    override fun layoutId() = R.layout.fragment_article_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { articleDetailsAnimator.postponeEnterTransition(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val caseStudies = (arguments?.get(PARAM_ARTICLE) as CaseStudies)
        renderArticleDetails(caseStudies)
        articleDetailsAnimator.cancelTransition(articlePoster)
    }

    override fun onBackPressed() {
        articleDetailsAnimator.fadeInvisible(scrollView, articleDetailsContainer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderArticleDetails(caseStudies: CaseStudies?) {
        articlePoster.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        caseStudies?.let { studies ->
            with(studies) {
                activity?.let {
                    heroImage?.let { url ->
                        articlePoster.loadUrlAndPostponeEnterTransition(
                            url,
                            it
                        )
                    }
                    it.toolbar.title = title
                }
                articleTitle.text = studies.title

                sections.forEach { section ->

                    section.body_elements.forEach {
                        if ((it is String)) {
                            val text = TextView(requireContext())
                            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                            )
                            params.setMargins(20, 10, 20, 10)
                            text.layoutParams = params
                            text.textSize = 16f
                            text.text = it
                            text.setTextColor(
                                ContextCompat.getColor(requireContext(), R.color.colorTextSecondary)
                            )
                            if (Build.VERSION.SDK_INT >= 29) {
                                text.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                            }
                            text.setPadding(8, 8, 8, 8)
                            articleDetailsContainer.addView(text)

                        } else if (it is LinkedHashMap<*, *>) {
                            val imageUrl = it["image_url"] as String
                            val image = ImageView(requireContext())
                            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            params.setMargins(0,10,0,10)
                            image.layoutParams = params
                            image.scaleType = ImageView.ScaleType.FIT_XY
                            image.loadFromUrl(imageUrl)
                            articleDetailsContainer.addView(image)
                        }
                    }
                }
            }
            articleDetailsAnimator.fadeVisible(scrollView, articleDetailsContainer)
        }
    }
}