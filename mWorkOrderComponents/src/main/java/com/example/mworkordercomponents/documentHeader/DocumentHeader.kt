package com.example.mworkordercomponents.documentHeader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mworkordercomponents.R

/**
 * A custom view component for displaying document headers in Android applications.
 *
 * This component extends ConstraintLayout and provides a structured layout for document headers,
 * including:
 * - A title and subtitle
 * - An optional image displayed in a CardView
 * - An icon
 *
 * The DocumentHeader offers methods to customize its appearance:
 * - Set the icon
 * - Set the image
 * - Set the title and subtitle texts
 * - Adjust the layout for right-to-left (RTL) languages
 *
 * This component is designed to be easily integrated into various document-centric interfaces,
 * providing a consistent and customizable header across different document types.
 */
class DocumentHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var image: ImageView
    private lateinit var card: CardView
    private lateinit var icon: ImageView
    private lateinit var document_header: ConstraintLayout

    init {
        inflate(context, R.layout.document_header, this)
        initializeViews()
    }

    private fun initializeViews() {
        title = findViewById(R.id.title)
        subtitle = findViewById(R.id.subtitle)
        image = findViewById(R.id.image)
        icon = findViewById(R.id.add_icon)
        card = findViewById(R.id.imageCard)
        document_header = findViewById(R.id.document_header_layout)
    }


    /**
     * Sets the icon for the document header.
     *
     * @param icon The resource ID of the icon to be displayed.
     */
    fun setIcon(icon: Int) {
        this.icon.setImageResource(icon)
    }

    /**
     * Sets the image for the document header and makes it visible.
     *
     * @param image The resource ID of the image to be displayed.
     */
    fun setImage(image: Int) {
        this.image.setBackgroundResource(image)
        this.card.visibility = View.VISIBLE
    }

    /**
     * Sets the title text of the document header.
     *
     * @param title The text to be displayed as the title.
     */
    fun setTitle(title: String) {
        this.title.text = title
    }

    /**
     * Sets the subtitle text of the document header.
     *
     * @param subtitle The text to be displayed as the subtitle.
     */
    fun setSubtitle(subtitle: String) {
        this.subtitle.text = subtitle
    }

    /**
     * Adjusts the layout for right-to-left (RTL) languages.
     * This includes changing the layout direction and text alignment.
     */
    fun rightAlign() {
        this.layoutDirection = View.LAYOUT_DIRECTION_RTL
        this.title.gravity = View.TEXT_ALIGNMENT_VIEW_START
        this.subtitle.gravity = View.TEXT_ALIGNMENT_VIEW_START
    }
}
