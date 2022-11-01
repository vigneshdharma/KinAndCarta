package com.kinandcarta.kincarta.articledetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.articledetail.ui.fragments.ArticleDetailsFragment
import com.kinandcarta.kincarta.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class ArticleDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
    }

    private fun initToolBar(){
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val INTENT_EXTRA_PARAM_ARTICLE = "article"

        fun callingIntent(context: Context, caseStudies: CaseStudies) =
            Intent(context, ArticleDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_ARTICLE, caseStudies)
            }
    }


    override fun fragment() =
        ArticleDetailsFragment.forArticle(intent.getSerializableExtra(INTENT_EXTRA_PARAM_ARTICLE) as? CaseStudies)
}
