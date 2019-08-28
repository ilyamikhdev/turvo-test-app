package com.itechart.turvo.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.itechart.turvo.MainActivity

abstract class BaseFragment : Fragment() {
    private var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            val activity = context as MainActivity?
            this.activity = activity
        }
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    fun getBaseActivity(): MainActivity? = activity
}