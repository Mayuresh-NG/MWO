package com.example.mworkordercomponents.topAppBar

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mworkordercomponents.R
import com.example.mworkordercomponents.databinding.TopAppBarBinding
import com.google.android.material.appbar.MaterialToolbar

/**
 * A custom view that extends [ConstraintLayout] and provides a top app bar with support
 * for light and dark modes.
 */
class TopAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: TopAppBarBinding =
        TopAppBarBinding.inflate(LayoutInflater.from(context), this, true)

    val toolbar: MaterialToolbar = binding.toolbar

    init {
        val nightModeFlags =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            setDarkModeColors()
        } else {
            setLightModeColors()
        }
    }

    /**
     * Sets the colors for dark mode.
     */
    private fun setDarkModeColors() {
        toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.top_app_bar_leading_icon_dark)
        toolbar.menu.findItem(R.id.edit)?.icon =
            ContextCompat.getDrawable(context, R.drawable.top_app_bar_play_button_dark)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    /**
     * Sets the colors for light mode.
     */
    private fun setLightModeColors() {
        toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.top_app_bar_leading_icon)
        toolbar.menu.findItem(R.id.edit)?.icon =
            ContextCompat.getDrawable(context, R.drawable.top_app_bar_play_button_default)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    /**
     * Sets the title of the toolbar.
     *
     * @param title The title to be set on the toolbar.
     */
    fun setTitle(title: String) {
        toolbar.title = title
    }

    /**
     * Sets a click listener for the navigation icon.
     *
     * @param listener The click listener for the navigation icon.
     */
    fun setNavigationClickListener(listener: OnClickListener) {
        toolbar.setNavigationOnClickListener(listener)
    }

    /**
     * Sets a click listener for the play button in the toolbar menu.
     *
     * @param listener The click listener for the play button.
     */
    fun setPlayButtonClickListener(listener: OnClickListener) {
        val playButton = toolbar.menu.findItem(R.id.edit)
        playButton?.setOnMenuItemClickListener { item ->
            listener.onClick(item.actionView)
            true
        }
    }

    /**
     * Makes the toolbar background transparent.
     */
    fun makeTransparent() {
        toolbar.background = null
    }

    /**
     * Aligns the toolbar to the right.
     */
    fun alignRight() {
        toolbar.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }
}
