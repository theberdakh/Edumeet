package com.imax.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object NavigationExtensions {


    fun FragmentManager.addFragment(
        @IdRes navHostViewId: Int,
        newFragment: Fragment,
        tag: String = newFragment::class.java.simpleName) {

        this.beginTransaction()
            .add(navHostViewId, newFragment, tag)
            .commit()
    }

    fun FragmentManager.addFragmentToBackStack(
        @IdRes fragmentContainerId: Int,
        fragment: Fragment,
        tag: String = fragment::class.java.simpleName,
    ) {
        val fragmentPopped = popBackStackImmediate(tag, 0)
        if (!fragmentPopped && findFragmentByTag(tag) == null) {
            beginTransaction()
                .setReorderingAllowed(true) // Optimize state changes
                .replace(fragmentContainerId, fragment, tag) // Use replace instead of add
                .addToBackStack(tag)
                .commit()
        }
    }

    fun FragmentManager.replaceFragment(
        @IdRes navHostViewId: Int,
        newFragment: Fragment,
        tag: String = newFragment::class.java.simpleName,

    ) {
        this.beginTransaction()
            .replace(navHostViewId, newFragment, tag)
            .commit()
    }
}
