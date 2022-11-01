package com.kinandcarta.kincarta.articles.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinandcarta.domain.exception.Failure
import com.kinandcarta.domain.exception.Failure.NetworkConnection
import com.kinandcarta.domain.exception.Failure.ServerError
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.base.BaseFragment
import com.kinandcarta.kincarta.R
import com.kinandcarta.kincarta.articles.ui.viewmodel.ArticlesViewModel
import com.kinandcarta.kincarta.articles.ui.adapters.ArticlesAdapter
import com.kinandcarta.kincarta.base.extension.failure
import com.kinandcarta.kincarta.base.extension.invisible
import com.kinandcarta.kincarta.base.extension.observe
import com.kinandcarta.kincarta.base.extension.visible
import com.kinandcarta.kincarta.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_articles.*
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    private val articleViewModel: ArticlesViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_articles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(articleViewModel) {
            observe(articles, ::renderArticlesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadArticlesList()
    }


    private fun initializeView() {
        articleList.layoutManager = LinearLayoutManager(requireContext())
        articleList.adapter = articlesAdapter
        articlesAdapter.clickListener = { article, navigationExtras ->
            navigator.showArticleDetails(requireActivity(), article, navigationExtras)
        }
    }

    private fun loadArticlesList() {
        emptyView.invisible()
        emptyViewMsg.invisible()
        articleList.visible()
        showProgress()
        articleViewModel.loadArticles()
    }

    private fun renderArticlesList(caseStudies: List<CaseStudies>?) {
        articlesAdapter.collection = caseStudies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        articleList.invisible()
        emptyView.visible()
        emptyViewMsg.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadArticlesList)
    }
}
