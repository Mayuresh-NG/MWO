package com.example.mworkordercomponents.iconButtons

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mworkordercomponents.R
import com.example.mworkordercomponents.databinding.IconButtonBinding

/**
 * A customizable icon button component for Android applications.
 *
 * This component extends ConstraintLayout and provides a flexible icon button with
 * configurable appearance and behavior. Key features include:
 * - Customizable button type (warning, primary, or default)
 * - Adjustable size
 * - Configurable click listener
 * - Dynamic background color based on the button type
 *
 * The IconButton is designed to be easily integrated into various UI contexts,
 * offering a consistent yet adaptable button style across different parts of an application.
 */
class IconButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: IconButtonBinding =
        IconButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private val primaryColor = R.color.primary
    private val warningColor = R.color.warning
    private val defaultColor = R.color.defaultColor

    /**
     * Configures the IconButton with the specified type, size, and click listener.
     *
     * @param type The type of the button appearance. Accepted values are:
     * - "warning": Sets the play button image to `play_button_warning` and changes the background color to a light red (#FAE0E3).
     * - "primary": Sets the play button image to `play_button_primary` and changes the background color to a light blue (#EBE9FB).
     * - any other value: Sets the play button image to `play_button_default` and changes the background color to a light gray (#EDEEF0).
     * @param size The size (width and height) of the play button in dp.
     * @param onClickListener The click listener to be set on the play button.
     */
    fun configureIcon(type: String, size: Int, onClickListener: View.OnClickListener) {
        when (type) {
            "warning" -> {
                binding.playButton.setImageResource(R.drawable.icon_button_play_button_warning)
                changeShapeColor(warningColor)
            }
            "primary" -> {
                binding.playButton.setImageResource(R.drawable.icon_button_play_button_primary)
                changeShapeColor(primaryColor)
            }
            else -> {
                binding.playButton.setImageResource(R.drawable.icon_button_play_button_default)
                changeShapeColor(defaultColor)
            }
        }
        setSize(size)
        binding.playButton.setOnClickListener(onClickListener)
    }

    private fun setSize(size: Int) {
        val layoutParams = binding.playButton.layoutParams
        layoutParams.width = size
        layoutParams.height = size
        binding.playButton.layoutParams = layoutParams
    }

    private fun changeShapeColor(colorResId: Int) {
        val color = ContextCompat.getColor(context, colorResId)
        val drawable = binding.playButton.background as StateListDrawable
        drawable.setTint(color)
    }

    fun getPlayButton(): ImageView {
        return binding.playButton
    }
}
