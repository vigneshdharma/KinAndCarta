package com.kinandcarta.kincarta.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kinandcarta.kincarta.articles.ui.fragments.ArticlesFragment
import com.kinandcarta.kincarta.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class ArticlesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
    }

    private fun initToolBar(){
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun callingIntent(context: Context) = Intent(context, ArticlesActivity::class.java)
    }

    override fun fragment() = ArticlesFragment()
}
