package com.example.mworkordercomponents.customTextField

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mworkordercomponents.R
import com.example.mworkordercomponents.databinding.CustomTextFieldBinding

/**
 * A custom text field component for Android that provides enhanced functionality and customization options.
 *
 * This component extends [ConstraintLayout] and includes features such as:
 * - Customizable hint and supporting text
 * - Character count display
 * - Leading and trailing icons
 * - Error state handling
 * - Right-to-left (RTL) support
 * - Dropdown functionality
 *
 * The component can be customized using the [Builder] class for a fluent API.
 *
 * @property editText The main EditText of the component.
 * @property supportingText A TextView for displaying additional information below the input field.
 * @property charCount A TextView for displaying the character count.
 * @property leadingIcon An ImageView for the leading icon.
 * @property trailingIcon An ImageView for the trailing icon.
 */
class CustomTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomTextFieldBinding =
        CustomTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    var editText = binding.editText
    val supportingText = binding.supportingText
    val charCount = binding.charCount
    val leadingIcon = binding.leadingIcon
    val trailingIcon = binding.trailingIcon
    var dropdownPopup: PopupWindow? = null
    private var isError = false
    private var maxLength = Int.MAX_VALUE

    init {
        charCount.visibility = View.GONE
    }

    fun setSupportingText(text: String) {
        supportingText.text = text
    }

    fun setHintText(hint: String) {
        editText.hint = hint
    }

    fun showLeadingIcon() {
        leadingIcon.visibility = View.VISIBLE
        editText.setPadding(
            resources.getDimensionPixelSize(R.dimen.leading_icon_padding),
            editText.paddingTop,
            editText.paddingEnd,
            editText.paddingBottom
        )
    }

    fun showTrailingIcon() {
        trailingIcon.visibility = View.VISIBLE
        editText.setPadding(
            editText.paddingStart,
            editText.paddingTop,
            resources.getDimensionPixelSize(R.dimen.trailing_icon_padding),
            editText.paddingBottom
        )
    }

    fun setLeadingIconClickListener(listener: OnClickListener) {
        leadingIcon.setOnClickListener(listener)
    }

    fun setTrailingIconClickListener(listener: OnClickListener) {
        trailingIcon.setOnClickListener(listener)
    }

    fun showDropdown(items: List<String>) {
        if (dropdownPopup == null) {
            val dropdownView = LayoutInflater.from(context).inflate(R.layout.custom_text_field_dropdown_layout, null)
            val container = dropdownView.findViewById<LinearLayout>(R.id.container)

            for (item in items) {
                val textView = TextView(context).apply {
                    text = item
                    textSize = 16f
                    setPadding(
                        resources.getDimensionPixelSize(R.dimen.dropdown_item_padding),
                        resources.getDimensionPixelSize(R.dimen.dropdown_item_padding),
                        resources.getDimensionPixelSize(R.dimen.dropdown_item_padding),
                        resources.getDimensionPixelSize(R.dimen.dropdown_item_padding)
                    )
                    setTextColor(Color.BLACK)
                }
                container.addView(textView)
            }

            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val focusable = true

            dropdownPopup = PopupWindow(dropdownView, width, height, focusable)
            dropdownPopup?.showAsDropDown(trailingIcon)
        } else {
            dropdownPopup?.dismiss()
            dropdownPopup = null
        }
    }

    fun setError(error: CharSequence?) {
        val paddingLeft = editText.paddingLeft
        val paddingTop = editText.paddingTop
        val paddingRight = editText.paddingRight
        val paddingBottom = editText.paddingBottom

        if (error != null) {
            isError = true
            editText.setBackgroundResource(R.drawable.custom_text_field_error_background)
        } else {
            isError = false
            editText.setBackgroundResource(R.drawable.custom_text_field_background_selector)
        }
        editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        editText.error = error
    }

    fun hasError(): Boolean {
        return isError
    }

    fun rightAlign() {
        binding.inputContainer.layoutDirection = View.LAYOUT_DIRECTION_RTL
        editText.textDirection = View.TEXT_DIRECTION_RTL
        editText.textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        editText.setPadding(
            editText.paddingStart,
            editText.paddingTop,
            resources.getDimensionPixelSize(R.dimen.trailing_icon_padding),
            editText.paddingBottom
        )

        leadingIcon.layoutParams = (leadingIcon.layoutParams as MarginLayoutParams).apply {
            marginEnd = 0
            marginStart = resources.getDimensionPixelSize(R.dimen.leading_icon_margin_start)
        }
    }

    fun enableCharacterCount(maxLength: Int) {
        this.maxLength = maxLength
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        charCount.visibility = View.VISIBLE
        updateCharCount()

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateCharCount()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateCharCount() {
        val count = editText.text?.length ?: 0
        charCount.text = "$count/$maxLength"
    }

    /**
     * A builder class for creating and configuring a [CustomTextField] instance.
     *
     * This builder provides a fluent API for setting various properties of the CustomTextField.
     *
     * @property customTextField The CustomTextField instance being configured.
     */
    class Builder(private val customTextField: CustomTextField) {
        private var hintText: String? = null
        private var supportingText: String? = null
        private var maxLength: Int = Int.MAX_VALUE
        private var showLeadingIcon: Boolean = false
        private var showTrailingIcon: Boolean = false
        private var errorText: String? = null
        private var rightAlign: Boolean = false
        private var dropdownItems: List<String>? = null
        private var trailingIconClickListener: OnClickListener? = null
        private var leadingIconClickListener: OnClickListener? = null

        fun setHintText(hint: String) = apply { this.hintText = hint }
        fun setSupportingText(text: String) = apply { this.supportingText = text }
        fun setMaxLength(maxLength: Int) = apply { this.maxLength = maxLength }
        fun showLeadingIcon() = apply { this.showLeadingIcon = true }
        fun showTrailingIcon() = apply { this.showTrailingIcon = true }
        fun setErrorText(error: String) = apply { this.errorText = error }
        fun rightAlign() = apply { this.rightAlign = true }
        fun setDropdownItems(items: List<String>) = apply { this.dropdownItems = items }
        fun setTrailingIconClickListener(listener: OnClickListener) =
            apply { this.trailingIconClickListener = listener }

        fun setLeadingIconClickListener(listener: OnClickListener) =
            apply { this.leadingIconClickListener = listener }

        fun build() {
            hintText?.let { customTextField.setHintText(it) }
            supportingText?.let { customTextField.setSupportingText(it) }
            if (maxLength != Int.MAX_VALUE) customTextField.enableCharacterCount(maxLength)
            if (showLeadingIcon) customTextField.showLeadingIcon()
            if (showTrailingIcon) customTextField.showTrailingIcon()
            errorText?.let { customTextField.setError(it) }
            if (rightAlign) customTextField.rightAlign()
            dropdownItems?.let { items ->
                customTextField.setTrailingIconClickListener {
                    customTextField.showDropdown(items)
                }
            }
            trailingIconClickListener?.let { customTextField.setTrailingIconClickListener(it) }
            leadingIconClickListener?.let { customTextField.setLeadingIconClickListener(it) }
        }
    }
}