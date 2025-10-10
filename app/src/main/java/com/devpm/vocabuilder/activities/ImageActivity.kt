package com.devpm.vocabuilder.activities

import android.os.Bundle
import android.view.View.GONE
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devpm.vocabuilder.R
import androidx.core.net.toUri

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image)

        val imageView = findViewById<ImageView>(R.id.fullscreenImageView)

        // Get image Uri from intent
        val imageUriStr = intent.getStringExtra("imageUri")
        if (imageUriStr != null) {
            val imageUri = imageUriStr.toUri()
            imageView.setImageURI(imageUri)
        } else {
            // Set default image
            // imageView.setImageResource(R.drawable.ic_user)
            // Hide ImageView
            imageView.visibility = GONE
        }

        // По клику на полноэкранное изображение закрываем активность
        imageView.setOnClickListener {
            finish()
        }
    }
}
