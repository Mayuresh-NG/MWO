package com.example.mworkordercomponentlibrary

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mworkordercomponents.mediaCard.MediaCard

class MainActivity : AppCompatActivity() {
    private lateinit var mediaCard: MediaCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mediaCard = findViewById(R.id.mediaCard)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == mediaCard.PICK_MEDIA_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUris = data.clipData  // For multiple images
            val newImages = mutableListOf<Bitmap>()

            if (imageUris != null) {
                for (i in 0 until imageUris.itemCount) {
                    val imageUri = imageUris.getItemAt(i).uri
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    newImages.add(bitmap)
                }
            } else {
                val imageUri = data.data
                if (imageUri != null) {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    newImages.add(bitmap)
                }
            }
            mediaCard.addImages(newImages)
        }
    }
}