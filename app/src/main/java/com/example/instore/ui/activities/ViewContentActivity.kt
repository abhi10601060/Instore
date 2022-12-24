package com.example.instore.ui.activities

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.example.instore.R

class ViewContentActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_content)

        imageView = findViewById(R.id.view_image)
        videoView = findViewById(R.id.view_Video)


        val incomingIntent = intent
        if (intent != null){
            val incomingFilePath = intent.getStringExtra("path")
            if (incomingFilePath != null) {
                if (incomingFilePath.endsWith(".mp4")){
                    showVideo(incomingFilePath)
                }
                else{
                    showImage(incomingFilePath)
                }
            }
        }

    }

    private fun showVideo(path : String) {
        videoView.visibility  = View.VISIBLE
        imageView.visibility = View.GONE

        videoView.setVideoURI(Uri.parse(path))

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()

    }

    private fun showImage(path: String) {
        videoView.visibility  = View.GONE
        imageView.visibility = View.VISIBLE

        val bitmap = BitmapFactory.decodeFile(path)
        imageView.setImageBitmap(bitmap)
    }
}