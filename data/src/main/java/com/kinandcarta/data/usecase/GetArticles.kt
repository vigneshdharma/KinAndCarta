package com.kinandcarta.data.usecase

import com.kinandcarta.domain.exception.Failure
import com.kinandcarta.data.repository.ArticlesRepository
import com.kinandcarta.domain.interactor.Either
import com.kinandcarta.domain.interactor.UseCase
import com.kinandcarta.domain.model.Articles
import javax.inject.Inject

class GetArticles @Inject constructor(private val articlesRepository: ArticlesRepository):
    UseCase<Articles, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, Articles>  = articlesRepository.articles()
}