package com.example.mworkordercomponents.mediaCard

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class ImageAdapter(private val context: Context) : PagerAdapter() {
    private val images = mutableListOf<Bitmap>()

    fun addImages(newImages: List<Bitmap>) {
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.setImageBitmap(images[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
