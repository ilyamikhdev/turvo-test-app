package com.itechart.turvo.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.itechart.turvo.R
import com.itechart.turvo.helper.Transaction
import com.itechart.turvo.helper.changeTo
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            changeTo(MainFragment.newInstance(), transaction = Transaction.SLIDE_UP)
        } else {
            super.onBackPressed()
        }
    }

    fun navigateToList(data: String) {
        changeTo(
            ListFragment.newInstance(data),
            transaction = Transaction.SLIDE_END,
            withBack = true
        )
    }

    fun hideKeyboard() {
        this.currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
