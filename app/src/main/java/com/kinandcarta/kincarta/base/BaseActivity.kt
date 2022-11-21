package com.kinandcarta.kincarta.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kinandcarta.kincarta.R
import com.kinandcarta.kincarta.base.extension.inTransaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) = savedInstanceState ?: supportFragmentManager.inTransaction {
        add(R.id.fragmentContainer, fragment())
    }

    abstract fun fragment(): BaseFragment
}
