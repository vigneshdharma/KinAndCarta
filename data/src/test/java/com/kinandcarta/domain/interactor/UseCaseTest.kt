package com.kinandcarta.domain.interactor

import com.kinandcarta.AndroidTest
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class UseCaseTest: AndroidTest(){
    private val useCase = MyUseCase()

    @Test
    fun `running use case should return 'Either' of use case type`() {
        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }

        result shouldBeEqualTo Either.Right(MyType(TYPE_TEST))
    }

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>() {
        override suspend fun run(params: MyParams) = Either.Right(MyType(TYPE_TEST))
    }

    companion object {
        private const val TYPE_TEST = "Test"
        private const val TYPE_PARAM = "ParamTest"
    }
}