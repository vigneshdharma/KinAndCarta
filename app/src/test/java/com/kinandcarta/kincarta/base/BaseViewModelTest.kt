package com.kinandcarta.kincarta.base

import androidx.lifecycle.MutableLiveData
import com.kinandcarta.domain.exception.Failure
import com.kinandcarta.kincarta.AndroidTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class BaseViewModelTest: AndroidTest() {
    @Test
    fun `should handle failure by updating live data`() {
        val viewModel = MyViewModel()

        viewModel.handleError(Failure.NetworkConnection)

        val failure = viewModel.failure
        val error = viewModel.failure.value

        failure shouldBeInstanceOf MutableLiveData::class.java
        error shouldBeInstanceOf Failure.NetworkConnection::class.java
    }

    private class MyViewModel : BaseViewModel() {
        fun handleError(failure: Failure) = handleFailure(failure)
    }
}