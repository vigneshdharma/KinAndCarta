package com.kinandcarta.data.repository

import com.kinandcarta.data.api.ArticlesService
import com.kinandcarta.data.api.NetworkHandler
import com.kinandcarta.data.model.ArticlesEntity
import com.kinandcarta.domain.exception.Failure
import com.kinandcarta.domain.interactor.Either
import com.kinandcarta.domain.model.Articles
import retrofit2.Call
import javax.inject.Inject

interface ArticlesRepository {

    fun articles(): Either<Failure, Articles>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private val service: ArticlesService) : ArticlesRepository {

        override fun articles(): Either<Failure, Articles> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.articles(), { articlesEntity: ArticlesEntity -> articlesEntity.toArticle() }, ArticlesEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }

        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                exception.printStackTrace()
                Either.Left(Failure.ServerError)
            }
        }
    }
}

