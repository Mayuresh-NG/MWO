package com.example.mworkordercomponents.tabBar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mworkordercomponents.R
import com.example.mworkordercomponents.databinding.TabBarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A custom tab bar component for Android applications, based on BottomNavigationView.
 *
 * This component extends ConstraintLayout and provides a customizable bottom navigation bar with the following features:
 * - Default menu items for home, alerts, issues, work orders, and more options
 * - Custom icons for both selected and unselected states
 * - Ability to set a custom navigation item selection listener
 * - Option to reverse the order of menu items for right-to-left (RTL) layouts
 *
 * The TabBar is designed to provide consistent navigation across different sections of an application,
 * with easy customization of icons and behavior.
 */
class TabBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: TabBarBinding =
        TabBarBinding.inflate(LayoutInflater.from(context), this, true)
    private var isMenuReversed: Boolean = false
    private val bottomNavigationView = binding.bottomNavigation

    init {
        defaultMenu()
    }

    private fun defaultMenu() {
        bottomNavigationView.menu.findItem(R.id.home).icon =
            ContextCompat.getDrawable(context, R.drawable.tab_bar_dashboard)
        bottomNavigationView.menu.findItem(R.id.alert).icon =
            ContextCompat.getDrawable(context, R.drawable.tab_bar_alert)
        bottomNavigationView.menu.findItem(R.id.issues).icon =
            ContextCompat.getDrawable(context, R.drawable.tab_bar_issues)
        bottomNavigationView.menu.findItem(R.id.work_order).icon =
            ContextCompat.getDrawable(context, R.drawable.tab_bar_work_order)
        bottomNavigationView.menu.findItem(R.id.more).icon =
            ContextCompat.getDrawable(context, R.drawable.tab_bar_more)
    }

    /**
     * Sets a listener for navigation item selection events.
     *
     * This method allows custom handling of tab selection, including updating the icon
     * to its 'filled' state when selected.
     *
     * @param listener The OnNavigationItemSelectedListener to be notified of selection events.
     */
    fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener) {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            defaultMenu()

            when (menuItem.itemId) {
                R.id.home -> menuItem.icon =
                    ContextCompat.getDrawable(context, R.drawable.tab_bar_dashboard_fill)

                R.id.alert -> menuItem.icon =
                    ContextCompat.getDrawable(context, R.drawable.tab_bar_alert_fill)

                R.id.issues -> menuItem.icon =
                    ContextCompat.getDrawable(context, R.drawable.tab_bar_issues_fill)

                R.id.work_order -> menuItem.icon =
                    ContextCompat.getDrawable(context, R.drawable.tab_bar_work_order_fill)

                R.id.more -> menuItem.icon =
                    ContextCompat.getDrawable(context, R.drawable.tab_bar_more_fill)
            }

            listener.onNavigationItemSelected(menuItem)
            true
        }
    }

    /**
     * Reverses the order of menu items in the tab bar.
     *
     * This method is useful for supporting right-to-left (RTL) layouts. It reverses the order
     * of the menu items if they haven't been reversed already. This operation is idempotent.
     */
    fun reverseMenu() {
        if (!isMenuReversed) {
            val menu = bottomNavigationView.menu
            val items = mutableListOf<MenuItem>()

            for (i in 0 until menu.size()) {
                items.add(menu.getItem(i))
            }

            menu.clear()

            for (i in items.size - 1 downTo 0) {
                menu.add(0, items[i].itemId, 0, items[i].title).apply {
                    icon = items[i].icon
                    isEnabled = items[i].isEnabled
                    isChecked = items[i].isChecked
                }
            }

            isMenuReversed = true
            defaultMenu()
        }
    }
}
