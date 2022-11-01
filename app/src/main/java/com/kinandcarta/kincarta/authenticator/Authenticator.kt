package com.kinandcarta.kincarta.authenticator

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor() {
    fun userLoggedIn() = true
}
