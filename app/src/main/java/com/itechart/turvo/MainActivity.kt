package com.itechart.turvo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itechart.turvo.helper.Transaction
import com.itechart.turvo.helper.changeTo
import com.itechart.turvo.ui.list.ListFragment
import com.itechart.turvo.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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

    fun getContentId() = R.id.container
}
