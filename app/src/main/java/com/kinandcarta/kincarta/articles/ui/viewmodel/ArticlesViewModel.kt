package com.kinandcarta.kincarta.articles.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kinandcarta.data.usecase.GetArticles
import com.kinandcarta.domain.interactor.UseCase
import com.kinandcarta.domain.model.Articles
import com.kinandcarta.domain.model.CaseStudies
import com.kinandcarta.kincarta.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel
@Inject constructor(private val getArticles: GetArticles) : BaseViewModel() {

    private val _articles: MutableLiveData<List<CaseStudies>> = MutableLiveData()
    val articles: LiveData<List<CaseStudies>> = _articles

    fun loadArticles() =
        getArticles(UseCase.None(), viewModelScope) { it.fold(::handleFailure, ::handleArticleList) }

    private fun handleArticleList(articles: Articles) {
        _articles.value = articles.caseStudies.filterNotNull()
    }
}
