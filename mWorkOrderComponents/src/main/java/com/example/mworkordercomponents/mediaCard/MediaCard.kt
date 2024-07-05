package com.example.mworkordercomponents.mediaCard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.example.mworkordercomponents.databinding.MediaCardBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

/**
 * A custom card view component for displaying and managing media content in Android applications.
 *
 * This component extends CardView and provides a rich media display interface with the following features:
 * - A ViewPager for displaying multiple images
 * - A dots indicator to show the current position in the image set
 * - An image index display (e.g., "1/5")
 * - A right icon button for adding new images
 * - A title text view
 *
 * The MediaCard allows for:
 * - Adding single or multiple images
 * - Automatic resizing of large images
 * - Integration with the device's image picker for selecting new images
 * - Dynamic visibility management of the ViewPager and related UI elements
 *
 * This component is designed to provide a flexible and user-friendly interface for media-rich content
 * in various application contexts.
 */
class MediaCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding: MediaCardBinding =
        MediaCardBinding.inflate(LayoutInflater.from(context), this, true)

    private val viewPager: ViewPager = binding.viewPager
    private val dotsIndicator: DotsIndicator = binding.dotsIndicator
    private val imageIndexTextView: TextView = binding.imageIndexTextView
    private val rightIcon: ImageButton = binding.rightIcon
    private val titleTextView: TextView = binding.title

    lateinit var imageAdapter: ImageAdapter

    val PICK_MEDIA_REQUEST_CODE = 1

    init {
        setupRightIconClickListener()
        setupViewPager()
    }

    private fun setupRightIconClickListener() {
        rightIcon.setOnClickListener {
            startImagePickerActivity()
        }
    }

    private fun startImagePickerActivity() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple selection
        (context as? Activity)?.startActivityForResult(intent, PICK_MEDIA_REQUEST_CODE)
    }

    private fun setupViewPager() {
        setupViewPagerListeners()
        setupImageAdapter()
    }

    private fun setupViewPagerListeners() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val currentIndex = position + 1
                val totalPages = imageAdapter.count
                updateImageIndexText(currentIndex, totalPages)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun setupImageAdapter() {
        imageAdapter = ImageAdapter(context)
        viewPager.adapter = imageAdapter
        dotsIndicator.setViewPager(viewPager)
    }

    /**
     * Adds multiple images to the MediaCard.
     *
     * @param images A list of Bitmap images to be added to the card.
     */
    fun addImages(images: List<Bitmap>) {
        val resizedImages = images.map { resizeBitmap(it) }
        imageAdapter.addImages(resizedImages)
        updateImageIndexText(viewPager.currentItem + 1, imageAdapter.count)
        showViewPagerIfNotEmpty()
    }

    /**
     * Adds a single image to the MediaCard.
     *
     * @param bitmap The Bitmap image to be added to the card.
     */
    fun addImage(bitmap: Bitmap) {
        val resizedBitmap = resizeBitmap(bitmap)
        imageAdapter.addImages(listOf(resizedBitmap))
        updateImageIndexText(viewPager.currentItem + 1, imageAdapter.count)
        showViewPagerIfNotEmpty()
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val maxWidth = 1024
        val maxHeight = 1024

        var width = bitmap.width
        var height = bitmap.height

        if (width <= maxWidth && height <= maxHeight) {
            return bitmap
        }

        val ratio = width.toFloat() / height.toFloat()
        if (ratio > 1) {
            width = maxWidth
            height = (width / ratio).toInt()
        } else {
            height = maxHeight
            width = (height * ratio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun updateImageIndexText(currentIndex: Int, totalPages: Int) {
        imageIndexTextView.text = "$currentIndex/$totalPages"
    }

    private fun showViewPagerIfNotEmpty() {
        if (imageAdapter.count > 0 && viewPager.visibility != View.VISIBLE) {
            viewPager.visibility = View.VISIBLE
            dotsIndicator.visibility = View.VISIBLE
            imageIndexTextView.visibility = View.VISIBLE
        }
    }
}
