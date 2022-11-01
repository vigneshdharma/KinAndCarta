package com.kinandcarta.kincarta.base.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kinandcarta.kincarta.base.BaseActivity
import com.kinandcarta.kincarta.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
