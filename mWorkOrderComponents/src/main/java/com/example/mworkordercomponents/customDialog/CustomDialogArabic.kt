package com.example.mworkordercomponents.customDialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mworkordercomponents.databinding.CustomDialogViewArabicBinding

class CustomDialogArabic @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomDialogViewArabicBinding = CustomDialogViewArabicBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Sets the text of the dialog title.
     *
     * @param text The text to set as the dialog title.
     */
    fun setDialogTitleText(text: String) {
        binding.dialogTitle.text = text
    }

    /**
     * Sets the text of the dialog body.
     *
     * @param text The text to set as the dialog body.
     */
    fun setDialogBodyText(text: String) {
        binding.dialogBody.text = text
    }

    /**
     * Sets the text of the left button.
     *
     * @param text The text to set on the left button.
     */
    fun setLeftButtonText(text: String) {
        binding.leftButton.text = text
    }

    /**
     * Sets the text of the right button.
     *
     * @param text The text to set on the right button.
     */
    fun setRightButtonText(text: String) {
        binding.rightButton.text = text
    }

    /**
     * Sets a click listener for the left button.
     *
     * @param listener The listener to set on the left button.
     */
    fun setLeftButtonClickListener(listener: View.OnClickListener) {
        binding.leftButton.setOnClickListener(listener)
    }

    /**
     * Sets a click listener for the right button.
     *
     * @param listener The listener to set on the right button.
     */
    fun setRightButtonClickListener(listener: View.OnClickListener) {
        binding.rightButton.setOnClickListener(listener)
    }

    /**
     * Hides the icon and aligns the title text to the end (right alignment for Arabic).
     */
    fun hideIcon() {
        binding.dialogTitle.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        binding.iconContainer.visibility = View.GONE
    }
}
