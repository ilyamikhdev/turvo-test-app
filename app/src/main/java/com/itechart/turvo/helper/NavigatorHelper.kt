package com.itechart.turvo.helper

import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.itechart.turvo.MainActivity
import com.itechart.turvo.R
import com.itechart.turvo.ui.BaseFragment

/**
 * @param sharedViews View in the first Fragment you want to “share” with the second Fragment. The transition name here is the transition name of the “shared” View in the second Fragment.
 * */
fun MainActivity.changeTo(
    fragment: Fragment,
    withBack: Boolean = false,
    tag: String? = null,
    transaction: Transaction? = null,
    sharedViews: Map<String, View> = emptyMap(),
    clearStack: Boolean = !withBack,
    reorderingEnabled: Boolean = false
) {
    let {
        val containerId: Int = it.getContentId()
        val fragmentManager = it.supportFragmentManager
        if (clearStack && !fragmentManager.isStateSaved) {
            fragmentManager.popBackStack(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        when (transaction) {
            Transaction.SLIDE_END -> fragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter_from_right,
                R.anim.fragment_exit_to_left,
                R.anim.fragment_enter_from_left,
                R.anim.fragment_exit_to_right
            )
            Transaction.SLIDE_UP -> fragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter_from_bottom,
                R.anim.fragment_exit_to_top,
                R.anim.fragment_enter_from_top,
                R.anim.fragment_exit_to_bottom
            )
            Transaction.SLIDE_END_WITHOUT_BACK -> fragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter_from_right,
                R.anim.fragment_exit_to_left,
                R.anim.fragment_enter_from_left,
                R.anim.fragment_exit_to_left
            )
            Transaction.NONE -> fragmentTransaction.setCustomAnimations(0, 0, 0, 0)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            sharedViews.forEach { (transitionName, sharedView) ->
                fragmentTransaction.addSharedElement(
                    sharedView,
                    transitionName
                )
            }
        }

        if (reorderingEnabled) {
            //so postponeEnterTransition() inside fragment can work
            fragmentTransaction.setReorderingAllowed(true)
        }
        fragmentTransaction.replace(containerId, fragment, tag)

        if (withBack) {
            fragmentTransaction.addToBackStack(tag)
        }

        fragmentTransaction.commitAllowingStateLoss()
    }
}

/**
 * @param allowOverlap is `true` by default in the android sdk.
 * */
fun BaseFragment.addSharedElementTransition(
    sharedElementTransition: TransitionSet,
    fragmentEnterTransition: Any? = null,
    fromFragment: Fragment? = null,
    allowOverlap: Boolean = true
): BaseFragment {

    fromFragment?.let {
        fragmentEnterTransition?.let { transition ->
            it.exitTransition = transition
        }
        it.sharedElementReturnTransition = sharedElementTransition
        it.allowEnterTransitionOverlap = allowOverlap
    }


    fragmentEnterTransition?.let {
        this.enterTransition = it
    }
    this.sharedElementEnterTransition = sharedElementTransition
    this.sharedElementReturnTransition = sharedElementTransition
    this.allowEnterTransitionOverlap = allowOverlap

    return this
}

class DefaultTransition : TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
    }
}

enum class Transaction {
    SLIDE_END,
    SLIDE_UP,
    NONE,
    SLIDE_END_WITHOUT_BACK
}