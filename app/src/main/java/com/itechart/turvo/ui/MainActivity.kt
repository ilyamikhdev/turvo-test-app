package com.itechart.turvo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.DefaultTransition
import com.itechart.turvo.helper.Transaction
import com.itechart.turvo.helper.addSharedElementTransition
import com.itechart.turvo.helper.changeTo
import com.itechart.turvo.ui.details.DetailsFragment
import com.itechart.turvo.ui.list.ListFragment
import com.itechart.turvo.ui.main.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    fun getContentId() = R.id.container
    fun getToolbar(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(getToolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            changeTo(MainFragment.newInstance(), transaction = Transaction.SLIDE_END_WITHOUT_BACK)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun navigateToList(data: String) {
        changeTo(
            ListFragment.newInstance(data),
            transaction = Transaction.SLIDE_END,
            withBack = true
        )
    }

    fun navigateToDetails(data: Ticker, sharedViews: Map<String, View>, fromFragment: Fragment) {
        val fragment = DetailsFragment.newInstance(data).addSharedElementTransition(
            DefaultTransition(), Fade(), fromFragment
        )
        changeTo(
            fragment = fragment,
            withBack = true,
            transaction = Transaction.NONE,
            sharedViews = sharedViews
        )
    }


    fun hideKeyboard() {
        this.currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
