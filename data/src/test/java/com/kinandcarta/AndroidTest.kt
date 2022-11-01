package com.kinandcarta

import android.app.Application
import android.content.Context
import android.os.Build
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    application = AndroidTest.ApplicationStub::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1]
)
abstract class AndroidTest {
    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@AndroidTest)

    fun context(): Context = RuntimeEnvironment.systemContext

    internal class ApplicationStub : Application()
}
