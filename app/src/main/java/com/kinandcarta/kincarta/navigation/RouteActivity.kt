package com.kinandcarta.kincarta.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RouteActivity : AppCompatActivity() {

    @Inject
    internal lateinit var navigator: Navigator

    private var contentHasLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            contentHasLoaded = true
            navigator.showArticles(this)
        }, 2000)
        setupSplashScreen()
    }

    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}
